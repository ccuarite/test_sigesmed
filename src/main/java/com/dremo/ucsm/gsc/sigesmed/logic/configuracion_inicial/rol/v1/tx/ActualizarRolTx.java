/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.rol.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.FuncionSistema;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Rol;
import com.dremo.ucsm.gsc.sigesmed.core.entity.RolFuncion;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrador
 */
public class ActualizarRolTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
                
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        Rol actualizarRol = null;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            int rolID = requestData.getElementInteger("rolID");
            String abreviatura = requestData.getElementString("abreviatura");
            String nombre = requestData.getElementString("nombre");
            String descripcion = requestData.getElementString("descripcion");
            String estado = requestData.getElementString("estado");
            
            actualizarRol = new Rol(rolID, abreviatura, nombre, descripcion,new Date(), 1, estado.charAt(0), null,null);
            
            actualizarRol.setRolFunciones(new ArrayList<RolFuncion>());
            
            short i =1;
            for(Object bo: requestData.getElementBaseArray("funciones").getList()){
                actualizarRol.getRolFunciones().add( new RolFuncion(new FuncionSistema( Integer.parseInt( ((BaseObject)bo).getElementString("funcionID") ) ) , actualizarRol,i++ ) );
            }
        
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
            
            String hql = "DELETE FROM rol_funcion t WHERE t.rol_id="+actualizarRol.getRolId();
            SQLQuery query = session.createSQLQuery(hql);
            query.executeUpdate();
            
            
            //actualizando Rol
            session.update(actualizarRol);            
            
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();            
            return WebResponse.crearWebResponseError("No se pudo actualizar el Rol", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */        
        return WebResponse.crearWebResponseExito("El Rol se actualizo correctamente");
        //Fin
    }
    
}
