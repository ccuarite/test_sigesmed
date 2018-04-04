/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service;

import com.dremo.ucsm.gsc.sigesmed.core.integration.LoaderModule;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

/**
 *
 * @author Administrador
 */
@ApplicationPath("/rest")
public class ServicioREST extends Application{    
    
    private String direccionRealAplicacion;
    public static String PATH_SIGESMED;
    
    public ServicioREST(){
        //super();
        System.out.println("SE INICIO NUESTRO SERVICIO REST SIGESMED");
    }
    
    @Context
    public void setServletContext(ServletContext context) {
        PATH_SIGESMED = context.getRealPath("");
        this.direccionRealAplicacion = context.getRealPath("/WEB-INF/classes/"+Sigesmed.COXTEXTO_PROYECTO_URL);
        System.out.println("direccion real : "+this.direccionRealAplicacion);
        
        LoaderModule.cargarModuloLogica(direccionRealAplicacion);
    }
    
    @Override
    public Set<Class<?>> getClasses(){
        return LoaderModule.cargarModuloServicios(direccionRealAplicacion);
    }
}

