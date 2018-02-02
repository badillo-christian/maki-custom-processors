package com.praxis.maki.processors;

import com.praxis.maki.model.ComprobanteMin;
import org.apache.avro.Schema;

import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectData;
import org.apache.commons.io.IOUtils;
import org.apache.nifi.annotation.behavior.SideEffectFree;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.*;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.io.InputStreamCallback;
import org.apache.nifi.processor.io.OutputStreamCallback;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementación de un NIFI Processor
 *
 * Para el parseo de un archivo JSON a formato AVRO.
 *
 * @author bamx Christian E. Badillo
 * @version 1.0
 *
 */
@SideEffectFree
@Tags({"JSONtoAVRO", "AVRO", "JSON", "AVRO JSON", "JSON TO AVRO" })
@CapabilityDescription("Convierte un archivo JSON a su representación AVRO")
@SuppressWarnings("SpellCheckingInspection")
public class AvroWriterProcessor  extends AbstractProcessor {

    public AvroWriterProcessor() {
    }

    private List<PropertyDescriptor> properties;
    private Set<Relationship> relationships;

    public static final String MATCH_ATTR = "match";

    public static final Relationship SUCCESS = new Relationship.Builder()
            .name("SUCCESS")
            .description("Succes relationship")
            .build();

    @Override
    public void init(final ProcessorInitializationContext context){

        Set<Relationship> relationships = new HashSet<>();
        relationships.add(SUCCESS);
        this.relationships = Collections.unmodifiableSet(relationships);
    }

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {

        final AtomicReference<String> value = new AtomicReference<>();

        FlowFile flowfile = session.get();

        session.read(flowfile, new InputStreamCallback() {
            @Override
            public void process(InputStream in) throws IOException {
                DataInputStream din = null;
                Object datumAvro = null;
                String json = null;
                InputStream  stream = null;
                Schema jsonSchema = null;
                Decoder decoder = null;
                DatumReader<Object> reader = null;
                GenericDatumWriter<Object> writer = null;
                ByteArrayOutputStream outputStream = null;
                Encoder encoder = null;
                String result = null;

                try{
                    json = IOUtils.toString(in);
                    stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8.name()));
                    jsonSchema = ReflectData.get().getSchema(ComprobanteMin.class);
                    din = new DataInputStream(stream);
                    decoder = DecoderFactory.get().jsonDecoder(jsonSchema, din);
                    reader = new GenericDatumReader<>(jsonSchema);

                    while(true) {
                        try {
                            datumAvro = reader.read(null, decoder);
                        } catch (EOFException e) {
                            break;

                        }
                    }

                    writer = new GenericDatumWriter<Object>(jsonSchema);
                    outputStream = new ByteArrayOutputStream();
                    encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
                    writer.write(datumAvro, encoder);
                    encoder.flush();

                    result = outputStream.toString();

                    getLogger().debug(result);
                    value.set(result);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    getLogger().error("Failed to convert json to avro.");
                    getLogger().debug("Error en el parseo de JSON a AVRO");
                }finally {
                    try {
                        din.close();
                    }catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }
            }
        });

        // Write the results to an attribute
        String results = value.get();
        if(results != null && !results.isEmpty()){
            flowfile = session.putAttribute(flowfile, "match", results);
        }

        // To write the results back out ot flow file
        flowfile = session.write(flowfile, new OutputStreamCallback() {

            @Override
            public void process(OutputStream out) throws IOException {
                out.write(value.get().getBytes());
            }
        });

        session.transfer(flowfile, SUCCESS);
    }

    @Override
    public Set<Relationship> getRelationships(){
        return relationships;
    }

    @Override
    public List<PropertyDescriptor> getSupportedPropertyDescriptors(){
        return properties;
    }
}
