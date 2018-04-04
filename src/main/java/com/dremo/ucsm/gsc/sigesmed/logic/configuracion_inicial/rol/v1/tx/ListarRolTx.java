/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.rol.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Rol;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class ListarRolTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        try{
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar los Roles", e.getMessage() );
        }
        //Fin        
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<Rol> roles = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar Roles
            String hql = "FROM Rol WHERE estReg!='E' " ;
            Query query = session.createQuery(hql);
            roles = query.list();
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar los Roles", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */
        BaseArray miArray = new BaseArray();
        for(Rol rol:roles ){
            BaseObject oResponse = new BaseObject();
            oResponse.addElement("rolID",rol.getRolId() );
            oResponse.addElement("abreviatura",rol.getAbr());
            oResponse.addElement("nombre",rol.getNom());
            oResponse.addElement("descripcion",rol.getDes());
            oResponse.addElement("fecha",rol.getFecMod().toString());
            oResponse.addElement("estado",""+rol.getEstReg());
            miArray.addElement(oResponse);
        }
        
        return WebResponse.crearWebResponseExito("Se Listo correctamente",miArray);        
        //Fin
    }
    
}

