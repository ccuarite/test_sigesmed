/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.login.v1.core;

import com.dremo.ucsm.gsc.sigesmed.core.service.WebComponent;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.IComponentRegister;
import com.dremo.ucsm.gsc.sigesmed.logic.login.v1.tx.SignInUsuarioTx;

/**
 *
 * @author ucsm
 */
public class ComponentRegister implements IComponentRegister{    
    @Override
    public WebComponent createComponent(){
        WebComponent component = new WebComponent(Sigesmed.MODULO_CONFIGURACION);
        component.setName("login");
        component.setVersion(1);
        component.setTransaction("signin", SignInUsuarioTx.class);
        
        return component;
    }
}
