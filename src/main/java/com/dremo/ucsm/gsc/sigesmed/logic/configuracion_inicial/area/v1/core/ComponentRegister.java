/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.area.v1.core;

import com.dremo.ucsm.gsc.sigesmed.core.service.WebComponent;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.IComponentRegister;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.area.v1.tx.EliminarAreaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.area.v1.tx.InsertarAreaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.area.v1.tx.ListarAreaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.area.v1.tx.ListarAreaPorOrganizacionTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.area.v1.tx.ActualizarAreaTx;

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
        component.setName("area");
        //Version del componente
        component.setVersion(1);
        //Lista de operaciones de logica, propias del componente
        component.addTransactionPOST("insertarArea", InsertarAreaTx.class);
        component.addTransactionGET("listarAreas", ListarAreaTx.class);
        component.addTransactionGET("listarAreasPorOrganizacion", ListarAreaPorOrganizacionTx.class);
        component.addTransactionDELETE("eliminarArea", EliminarAreaTx.class);
        component.addTransactionPUT("actualizarArea", ActualizarAreaTx.class);
        
        return component;
    }
}
