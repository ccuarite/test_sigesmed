/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.sub_modulo_sistema.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.ModuloSistema;
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
public class InsertarSubModuloSistemaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
                
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        SubModuloSistema nuevoSubModulo = null;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            short moduloID = requestData.getElementShort("moduloID");
            String codigo = requestData.getElementString("codigo");
            String nombre = requestData.getElementString("nombre");
            String descripcion = requestData.getElementString("descripcion");
            String icono = requestData.getElementString("icono");
            String estado = requestData.getElementString("estado");
            nuevoSubModulo = new SubModuloSistema(0,new ModuloSistema(moduloID), codigo, nombre, descripcion,icono, new Date(), 1, estado.charAt(0), null);
        
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
            session.persist( nuevoSubModulo);
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
        oResponse.addElement("subModuloID",nuevoSubModulo.getSubModSisId());
        oResponse.addElement("fecha",nuevoSubModulo.getFecMod().toString());
        return WebResponse.crearWebResponseExito("El registro Sub Modulo se realizo correctamente", oResponse);
        //Fin
    }
    
}
