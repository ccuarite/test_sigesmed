/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service.constantes;

/**
 *
 * @author Administrador
 */
public class Sigesmed {
    
    public static final String NOM_PROYECTO = "sigesmed";
    public static final String UBI_SERVICIOS = "service/rest";
    public static final String UBI_LOGICA = "logic";
    
    public static final String COXTEXTO_PROYECTO = "com.dremo.ucsm.gsc.sigesmed";
    public static final String COXTEXTO_PROYECTO_URL = "com/dremo/ucsm/gsc/sigesmed";
    
    
    /*
    * LISTA DE MODULOS
    */
    public static final int MODULO_CONFIGURACION = 1;
    public static final int MODULO_TRAMITE_DOCUMENTARIO = 2;
    
    
    public static final int MODULO_OTROS = 3;
    
    
    
    /*
    public static String contextoAplicacion(){
        String paquete = this..getPackage().getName();
        //buscando el inicio del proyecto global
        int posicionProyecto = paquete.indexOf(".", paquete.indexOf(Sigesmed.NOM_PROYECTO) );
        //substrayendo el paquete principal
        paquete = paquete.substring(0, posicionProyecto);
        
        return paquete;
    }*/
    
}
