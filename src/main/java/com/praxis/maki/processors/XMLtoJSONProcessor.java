package com.praxis.maki.processors;

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

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementación de un NIFI Processor
 *
 * Para el parseo de un archivo XML a formato JSON.
 *
 * @author bamx Christian E. Badillo
 * @version 1.0
 *
 */
@SideEffectFree
@Tags({"XMLtoJSON", "XML", "JSON", "XML JSON", "XML TO JSON" })
@CapabilityDescription("Convierte un archivo XML a su reprensentación JSON")
@SuppressWarnings("SpellCheckingInspection")
public class XMLtoJSONProcessor extends AbstractProcessor{

    public XMLtoJSONProcessor() {
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

                try{
                    String xml = IOUtils.toString(in);
                    getLogger().debug(xml);
                    JSONObject json = XML.toJSONObject(xml);

                    json = new JSONObject(json.toString().replace("\"Moneda\":","\"moneda\":"));
                    json = new JSONObject(json.toString().replace("\"LugarExpedicion\":","\"lugarExpedicion\":"));
                    json = new JSONObject(json.toString().replace("\"TipoCambio\":","\"tipoCambio\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Emisor\":","\"emisor\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Comprobante\":","\"comprobante\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:DomicilioFiscal\":","\"domicilioFiscal\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Domicilio\":","\"domicilio\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:RegimenFiscal\":","\"regimenFiscal\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:ExpedidoEn\":","\"expedidoEn\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Conceptos\":","\"conceptos\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Concepto\":","\"concepto\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:ComplementoConcepto\":","\"complementoConcepto\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Complemento\":","\"complemento\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Impuestos\":","\"impuestos\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Traslados\":","\"traslados\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Traslado\":","\"traslado\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Receptor\":","\"receptor\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:Domicilio\":","\"domicilio\":"));
                    json = new JSONObject(json.toString().replace("\"tfd:TimbreFiscalDigital\":","\"timbreFiscalDigital\":"));
                    json = new JSONObject(json.toString().replace("\"cfdi:RegimenFiscal\":","\"regimenFiscal\":"));
                    json = new JSONObject(json.toString().replace("\"Regimen\":","\"regimen\":"));


                    json.getJSONObject("comprobante").remove("xmlns:xsi");

                    if(!json.getJSONObject("comprobante").isNull("complemento")) {
                        json.getJSONObject("comprobante").getJSONObject("complemento").getJSONObject("timbreFiscalDigital").remove("selloSAT");
                        json.getJSONObject("comprobante").getJSONObject("complemento").getJSONObject("timbreFiscalDigital").remove("selloCFD");
                        json.getJSONObject("comprobante").getJSONObject("complemento").getJSONObject("timbreFiscalDigital").remove("xsi:schemaLocation");
                        json.getJSONObject("comprobante").getJSONObject("complemento").getJSONObject("timbreFiscalDigital").remove("xmlns:tfd");
                    }
                    json.getJSONObject("comprobante").remove("sello");
                    json.getJSONObject("comprobante").remove("xmlns:cfdi");
                    json.getJSONObject("comprobante").remove("xsi:schemaLocation");
                    json.getJSONObject("comprobante").remove("xmlns:tfd");
                    json.getJSONObject("comprobante").remove("certificado");

                    JSONObject jsonFlat = new JSONObject();

                    if(!json.isNull("comprobante")){
                        if(!json.getJSONObject("comprobante").isNull("serie")){
                            jsonFlat.put("serie", json.getJSONObject("comprobante").getString("serie"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("version")){
                            jsonFlat.put("version", json.getJSONObject("comprobante").getDouble("version"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("fecha")){
                            jsonFlat.put("fecha", json.getJSONObject("comprobante").getString("fecha"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("tipoDeComprobante")){
                            jsonFlat.put("tipoDeComprobante", json.getJSONObject("comprobante").getString("tipoDeComprobante"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("formaDePago")){
                            jsonFlat.put("formaDePago", json.getJSONObject("comprobante").getString("formaDePago"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("condicionesDePago")){
                            jsonFlat.put("condicionesDePago", json.getJSONObject("comprobante").getString("condicionesDePago"));
                        }else{
                            jsonFlat.put("condicionesDePago", "NO DISPONIBLE");
                        }
                        if(!json.getJSONObject("comprobante").isNull("subTotal")){
                            jsonFlat.put("subTotal", json.getJSONObject("comprobante").getDouble("subTotal"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("descuento")){
                            jsonFlat.put("descuento", json.getJSONObject("comprobante").getDouble("descuento"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("tipoCambio")){
                            jsonFlat.put("tipoCambio", json.getJSONObject("comprobante").getDouble("tipoCambio"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("moneda")){
                            jsonFlat.put("moneda", json.getJSONObject("comprobante").getString("moneda"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("total")){
                            jsonFlat.put("total", json.getJSONObject("comprobante").getDouble("total"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("metodoDePago")){
                            jsonFlat.put("metodoDePago", json.getJSONObject("comprobante").getString("metodoDePago"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("lugarExpedicion")){
                            jsonFlat.put("lugarExpedicion", json.getJSONObject("comprobante").getString("lugarExpedicion"));
                        }
                        if(!json.getJSONObject("comprobante").isNull("impuestos")) {
                            if (!json.getJSONObject("comprobante").getJSONObject("impuestos").isNull("totalImpuestosTrasladados")) {
                                jsonFlat.put("impuestosTotalImpuestosTrasladados", json.getJSONObject("comprobante").getJSONObject("impuestos").getDouble("totalImpuestosTrasladados"));
                            }
                            if (!json.getJSONObject("comprobante").getJSONObject("impuestos").isNull("traslados")) {
                                if (!json.getJSONObject("comprobante").getJSONObject("impuestos").getJSONObject("traslados").isNull("traslado")) {
                                    if (!json.getJSONObject("comprobante").getJSONObject("impuestos").getJSONObject("traslados").getJSONObject("traslado").isNull("impuesto")) {
                                        jsonFlat.put("impuestostTrasladoImpuesto", json.getJSONObject("comprobante").getJSONObject("impuestos").getJSONObject("traslados").getJSONObject("traslado").getString("impuesto"));
                                    }
                                    if (!json.getJSONObject("comprobante").getJSONObject("impuestos").getJSONObject("traslados").getJSONObject("traslado").isNull("tasa")) {
                                        jsonFlat.put("impuestosTrasladoTasa", json.getJSONObject("comprobante").getJSONObject("impuestos").getJSONObject("traslados").getJSONObject("traslado").getDouble("tasa"));
                                    }
                                    if (!json.getJSONObject("comprobante").getJSONObject("impuestos").getJSONObject("traslados").getJSONObject("traslado").isNull("importe")) {
                                        jsonFlat.put("impuestosTrasladoImporte", json.getJSONObject("comprobante").getJSONObject("impuestos").getJSONObject("traslados").getJSONObject("traslado").getDouble("importe"));
                                    }
                                }
                            }
                        }
                        if(!json.getJSONObject("comprobante").isNull("emisor")){
                            if(!json.getJSONObject("comprobante").getJSONObject("emisor").isNull("rfc")){
                                jsonFlat.put("emisorRfc", json.getJSONObject("comprobante").getJSONObject("emisor").getString("rfc"));
                            }
                            if(!json.getJSONObject("comprobante").getJSONObject("emisor").isNull("nombre")){
                                jsonFlat.put("emisorNombre", json.getJSONObject("comprobante").getJSONObject("emisor").getString("nombre"));
                            }
                            if(!json.getJSONObject("comprobante").getJSONObject("emisor").isNull("domicilioFiscal")){
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").isNull("calle")){
                                    jsonFlat.put("emisorDomicilioFiscalCalle", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").getString("calle"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").isNull("noExterior")){
                                    jsonFlat.put("emisorDomicilioFiscalNumeroExterior", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").getString("noExterior"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").isNull("colonia")){
                                    jsonFlat.put("emisorDomicilioFiscalColonia", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").getString("colonia"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").isNull("localidad")){
                                    jsonFlat.put("emisorDomicilioFiscalLocalidad", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").getString("localidad"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").isNull("municipio")){
                                    jsonFlat.put("emisorDomicilioFiscalMunicipio", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").getString("municipio"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").isNull("estado")){
                                    jsonFlat.put("emisorDomicilioFiscalEstado", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").getString("estado"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").isNull("pais")){
                                    jsonFlat.put("emisorDomicilioFiscalPais", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").getString("pais"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").isNull("codigoPostal")){
                                    //jsonFlat.put("emisorDomicilioFiscalCodigoPostal", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").getString("codigoPostal"));

                                    Object obj = json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("domicilioFiscal").get("codigoPostal");
                                    if(obj instanceof  Integer){
                                        jsonFlat.put("emisorDomicilioFiscalCodigoPostal", Integer.toString((Integer) obj));
                                    }else {
                                        jsonFlat.put("emisorDomicilioFiscalCodigoPostal", obj.toString());
                                    }
                                }
                            }
                            if(!json.getJSONObject("comprobante").getJSONObject("emisor").isNull("expedidoEn")){
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").isNull("calle")){
                                    jsonFlat.put("emisorExpedidoEnCalle", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").getString("calle"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").isNull("noExterior")){
                                    jsonFlat.put("emisorExpedidoEnNumeroExterior", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").getString("noExterior"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").isNull("colonia")){
                                    jsonFlat.put("emisorExpedidoEnColonia", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").getString("colonia"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").isNull("localidad")){
                                    jsonFlat.put("emisorExpedidoEnLocalidad", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").getString("localidad"));
                                }else{
                                    jsonFlat.put("emisorExpedidoEnLocalidad","N/A");
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").isNull("municipio")){
                                    jsonFlat.put("emisorExpedidoEnMunicipio", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").getString("municipio"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").isNull("estado")){
                                    jsonFlat.put("emisorExpedidoEnEstado", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").getString("estado"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").isNull("pais")){
                                    jsonFlat.put("emisorExpedidoEnPais", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").getString("pais"));
                                }
                                if(!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").isNull("codigoPostal")){
                                    //jsonFlat.put("emisorExpedidoEnCodigoPostal", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").getString("codigoPostal"));
                                    Object obj = json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("expedidoEn").get("codigoPostal");
                                    if(obj instanceof  Integer){
                                        jsonFlat.put("emisorExpedidoEnCodigoPostal", Integer.toString((Integer) obj));
                                    }else {
                                        jsonFlat.put("emisorExpedidoEnCodigoPostal", obj.toString());
                                    }
                                }
                            }
                            if(!json.getJSONObject("comprobante").getJSONObject("emisor").isNull("regimenFiscal")) {
                                if (!json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("regimenFiscal").isNull("regimen")) {
                                    jsonFlat.put("emisorRegimenFiscalRegimen", json.getJSONObject("comprobante").getJSONObject("emisor").getJSONObject("regimenFiscal").getString("regimen"));
                                }
                            }
                         }
                        if(!json.getJSONObject("comprobante").isNull("receptor")) {
                            if (!json.getJSONObject("comprobante").getJSONObject("receptor").isNull("rfc")) {
                                jsonFlat.put("receptorRfc", json.getJSONObject("comprobante").getJSONObject("receptor").getString("rfc"));
                            }
                            if (!json.getJSONObject("comprobante").getJSONObject("receptor").isNull("nombre")) {
                                jsonFlat.put("receptorNombre", json.getJSONObject("comprobante").getJSONObject("receptor").getString("nombre"));
                            }
                            if (!json.getJSONObject("comprobante").getJSONObject("receptor").isNull("domicilio")) {
                                if (!json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").isNull("calle")) {
                                    jsonFlat.put("receptorDomicilioCalle", json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").getString("calle"));
                                }
                                if (!json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").isNull("noExterior")) {
                                    Object obj = json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").get("noExterior");
                                     if(obj instanceof  Integer){
                                         jsonFlat.put("receptorDomicilioNumeroExterior", Integer.toString((Integer) obj));
                                     }else {
                                         jsonFlat.put("receptorDomicilioNumeroExterior", obj.toString());
                                     }
                                }else{
                                    jsonFlat.put("receptorDomicilioNumeroExterior", "NO DISPONIBLE");
                                }
                                if (!json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").isNull("colonia")) {
                                    jsonFlat.put("receptorDomicilioColonia", json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").getString("colonia"));
                                }else{
                                    jsonFlat.put("receptorDomicilioColonia", "NO DISPONIBLE");
                                }
                                if (!json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").isNull("localidad")) {
                                    jsonFlat.put("receptorDomicilioLocalidad", json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").getString("localidad"));
                                }else{
                                    jsonFlat.put("receptorDomicilioLocalidad", "NO DISPONIBLE");
                                }
                                if (!json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").isNull("municipio")) {
                                    jsonFlat.put("receptorDomicilioMunicipio", json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").getString("municipio"));
                                }
                                if (!json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").isNull("estado")) {
                                    jsonFlat.put("receptorDomicilioEstado", json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").getString("estado"));
                                }else{
                                    jsonFlat.put("receptorDomicilioEstado", "NO DISPONIBLE");
                                }
                                if (!json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").isNull("pais")) {
                                    jsonFlat.put("receptorDomicilioPais", json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").getString("pais"));
                                }
                                if (!json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").isNull("codigoPostal")) {
                                    //jsonFlat.put("receptorDomicilioCodigoPostal", json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").getString("codigoPostal"));
                                    Object obj = json.getJSONObject("comprobante").getJSONObject("receptor").getJSONObject("domicilio").get("codigoPostal");
                                    if(obj instanceof  Integer){
                                        jsonFlat.put("receptorDomicilioCodigoPostal", Integer.toString((Integer) obj));
                                    }else {
                                        jsonFlat.put("receptorDomicilioCodigoPostal", obj.toString());
                                    }
                                }
                            }
                        }
                        if(!json.getJSONObject("comprobante").isNull("conceptos")) {
                            if (!json.getJSONObject("comprobante").getJSONObject("conceptos").isNull("concepto")) {

                                Object obj = json.getJSONObject("comprobante").getJSONObject("conceptos").get("concepto");

                                if (obj instanceof JSONArray) {
                                    JSONArray conceptos = (JSONArray) obj;
                                    JSONObject objConcepto = null;
                                    int current = 1;

                                    for(int i = 0; i < conceptos.length(); i++ ){
                                        objConcepto = conceptos.getJSONObject(i);

                                        if (!objConcepto.isNull("cantidad")) {
                                            jsonFlat.put("concepto"+ current +"Cantidad", objConcepto.getDouble("cantidad"));
                                        }else{
                                            jsonFlat.put("concepto"+ current +"Cantidad", -1);
                                        }
                                        if (!objConcepto.isNull("unidad")) {
                                            jsonFlat.put("concepto"+ current+"Unidad", objConcepto.getString("unidad"));
                                        }else{
                                            jsonFlat.put("concepto"+ current +"Unidad", "NO DISPONIBLE");
                                        }
                                        if (!objConcepto.isNull("descripcion")) {
                                            jsonFlat.put("concepto"+ current +"Descripcion", objConcepto.getString("descripcion"));
                                        }else{
                                            jsonFlat.put("concepto"+ current +"Descripcion", "NO DISPONIBLE");
                                        }
                                        if (!objConcepto.isNull("valorUnitario")) {
                                            jsonFlat.put("concepto"+ current +"ValorUnitario", objConcepto.getDouble("valorUnitario"));
                                        }else{
                                            jsonFlat.put("concepto"+ current +"ValorUnitario", -1);
                                        }
                                        if (!objConcepto.isNull("importe")) {
                                            jsonFlat.put("concepto"+ current +"Importe", objConcepto.getDouble("importe"));
                                        }else{
                                            jsonFlat.put("concepto"+ current +"Importe", -1);
                                        }
                                        current++;
                                    }
                                    for(int i = current; i <= 20; i++ ){
                                        jsonFlat.put("concepto"+ i +"Cantidad", -1);
                                        jsonFlat.put("concepto"+ i +"Unidad", "NO DISPONIBLE");
                                        jsonFlat.put("concepto"+ i +"Descripcion", "NO DISPONIBLE");
                                        jsonFlat.put("concepto"+ i +"ValorUnitario", -1);
                                        jsonFlat.put("concepto"+ i +"Importe", -1);
                                    }

                                } else {
                                    if (!json.getJSONObject("comprobante").getJSONObject("conceptos").getJSONObject("concepto").isNull("cantidad")) {
                                        jsonFlat.put("concepto1Cantidad", json.getJSONObject("comprobante").getJSONObject("conceptos").getJSONObject("concepto").getDouble("cantidad"));
                                    }
                                    if (!json.getJSONObject("comprobante").getJSONObject("conceptos").getJSONObject("concepto").isNull("unidad")) {
                                        jsonFlat.put("concepto1Unidad", json.getJSONObject("comprobante").getJSONObject("conceptos").getJSONObject("concepto").getString("unidad"));
                                    }
                                    if (!json.getJSONObject("comprobante").getJSONObject("conceptos").getJSONObject("concepto").isNull("descripcion")) {
                                        jsonFlat.put("concepto1Descripcion", json.getJSONObject("comprobante").getJSONObject("conceptos").getJSONObject("concepto").getString("descripcion"));
                                    }
                                    if (!json.getJSONObject("comprobante").getJSONObject("conceptos").getJSONObject("concepto").isNull("valorUnitario")) {
                                        jsonFlat.put("concepto1ValorUnitario", json.getJSONObject("comprobante").getJSONObject("conceptos").getJSONObject("concepto").getDouble("valorUnitario"));
                                    }
                                    if (!json.getJSONObject("comprobante").getJSONObject("conceptos").getJSONObject("concepto").isNull("importe")) {
                                        jsonFlat.put("concepto1Importe", json.getJSONObject("comprobante").getJSONObject("conceptos").getJSONObject("concepto").getDouble("importe"));
                                    }

                                    for(int current = 2; current <= 20; current++ ){
                                        jsonFlat.put("concepto"+ current +"Cantidad", -1);
                                        jsonFlat.put("concepto"+ current +"Unidad", "NO DISPONIBLE");
                                        jsonFlat.put("concepto"+ current +"Descripcion", "NO DISPONIBLE");
                                        jsonFlat.put("concepto"+ current +"ValorUnitario", -1);
                                        jsonFlat.put("concepto"+ current +"Importe", -1);
                                    }

                                }
                            }
                        }
                    }

                    if(jsonFlat != null){
                        System.out.println("LD Json aplanado correctamente");
                    }

                    String result = jsonFlat.toString();
                    getLogger().debug(result);
                    value.set(result);
                }catch(Exception ex){
                    ex.printStackTrace();
                    getLogger().error("Failed to read xml string.");
                    getLogger().debug("Error en el parseo de XML a JSON");
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
