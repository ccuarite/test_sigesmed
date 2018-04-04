/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.funcion_sistema.v1.core;

import com.dremo.ucsm.gsc.sigesmed.core.service.WebComponent;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.IComponentRegister;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.funcion_sistema.v1.tx.EliminarFuncionSistemaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.funcion_sistema.v1.tx.InsertarFuncionSistemaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.funcion_sistema.v1.tx.ListarFuncionSistemaTx;
import com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.funcion_sistema.v1.tx.ActualizarFuncionSistemaTx;

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
        component.setName("funcionSistema");
        //Version del componente
        component.setVersion(1);
        //Lista de operaciones de logica, propias del componente
        component.addTransactionPOST("insertarFuncion", InsertarFuncionSistemaTx.class);
        component.addTransactionGET("listarFunciones", ListarFuncionSistemaTx.class);
        component.addTransactionDELETE("eliminarFuncion", EliminarFuncionSistemaTx.class);
        component.addTransactionPUT("actualizarFuncion", ActualizarFuncionSistemaTx.class);
        
        return component;
    }
}
