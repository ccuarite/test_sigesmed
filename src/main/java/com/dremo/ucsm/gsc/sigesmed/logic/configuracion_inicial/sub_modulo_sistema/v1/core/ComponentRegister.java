/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.sub_modulo_sistema.v1.core;

import com.dremo.ucsm.gsc.sigesmed.core.service.WebComponent;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.IComponentRegister;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.sub_modulo_sistema.v1.tx.EliminarSubModuloSistemaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.sub_modulo_sistema.v1.tx.InsertarSubModuloSistemaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.sub_modulo_sistema.v1.tx.ListarSubModuloSistemaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.sub_modulo_sistema.v1.tx.ActualizarSubModuloSistemaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.sub_modulo_sistema.v1.tx.ListarSubModuloSistemaConFuncionesTx;

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
        component.setName("subModuloSistema");
        //Version del componente
        component.setVersion(1);
        //Lista de operaciones de logica, propias del componente
        component.addTransactionPOST("insertarSubModulo", InsertarSubModuloSistemaTx.class);
        component.addTransactionGET("listarSubModulos", ListarSubModuloSistemaTx.class);
        component.addTransactionGET("listarSubModulosConFunciones", ListarSubModuloSistemaConFuncionesTx.class);
        component.addTransactionDELETE("eliminarSubModulo", EliminarSubModuloSistemaTx.class);
        component.addTransactionPUT("actualizarSubModulo", ActualizarSubModuloSistemaTx.class);
        
        return component;
    }
}
