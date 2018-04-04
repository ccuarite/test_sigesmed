/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.tramite_documentario.tipo_tramite.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.TipoOrganizacion;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.TipoTramite;
import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrador
 */
public class ActualizarTipoTramiteTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
                
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        TipoTramite actualizarTipoTramite = null;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            int id = requestData.getElementInteger("tipoTramiteID");
            String nombre = requestData.getElementString("nombre");
            String codigo = requestData.getElementString("codigo");
            String descripcion = requestData.getElementString("descripcion");
            short duracion = requestData.getElementShort("duracion");
            double costo = requestData.getElementDouble("costo");
            boolean tipo = requestData.getElementBoolean("tipo");
            
            short tipoOrganizacion = requestData.getElementShort("tipoOrganizacionID");
            
            actualizarTipoTramite = new TipoTramite(id, codigo, nombre, descripcion, duracion, new BigDecimal(costo), tipo, new Date(), 1, 'A', new TipoOrganizacion(tipoOrganizacion));
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo actualizar, datos incorrectos", e.getMessage() );
        }
        //Fin
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction miTx = session.beginTransaction();
        try{
            //actualizando TipoTramite
            session.update(actualizarTipoTramite);   
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();            
            return WebResponse.crearWebResponseError("No se pudo actualizar Tipo TipoTramite, ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */        
        return WebResponse.crearWebResponseExito("El Tipo TipoTramite se actualizo correctamente");
        //Fin
    }
    
}
