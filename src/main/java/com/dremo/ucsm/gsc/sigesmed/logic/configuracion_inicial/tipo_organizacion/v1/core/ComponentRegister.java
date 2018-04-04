/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.tipo_organizacion.v1.core;

import com.dremo.ucsm.gsc.sigesmed.core.service.WebComponent;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.IComponentRegister;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.tipo_organizacion.v1.tx.EliminarTipoOrganizacionTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.tipo_organizacion.v1.tx.InsertarTipoOrganizacionTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.tipo_organizacion.v1.tx.ListarTipoOrganizacionTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.tipo_organizacion.v1.tx.ActualizarTipoOrganizacionTx;


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
        component.setName("tipoOrganizacion");
        //Version del componente
        component.setVersion(1);
        //Lista de operaciones de logica, propias del componente
        component.addTransactionPOST("insertarTipoOrganizacion", InsertarTipoOrganizacionTx.class);
        component.addTransactionGET("listarTipoOrganizaciones", ListarTipoOrganizacionTx.class);
        component.addTransactionDELETE("eliminarTipoOrganizacion", EliminarTipoOrganizacionTx.class);
        component.addTransactionPUT("actualizarTipoOrganizacion", ActualizarTipoOrganizacionTx.class);
        
        return component;
    }
}
