/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service;

import com.dremo.ucsm.gsc.sigesmed.core.security.TokenAuthentication;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Administrador
 */
@Provider
public class FiltroAutenticacionREST implements ContainerRequestFilter{

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        //System.out.println(" URI : "+ requestContext.getUriInfo().getPath()+ "  tipo " + requestContext.getMethod());
        
        //si no solicitamos logiarnos verificacmos la seguridad de nuestro servicio
        if( !requestContext.getUriInfo().getPath().contentEquals("login") ){        
            String autorizacion = requestContext.getHeaders().getFirst("autorizacion");
            //si no hay token
            if( autorizacion==null || autorizacion.contentEquals("") ){
                System.out.println("NO HAY TOKEN");
                requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }
            //token no autorizado
            if( !(new TokenAuthentication()).isValidAuthentication(autorizacion) ){
                System.out.println("TOKEN INVALIDO");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }
            
            
        }
    }
}
