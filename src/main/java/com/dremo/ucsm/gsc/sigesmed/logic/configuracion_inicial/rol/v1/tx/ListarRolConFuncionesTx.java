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
import com.dremo.ucsm.gsc.sigesmed.core.entity.RolFuncion;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class ListarRolConFuncionesTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<Rol> roles = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar Roles con funciones
            String hql = "SELECT DISTINCT r FROM Rol r LEFT JOIN FETCH r.rolFunciones WHERE r.estReg!='E' " ;
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
            
            if( rol.getRolFunciones().size() > 0 ){
                BaseArray aFunciones = new BaseArray();
                for(RolFuncion rf:rol.getRolFunciones()){
                    BaseObject oFuncion = new BaseObject();
                    oFuncion.addElement("funcionID",rf.getFuncionSistema().getFunSisId() );
                    oFuncion.addElement("nombre",rf.getFuncionSistema().getNom());
                    aFunciones.addElement(oFuncion);
                }
                oResponse.addElement("funciones",aFunciones);
            }
            
            miArray.addElement(oResponse);
        }
        
        return WebResponse.crearWebResponseExito("Se Listo correctamente",miArray);        
        //Fin
    }
    
}

