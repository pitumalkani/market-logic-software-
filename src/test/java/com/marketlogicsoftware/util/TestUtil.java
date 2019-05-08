package com.marketlogicsoftware.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class TestUtil {

    /** The Constant JSON_BASE_PATH. */
    private static final String JSON_BASE_PATH = "/JSON/";
    
    public  String returnJsonAsString( String fileName ) throws IOException, URISyntaxException {
        String jsonFile = new String( Files.readAllBytes( Paths.get( getClass().getResource( JSON_BASE_PATH + fileName ).toURI() ) ) );
        return jsonFile;
    }
}
