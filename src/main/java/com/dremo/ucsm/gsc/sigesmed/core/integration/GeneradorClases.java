/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.integration;

import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Mensajes;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class GeneradorClases {
    
    private String contexto;
    private String direccionFisica;
    public GeneradorClases(){
        
    }
    public GeneradorClases(String direccionFisica,String contexto){
        
        this.contexto = contexto;
        this.direccionFisica = direccionFisica;
    }
    
    //buscaqueda de archivos profundidad ilimitada
    public List<String> retornarNombredeArchivos(String subDirectorio,String nombreReferencia){
        
        List<String> lista = new ArrayList<String>();
        File directorio = new File(this.direccionFisica +"/"+subDirectorio);
        String paqueteRaiz = subDirectorio.replace('/', '.');
        if( directorio.exists() ){
            
            File[] ficheros = directorio.listFiles();
            for(File file : ficheros)
                buscarNombresAchivos(lista,paqueteRaiz,file,nombreReferencia);
        }
        else
            throw new RuntimeException(Mensajes.ERR_DIRECTORIO_NO_ENCONTRADO +" dir: "+subDirectorio);//error
        
        return lista;
    }
    //buscaqueda de archivos profundidad ilimitada
    public List<Class> retornarClases(String subDirectorio,String nombreReferencia){
        
        List<Class> lista = new ArrayList<Class>();
        File directorio = new File(this.direccionFisica +"/"+subDirectorio);
        String paqueteRaiz = subDirectorio.replace('/', '.');
        if( directorio.exists() ){
            
            File[] ficheros = directorio.listFiles();
            for(File file : ficheros)
                buscarClases(lista,paqueteRaiz,file,nombreReferencia);            
        }
        else
            throw new RuntimeException(Mensajes.ERR_DIRECTORIO_NO_ENCONTRADO +" dir: "+subDirectorio);//error
        
        return lista;
    }
    private void buscarNombresAchivos(List<String>lista,String directorioRaiz,File fichero,String nombreReferencia ){
        
        DirectorioFicheros dirFic = analizarFichero(fichero,directorioRaiz);
                
        if(dirFic != null)
            //aplicar el algoritmo de nuevo
            for(File file : dirFic.archivos)
                buscarNombresAchivos(lista,dirFic.nombre,file,nombreReferencia);
        else
            agregarArchivo(lista,directorioRaiz,fichero.getName(),nombreReferencia);
        
    }
    private void buscarClases(List<Class>lista,String directorioRaiz,File fichero,String nombreReferencia ){
        
        DirectorioFicheros dirFic = analizarFichero(fichero,directorioRaiz);
                
        if(dirFic != null)
            //aplicar el algoritmo de nuevo
            for(File file : dirFic.archivos)
                buscarClases(lista,dirFic.nombre,file,nombreReferencia);
        else
            agregarClase(lista,directorioRaiz,fichero.getName(),nombreReferencia);
        
    }
    
    private DirectorioFicheros analizarFichero(File fichero, String directorioRaiz){
        
        if( fichero.isDirectory() ){
            File[] ficheros = fichero.listFiles();
            String nombre = directorioRaiz+"."+fichero.getName();
            return new DirectorioFicheros(nombre,ficheros);
        }
        return null;
    }
    
    private void agregarArchivo(List<String>lista ,String directorioRaiz, String nombre, String nombreReferencia){        
        
        //si el nombre del archivo tiene la palabra reservada agregamos
        if(nombre.contains(nombreReferencia) ){
            //ubicamos le tipo de extencion
            int posicionExtencion = nombre.lastIndexOf(".");
            String nombreFinal = this.contexto+"."+directorioRaiz+"."+nombre.substring(0, posicionExtencion);
            lista.add( nombreFinal );
        }
    }
    private void agregarClase(List<Class>lista ,String directorioRaiz, String nombre, String nombreReferencia){
        
        //si el nombre del archivo tiene la palabra reservada agregamos
        if(nombre.contains(nombreReferencia) ){
            //ubicamos le tipo de extencion
            int posicionExtencion = nombre.lastIndexOf(".");
            String nombreFinal = this.contexto+"."+directorioRaiz+"."+nombre.substring(0, posicionExtencion);            
            try {
                lista.add( Class.forName(nombreFinal));
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(Mensajes.ERR_CLASE_NO_ENCONTRADO+" nombre clase : "+ex.getMessage());//error
            }
            
            
        }
    }
    
}
