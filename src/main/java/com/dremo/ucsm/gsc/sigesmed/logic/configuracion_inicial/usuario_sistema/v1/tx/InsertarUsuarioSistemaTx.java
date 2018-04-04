/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.usuario_sistema.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Organizacion;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Rol;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Usuario;
import com.dremo.ucsm.gsc.sigesmed.core.entity.SubModuloSistema;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrador
 */
public class InsertarUsuarioSistemaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
                
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        Usuario nuevoUsuario = null;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            int rolID = requestData.getElementInteger("rolID");
            int organizacionID = requestData.getElementInteger("organizacionID");
            String nombre = requestData.getElementString("nombre");
            String password = requestData.getElementString("password");
            String estado = requestData.getElementString("estado");
            nuevoUsuario = new Usuario(0, new Organizacion(organizacionID), new Rol(rolID), nombre, password, new Date(), new Date(), 1, estado.charAt(0));
            
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo registrar, datos incorrectos", e.getMessage() );
        }
        //Fin
        
        
        /*
        *  Parte para la operacion en la Base de Datos
        */        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction miTx = session.beginTransaction();
        try{
            session.persist( nuevoUsuario);
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();
            return WebResponse.crearWebResponseError("No se pudo registrar, ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        
        /*
        *  Repuesta Correcta
        */
        BaseObject oResponse = new BaseObject();
        oResponse.addElement("usuarioID",nuevoUsuario.getUsuId());
        return WebResponse.crearWebResponseExito("El registro de la Usuario se realizo correctamente", oResponse);
        //Fin
    }
    
}
