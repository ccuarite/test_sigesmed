/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.rol.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.FuncionSistema;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Rol;
import com.dremo.ucsm.gsc.sigesmed.core.entity.RolFuncion;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrador
 */
public class InsertarRolTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        Rol nuevoRol = null;
        
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            String abreviatura = requestData.getElementString("abreviatura");
            String nombre = requestData.getElementString("nombre");
            String descripcion = requestData.getElementString("descripcion");
            String estado = requestData.getElementString("estado");
            
            nuevoRol = new Rol(0, abreviatura, nombre, descripcion,new Date(), 1, estado.charAt(0), null,null);
            nuevoRol.setRolFunciones( new ArrayList<RolFuncion>() );
            
            short i =1;            
            for(Object bo: requestData.getElementBaseArray("funciones").getList()){
                nuevoRol.getRolFunciones().add( new RolFuncion(new FuncionSistema( Integer.parseInt( ((BaseObject)bo).getElementString("funcionID") ) ) , nuevoRol,i++ ) );
            }
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo registrar, datos incorrectos", e.getMessage() );
        }
        //Fin
        
        
        /*
        *  Parte para la operacion en la Base de Datos
        */        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction miTx = null;
        String hql = "";
        try{
            miTx = session.beginTransaction();
            session.persist(nuevoRol);
            miTx.commit();
            /*
            miTx = session.beginTransaction();
            int i=1;
            for(Integer funcionId: funciones){
                hql = "INSERT INTO rol_funcion (rol_id,fun_sis_id,num) VALUES (:1,:2,:3)";
            
                SQLQuery query = session.createSQLQuery(hql);
                query.setParameter("1", nuevoRol.getRolId() );
                query.setParameter("2", funcionId );
                query.setParameter("3", i++ );
                query.executeUpdate();
            }
            miTx.commit();*/
        
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
        oResponse.addElement("rolID",nuevoRol.getRolId());
        oResponse.addElement("fecha",nuevoRol.getFecMod().toString());
        return WebResponse.crearWebResponseExito("El registro del Rol se realizo correctamente", oResponse);
        //Fin
    }
    
}
