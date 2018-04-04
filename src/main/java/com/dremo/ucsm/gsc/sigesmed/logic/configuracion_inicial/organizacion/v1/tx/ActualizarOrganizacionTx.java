/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.organizacion.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.TipoOrganizacion;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Organizacion;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrador
 */
public class ActualizarOrganizacionTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
                
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        Organizacion organizacionAct = null;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            short tipoOrganizacionID = requestData.getElementShort("tipoOrganizacionID");
            int organizacionPadreID = requestData.getElementShort("organizacionPadreID");
            int organizacionID = requestData.getElementInteger("organizacionID");
            String codigo = requestData.getElementString("codigo");
            String nombre = requestData.getElementString("nombre");
            String alias = requestData.getElementString("alias");
            String descripcion = requestData.getElementString("descripcion");
            String direccion = requestData.getElementString("direccion");
            String estado = requestData.getElementString("estado");
            
            if(organizacionPadreID == 0)
                organizacionAct = new Organizacion(organizacionID,new TipoOrganizacion(tipoOrganizacionID), codigo, nombre, alias, descripcion,direccion, new Date(), 1, estado.charAt(0));
            else            
                organizacionAct = new Organizacion(organizacionID,new TipoOrganizacion(tipoOrganizacionID),new Organizacion(organizacionPadreID), codigo, nombre, alias, descripcion, direccion, new Date(), 1, estado.charAt(0), null,null);
        
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
            //actualizando Organizacion
            session.update(organizacionAct);   
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();            
            return WebResponse.crearWebResponseError("No se pudo actualizar la Organizacion", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */        
        return WebResponse.crearWebResponseExito("La Organizacion se actualizo correctamente");
        //Fin
    }
    
}
