/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.integration;

import com.dremo.ucsm.gsc.sigesmed.core.service.FiltroAutenticacionREST;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebCommandFactory;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebComponent;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Mensajes;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.IComponentRegister;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Administrador
 */
public class LoaderModule {
    
    public static void cargarModuloLogica(String direccionReal){
        
        WebCommandFactory factory = WebCommandFactory.getInstance();
        System.out.println("Cargando Dominios y Transacciones");
        List<Class> componentes = new GeneradorClases(direccionReal,Sigesmed.COXTEXTO_PROYECTO).retornarClases(Sigesmed.UBI_LOGICA,"ComponentRegister");

        try {
            for(Class componente: componentes){
                //instanciando a partir de la clase del componente
                IComponentRegister component = (IComponentRegister)componente.newInstance();                
                //Creando el nuevo oomponente, registrnado sus transacciones
                WebComponent nuevoComponent = component.createComponent();
                
                //registrando dominio
                factory.registerComponent2(nuevoComponent);
            }
        
        } catch (InstantiationException | IllegalAccessException  ex) {
            throw new RuntimeException(Mensajes.ERR_INSTANCIANDO_COMPONENTE+ " componente : "+ex.getMessage());//erro
        } catch (Exception ex) {
            throw new RuntimeException(Mensajes.ERR_REGISTRO_COMPONENTE + " componente : "+ex.getMessage());//error
        }        
    }
    public static Set<Class<?>> cargarModuloServicios(String direccionReal){
        
        final Set<Class<?>> recursos = new java.util.HashSet<>();
        
        //Registrando el filtro de seguridad
        System.out.println("Cargando servicios rest");
        recursos.add(FiltroAutenticacionREST.class);
        List<Class> servicios = new GeneradorClases(direccionReal,Sigesmed.COXTEXTO_PROYECTO).retornarClases(Sigesmed.UBI_SERVICIOS, "Service");
        try {
            for(Class servicio: servicios){
                System.out.println("agregando recurso rest : "+servicio);
                recursos.add(servicio);
            }
        } catch (Exception ex) {
            throw new RuntimeException(Mensajes.ERR_REGISTRO_SERVICIO+" Servicio : "+ex.getMessage());//error
        }
        
        return recursos;        
    }
    
}
