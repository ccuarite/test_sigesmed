package com.dremo.ucsm.gsc.sigesmed.core.service.base;

/**
 *
 * @author Administrador
 */
public class FileBaseObject {
    
    private String name;        //nombre del archivo con su extension
    private String type;        //tipo de archivo (ej application, image etc)    
    private String codeType;    //codificacion del contenido (ej base64, base32, etc)
    private String data;        //contenido del archivo    
    
    public FileBaseObject(BaseObject bo){
        parseBaseObject(bo);
    }
    
    private void parseBaseObject(BaseObject bo){
        //leendo un los atributos de un archivo
        try{
            this.name = bo.getElementString("name");
            this.type = bo.getElementString("type");
            this.codeType= bo.getElementString("codeType");
            this.data = bo.getElementString("data");
        }catch(Exception e){
            System.out.println( "Error leendo formato de archivo : "+e);
            throw new RuntimeException("Error leendo formato de archivo: "+e);
        }
    }
    
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public String getCodeType(){
        return codeType;
    }
    public String getData(){
        return data;
    }
}
