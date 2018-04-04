/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.usuario_sistema.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Usuario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class ListarUsuarioSistemaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<Usuario> usuarios = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar usuarios
            String hql = "SELECT u FROM Usuario u JOIN FETCH u.rol JOIN FETCH u.organizacion WHERE u.estReg!='E' " ;
            Query query = session.createQuery(hql);
            usuarios = query.list();
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar los usuarios del Sistema ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */
        BaseArray miArray = new BaseArray();
        for(Usuario usuario:usuarios ){
            BaseObject oResponse = new BaseObject();
            oResponse.addElement("usuarioID",usuario.getUsuId() );
            oResponse.addElement("nombre",usuario.getNom());
            oResponse.addElement("password",usuario.getPas());
            oResponse.addElement("rolID",usuario.getRol().getRolId() );
            oResponse.addElement("rol",usuario.getRol().getNom() );
            
            oResponse.addElement("organizacionID",usuario.getOrganizacion().getOrgId() );
            oResponse.addElement("organizacion",usuario.getOrganizacion().getNom() );
            
            
            oResponse.addElement("estado",""+usuario.getEstReg());
            miArray.addElement(oResponse);
        }
        
        return WebResponse.crearWebResponseExito("Se Listo correctamente",miArray);        
        //Fin
    }
    
}

