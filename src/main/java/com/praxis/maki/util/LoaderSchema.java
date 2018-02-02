package com.praxis.maki.util;

import com.praxis.maki.constants.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Utileria para la carga de schemas.
 *
 * @author bamx Christian E. Badillo.
 * @version 1.0
 *
 */
public class LoaderSchema {

    private static LoaderSchema instance;

    public static LoaderSchema getInstance() {
        if (instance == null) {
            instance = new LoaderSchema();
        }
        return instance;
    }

    /**
     * Metodo que obtiene el schema.
     *
     * @param type El tipo de schema que se desea obtener
     * @return Objeto File con el schema solicitado
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("SpellCheckingInspection")
    public File getSchema(String type) throws URISyntaxException {

        File result = null;

        try {

            FileUtils fileUtils = FileUtils.getInstance();
            String ruta = null;
            ruta= "xsd/";

            if(type.equalsIgnoreCase(Constants.SCHEMA_NAME_CFDV32)){
                ruta += "cfdv32.xsd";
            }else if(type.equalsIgnoreCase(Constants.SCHEMA_NAME_TIMBRE_FISCAL_DIGITAL)){
                ruta += "TimbreFiscalDigital.xsd";
            }else if(type.equalsIgnoreCase(Constants.SCHEMA_NAME_TERCEROS_11)){
                ruta += "terceros11.xsd";
            }

            URL resource = this.getClass().getClassLoader().getResource(ruta);

            URI aURI = fileUtils.toURI(resource.getPath());

            result =  new File(aURI.getSchemeSpecificPart());

        }
        catch (URISyntaxException use){
            //throw new URISyntaxException();
        }
        return result;
    }
}