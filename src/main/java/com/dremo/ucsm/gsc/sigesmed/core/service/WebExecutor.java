/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service;

import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Mensajes;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;

/**
 *
 * @author Administrador
 */
public final class WebExecutor {
    //private WebCommandFactory domainFactory = null;
    //private static String TAG = WebExecutor.class.getName();
    /*
    public static WebResponse executeRequest(String componentId,String versionId, String txId, WebRequest request){
        
        ITransaction tx = WebCommandFactory.getInstance().getTransaction(componentId, versionId, txId);
        
        try{
            return tx.execute(request);
        }catch(Exception e){
            //e.printStackTrace();
            throw  new RuntimeException(Mensajes.ERR_EJECUTANDO_COMANDO+" Clase: " + tx.toString());
        }
        
    }*/
    public static WebResponse executeRequest(int moduleId,String componentId,String versionId, String txId, WebRequest request){
        
        ITransaction tx = WebCommandFactory.getInstance().getTransaction(moduleId,componentId, versionId, txId);
        
        try{
            return tx.execute(request);
        }catch(Exception e){
            //e.printStackTrace();
            throw  new RuntimeException(Mensajes.ERR_EJECUTANDO_COMANDO+" Clase: " + tx.toString());
        }
        
    }
}
