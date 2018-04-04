/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.service.rest.configuracion_inicial;

import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.TypeTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 *
 * @author Administrador
 */

@Path("/configuracionInicial")
public class ConfiguracionInicialService {
    
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String recursoPost(String content){
        JSONObject jsonObject = new JSONObject(content);
        WebRequest wRequest = WebRequest.createFromJSON(jsonObject);        
        WebResponse wResponse = wRequest.invoke(Sigesmed.MODULO_CONFIGURACION,TypeTransaction.type_transaction_POST);        
        return wResponse.toJSON().toString();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String recursoGet(@QueryParam("content") String content){
        
        JSONObject jsonObject = new JSONObject(content);
        WebRequest wRequest = WebRequest.createFromJSON(jsonObject);
        WebResponse wResponse = wRequest.invoke(Sigesmed.MODULO_CONFIGURACION,TypeTransaction.type_transaction_GET);
        return wResponse.toJSON().toString();
    }    
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String recursoPut(String content){
        
        JSONObject jsonObject = new JSONObject(content);
        WebRequest wRequest = WebRequest.createFromJSON(jsonObject);
        WebResponse wResponse = wRequest.invoke(Sigesmed.MODULO_CONFIGURACION,TypeTransaction.type_transaction_PUT);
        return wResponse.toJSON().toString();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String recursoDelete(@QueryParam("content") String content){
        
        JSONObject jsonObject = new JSONObject(content);
        WebRequest wRequest = WebRequest.createFromJSON(jsonObject);
        WebResponse wResponse = wRequest.invoke(Sigesmed.MODULO_CONFIGURACION,TypeTransaction.type_transaction_DELETE);
        return wResponse.toJSON().toString();
    }
}
