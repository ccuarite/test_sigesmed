/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.tramite_documentario.tipo_tramite.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrador
 */
public class EliminarTipoTramiteTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        int tipoTramiteID = 0;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            tipoTramiteID = requestData.getElementInteger("tipoTramiteID");
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo eliminar, datos incorrectos", e.getMessage() );
        }
        //Fin
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction miTx = session.beginTransaction();
        try{
            //eliminando tipo tramite
            String hql = "UPDATE TipoTramite t SET t.estReg='E' WHERE t.tipTraId ="+tipoTramiteID;
            
            Query query = session.createQuery(hql);
            query.executeUpdate();    
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();            
            return WebResponse.crearWebResponseError("No se pudo eliminar Tipo TipoTramite, ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */        
        return WebResponse.crearWebResponseExito("El Tipo TipoTramite se elimino correctamente");
        //Fin
    }
}
