package com.dremo.ucsm.gsc.sigesmed.util;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Administrador
 */
public class BuildFile {
    //Construye un archivo apartir de una cadena en Base64
    public static void buildFromBase64(String path, String nombreFile, String fileBase64 ){
        byte fileBinary[] = Base64.decodeBase64(fileBase64);
        try {
            FileOutputStream file = new FileOutputStream(path+"/"+nombreFile);
            file.write(fileBinary);
            file.close();
        }catch (IOException ex) {
            System.out.println( "Error Creando el File : "+nombreFile +"\n"+ ex) ;
            throw new RuntimeException("Error Creando el File : "+nombreFile);
        }
    }
}
