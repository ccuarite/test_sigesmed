/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.funcion_sistema.v1.tx;

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
public class EliminarFuncionSistemaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        int funcionID = 0;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            funcionID = requestData.getElementInteger("funcionID");
        
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
            //eliminando funcion sitema
            String hql = "UPDATE FuncionSistema f SET f.estReg='E' WHERE f.funSisId ="+funcionID;
            
            Query query = session.createQuery(hql);
            query.executeUpdate();    
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();            
            return WebResponse.crearWebResponseError("No se pudo eliminar la funcion del Sistema, ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */        
        return WebResponse.crearWebResponseExito("La funcion del Sistema se elimino correctamente");
        //Fin
    }
}
