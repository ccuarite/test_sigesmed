/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.rol.v1.core;

import com.dremo.ucsm.gsc.sigesmed.core.service.WebComponent;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.IComponentRegister;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.rol.v1.tx.EliminarRolTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.rol.v1.tx.InsertarRolTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.rol.v1.tx.ListarRolTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.rol.v1.tx.ActualizarRolTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.rol.v1.tx.ListarRolConFuncionesTx;


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
        component.setName("rol");
        //Version del componente
        component.setVersion(1);
        //Lista de operaciones de logica, propias del componente
        component.addTransactionPOST("insertarRol", InsertarRolTx.class);
        component.addTransactionGET("listarRoles", ListarRolTx.class);
        component.addTransactionGET("listarRolesConFunciones", ListarRolConFuncionesTx.class);
        component.addTransactionDELETE("eliminarRol", EliminarRolTx.class);
        component.addTransactionPUT("actualizarRol", ActualizarRolTx.class);
        
        return component;
    }
}
