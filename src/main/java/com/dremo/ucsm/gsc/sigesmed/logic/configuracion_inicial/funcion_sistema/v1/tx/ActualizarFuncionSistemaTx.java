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
import com.dremo.ucsm.gsc.sigesmed.core.entity.FuncionSistema;
import com.dremo.ucsm.gsc.sigesmed.core.entity.SubModuloSistema;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrador
 */
public class ActualizarFuncionSistemaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
                
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        FuncionSistema funcionAct = null;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            int funcionID = requestData.getElementShort("funcionID");
            int subModuloID = requestData.getElementInteger("subModuloID");
            String nombre = requestData.getElementString("nombre");
            String descripcion = requestData.getElementString("descripcion");
            String url = requestData.getElementString("url");
            String clave = requestData.getElementString("clave");
            String controlador = requestData.getElementString("controlador");
            String interfaz = requestData.getElementString("interfaz");
            String icono = requestData.getElementString("icono");
            String estado = requestData.getElementString("estado");
            
            funcionAct = new FuncionSistema(funcionID,new SubModuloSistema(subModuloID), nombre, descripcion, url,clave,controlador,interfaz,icono,new Date(), 1, estado.charAt(0),null);
        
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
            //actualizando FuncionSistema
            session.update(funcionAct);   
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();            
            return WebResponse.crearWebResponseError("No se pudo actualizar la Funcion del Sistema, ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */        
        return WebResponse.crearWebResponseExito("La Funcion del Sistema se actualizo correctamente");
        //Fin
    }
    
}
