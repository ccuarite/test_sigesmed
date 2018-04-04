/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.modulo_sistema.v1.core;

import com.dremo.ucsm.gsc.sigesmed.core.service.WebComponent;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.IComponentRegister;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.modulo_sistema.v1.tx.EliminarModuloSistemaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.modulo_sistema.v1.tx.InsertarModuloSistemaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.modulo_sistema.v1.tx.ListarModuloSistemaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.modulo_sistema.v1.tx.ActualizarModuloSistemaTx;


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
        component.setName("moduloSistema");
        //Version del componente
        component.setVersion(1);
        //Lista de operaciones de logica, propias del componente
        component.addTransactionPOST("insertarModulo", InsertarModuloSistemaTx.class);
        component.addTransactionGET("listarModulos", ListarModuloSistemaTx.class);
        component.addTransactionDELETE("eliminarModulo", EliminarModuloSistemaTx.class);
        component.addTransactionPUT("actualizarModulo", ActualizarModuloSistemaTx.class);
        
        return component;
    }
}
