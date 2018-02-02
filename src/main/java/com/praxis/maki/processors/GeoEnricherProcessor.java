package com.praxis.maki.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praxis.maki.dto.GoogleGeoCodeDTO;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.*;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.io.InputStreamCallback;
import org.apache.nifi.processor.io.OutputStreamCallback;
import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Tags({"http", "https", "remote", "json"})
@InputRequirement(InputRequirement.Requirement.INPUT_ALLOWED)

public class GeoEnricherProcessor extends AbstractProcessor {

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
                try {
                    String API_KEY = "AIzaSyCFWs_trMu3F5dRPSlvgkdjdKjJ9vQv4f4";
                    String jsonFlat = null;
                    String codigoPostal;
                    String latitud;
                    String longitud;

                    StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/geocode/json?");

                    url.append("key=");
                    url.append(API_KEY);
                    url.append("&");

                    url.append("components=country:MX|postal_code:");

                    jsonFlat = IOUtils.toString(in);

                    JSONObject json = new JSONObject(jsonFlat);

                    codigoPostal = json.getString("emisorExpedidoEnCodigoPostal");

                    if(codigoPostal != null && codigoPostal.length() == 5) {
                        url.append(codigoPostal);
                        try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
                            HttpGet request = new HttpGet(url.toString());

                            request.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:31.0) Gecko/20100101 Firefox/31.0 Iceweasel/31.6.0");
                            request.setHeader("Host", "maps.googleapis.com");
                            request.setHeader("Connection", "keep-alive");
                            request.setHeader("Accept-Language", "en-US,en;q=0.5");
                            request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                            request.setHeader("Accept-Encoding", "gzip, deflate");

                            try (CloseableHttpResponse response = httpclient.execute(request)) {
                                HttpEntity entity = response.getEntity();

                                StringBuilder resultResponse = new StringBuilder();
                                try (BufferedReader in2 = new BufferedReader(new InputStreamReader(entity.getContent()))) {
                                    String inputLine;
                                    while ((inputLine = in2.readLine()) != null) {
                                        resultResponse.append(inputLine);
                                        resultResponse.append("\n");
                                    }
                                }

                                ObjectMapper mapper = new ObjectMapper();
                                GoogleGeoCodeDTO geocode = mapper.readValue(resultResponse.toString(), GoogleGeoCodeDTO.class);

                                if (!"OK".equals(geocode.getStatus())) {
                                    json.put("latitudDireccionExpedicion", new Double(-90.0));
                                    json.put("longitudDireccionExpedicion", new Double(0.0));
                                    if (geocode.getError_message() != null) {
                                        throw new Exception(geocode.getError_message());
                                    }
                                    throw new Exception("Can not find geocode for: " + codigoPostal);
                                }else{
                                    if(geocode.getResults().length > 1){
                                        latitud = geocode.getResults()[0].getGeometry().getLocation().getLat();
                                        longitud = geocode.getResults()[0].getGeometry().getLocation().getLng();
                                        json.put("latitudDireccionExpedicion", new Double(latitud));
                                        json.put("longitudDireccionExpedicion", new Double(longitud));
                                    }

                                }
                                //return geocode;
                            }
                        }

                    }

                    if(json != null){
                        System.out.println("LD Json enriquecido correctamente");
                    }

                    String result = json.toString();
                    getLogger().debug(result);
                    value.set(result);
                }catch(Exception ex){
                    ex.printStackTrace();
                    getLogger().error("Failed to read xml string.");
                    getLogger().debug("Error en el enrichment");
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