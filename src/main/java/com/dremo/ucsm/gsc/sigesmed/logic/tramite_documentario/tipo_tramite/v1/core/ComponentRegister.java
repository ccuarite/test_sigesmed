/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.tramite_documentario.tipo_tramite.v1.core;

import com.dremo.ucsm.gsc.sigesmed.core.service.WebComponent;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Sigesmed;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.IComponentRegister;
import com.dremo.ucsm.gsc.sigesmed.logic.tramite_documentario.tipo_tramite.v1.tx.InsertarTipoTramiteTx;
import com.dremo.ucsm.gsc.sigesmed.logic.tramite_documentario.tipo_tramite.v1.tx.ListarTipoTramiteTx;
import com.dremo.ucsm.gsc.sigesmed.logic.tramite_documentario.tipo_tramite.v1.tx.EliminarTipoTramiteTx;
import com.dremo.ucsm.gsc.sigesmed.logic.tramite_documentario.tipo_tramite.v1.tx.ActualizarTipoTramiteTx;

/**
 *
 * @author ucsm
 */
public class ComponentRegister implements IComponentRegister{
    @Override
    public WebComponent createComponent(){
        //Asiganando el modulo al que pertenece
        WebComponent component = new WebComponent(Sigesmed.MODULO_TRAMITE_DOCUMENTARIO);        
        
        //Registrnado el Nombre del componente
        component.setName("tipoTramite");
        //Version del componente
        component.setVersion(1);
        //Lista de operaciones de logica, propias del componente
        component.addTransactionPOST("insertarTipoTramite", InsertarTipoTramiteTx.class);
        component.addTransactionGET("listarTipoTramite", ListarTipoTramiteTx.class);
        component.addTransactionDELETE("eliminarTipoTramite", EliminarTipoTramiteTx.class);
        component.addTransactionPUT("actualizarTipoTramite", ActualizarTipoTramiteTx.class);
        
        return component;
    }
}
