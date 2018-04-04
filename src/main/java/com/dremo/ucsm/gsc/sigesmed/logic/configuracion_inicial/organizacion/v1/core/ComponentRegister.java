/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.organizacion.v1.core;

import com.dremo.ucsm.gsc.sigesmed.core.service.WebComponent;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.IComponentRegister;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.organizacion.v1.tx.EliminarOrganizacionTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.organizacion.v1.tx.InsertarOrganizacionTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.organizacion.v1.tx.ListarOrganizacionTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.organizacion.v1.tx.ActualizarOrganizacionTx;

/**
 *
 * @author ucsm
 */
public class ComponentRegister implements IComponentRegister{
    @Override
    public WebComponent createComponent(){
        //Asiganando el modulo al que pertenece
        WebComponent component = new WebComponent(Sigesmed.MODULO_CONFIGURACION);        
        
        //Registrando el Nombre del componente
        component.setName("organizacion");
        //Version del componente
        component.setVersion(1);
        //Lista de operaciones de logica, propias del componente
        component.addTransactionPOST("insertarOrganizacion", InsertarOrganizacionTx.class);
        component.addTransactionGET("listarOrganizaciones", ListarOrganizacionTx.class);
        component.addTransactionDELETE("eliminarOrganizacion", EliminarOrganizacionTx.class);
        component.addTransactionPUT("actualizarOrganizacion", ActualizarOrganizacionTx.class);
        
        return component;
    }
}
