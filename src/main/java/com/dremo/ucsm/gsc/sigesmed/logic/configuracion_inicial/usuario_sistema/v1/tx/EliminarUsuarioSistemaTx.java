/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.usuario_sistema.v1.tx;

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
public class EliminarUsuarioSistemaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        int usuarioID = 0;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            usuarioID = requestData.getElementInteger("usuarioID");
        
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
            //eliminando usuario
            String hql = "UPDATE Usuario u SET u.estReg='E' WHERE u.usuId ="+usuarioID;
            
            Query query = session.createQuery(hql);
            query.executeUpdate();    
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();            
            return WebResponse.crearWebResponseError("No se pudo eliminar la usuario del Sistema, ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */        
        return WebResponse.crearWebResponseExito("La usuario del Sistema se elimino correctamente");
        //Fin
    }
}
