package com.praxis.maki.processors;

import com.praxis.maki.constants.Constants;
import com.praxis.maki.util.LoaderSchema;
import org.apache.commons.io.IOUtils;
import org.apache.nifi.annotation.behavior.*;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.logging.ComponentLog;
import org.apache.nifi.processor.*;
import org.apache.nifi.processor.io.InputStreamCallback;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@EventDriven
@SideEffectFree
@SupportsBatching
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED)
@Tags({"xml", "schema", "validation", "xsd"})
@WritesAttributes({
        @WritesAttribute(attribute = "validate32xml.invalid.error", description = "If the flow file is routed to the invalid relationship "
                + "the attribute will contain the error message resulting from the validation failure.")
})
@CapabilityDescription("Valida el contenido del Flowfile contra los schemas de la versión 3.2")
@SuppressWarnings("SpellCheckingInspection")
public class ValidateFactura32Processor extends AbstractProcessor {

    public ValidateFactura32Processor() {
    }

    public static final String ERROR_ATTRIBUTE_KEY = "validate32xml.invalid.error";


    public static final Relationship REL_VALID = new Relationship.Builder()
            .name("valid")
            .description("FlowFiles that are successfully validated against the schemas are routed to this relationship")
            .build();
    public static final Relationship REL_INVALID = new Relationship.Builder()
            .name("invalid")
            .description("FlowFiles that are not valid according to the specified schemas are routed to this relationship")
            .build();

    private List<PropertyDescriptor> properties;
    private Set<Relationship> relationships;

    @Override
    protected void init(final ProcessorInitializationContext context) {
        final List<PropertyDescriptor> properties = new ArrayList<>();
        this.properties = Collections.unmodifiableList(properties);

        final Set<Relationship> relationships = new HashSet<>();
        relationships.add(REL_VALID);
        relationships.add(REL_INVALID);
        this.relationships = Collections.unmodifiableSet(relationships);
    }

    @Override
    public Set<Relationship> getRelationships() {
        return relationships;
    }

    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return properties;
    }

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) {
        final List<FlowFile> flowFiles = session.get(50);
        if (flowFiles.isEmpty()) {
            return;
        }

        final ComponentLog logger = getLogger();

        for (FlowFile flowFile : flowFiles) {
            final AtomicBoolean valid = new AtomicBoolean(true);
            final AtomicReference<Exception> exception = new AtomicReference<Exception>(null);

            session.read(flowFile, new InputStreamCallback() {
                @Override
                public void process(final InputStream in) throws IOException {
                    try {
                        String xml = IOUtils.toString(in);
                        validarComprobante(xml);
                    } catch (final URISyntaxException | IllegalArgumentException | SAXException e) {
                        valid.set(false);
                        exception.set(e);
                    }
                }
            });

            if (valid.get()) {
                logger.debug("Successfully validated {} against schema; routing to 'valid'", new Object[]{flowFile});
                session.getProvenanceReporter().route(flowFile, REL_VALID);
                session.transfer(flowFile, REL_VALID);
            } else {
                flowFile = session.putAttribute(flowFile, ERROR_ATTRIBUTE_KEY, exception.get().getLocalizedMessage());
                logger.info("Failed to validate {} against schema due to {}; routing to 'invalid'", new Object[]{flowFile, exception.get().getLocalizedMessage()});
                session.getProvenanceReporter().route(flowFile, REL_INVALID);
                session.transfer(flowFile, REL_INVALID);
            }
        }
    }

    private void validarComprobante(final String inputXml)
            throws SAXException, IOException, URISyntaxException {

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        LoaderSchema loaderSchema = LoaderSchema.getInstance();

        byte[] bytes = inputXml.getBytes("UTF-8");

        Source[] schemas = new Source[3];
        schemas[0] = new StreamSource(loaderSchema.getSchema(Constants.SCHEMA_NAME_CFDV32));
        schemas[1] = new StreamSource(loaderSchema.getSchema(Constants.SCHEMA_NAME_TIMBRE_FISCAL_DIGITAL));
        schemas[2] = new StreamSource(loaderSchema.getSchema(Constants.SCHEMA_NAME_TERCEROS_11));
        Schema schema = factory.newSchema(schemas);
        Validator validator = schema.newValidator();

        Locale.setDefault(Locale.ENGLISH);
        Source source = new StreamSource(new InputStreamReader(new ByteArrayInputStream(bytes)));
        validator.validate(source);
    }

}