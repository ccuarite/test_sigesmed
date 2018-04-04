/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.service.rest;

import com.dremo.ucsm.gsc.sigesmed.core.security.TokenAuthentication;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.TypeTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Administrador
 */
@Path("/login")
public class LoginService {
    private static Logger logger = Logger.getLogger(LoginService.class.getName());
    
    
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String SignIn(String content){
        
        JSONObject jsonObject = new JSONObject(content);
        WebRequest wRequest = WebRequest.createFromJSON(jsonObject);
        WebResponse wResponse = wRequest.invoke(Sigesmed.MODULO_CONFIGURACION);
        return wResponse.toJSON().toString();
    }
    //metodo que solo valida el token
    //sin necesidad de entrar a la logica
    @HEAD
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarToken(@HeaderParam("autorizacion") String autorizacion){

        //si no hay token
        if( autorizacion==null || autorizacion.contentEquals("") ){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //token no autorizado
        if( !(new TokenAuthentication()).isValidAuthentication(autorizacion) ){
            System.out.println("TOKEN INVALIDO");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        //el token a sido validado
        return Response.ok().build();
    }
}
