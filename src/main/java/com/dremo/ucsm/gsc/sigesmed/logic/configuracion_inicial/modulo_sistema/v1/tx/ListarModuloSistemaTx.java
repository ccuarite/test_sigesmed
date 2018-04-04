/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.modulo_sistema.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.ModuloSistema;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class ListarModuloSistemaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        try{
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar los Modulos del sistema", e.getMessage() );
        }
        //Fin        
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<ModuloSistema> modulos = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar Tipo TipoTramites
            String hql = "SELECT m FROM ModuloSistema m WHERE m.estReg!='E' ORDER BY m.modSisId" ;
            Query query = session.createQuery(hql);
            modulos = query.list();
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar los Modulos del Sistema, ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */
        BaseArray miArray = new BaseArray();
        for(ModuloSistema modulo:modulos ){
            BaseObject oResponse = new BaseObject();
            oResponse.addElement("moduloID",modulo.getModSisId() );
            oResponse.addElement("codigo",modulo.getCod());
            oResponse.addElement("nombre",modulo.getNom());
            oResponse.addElement("descripcion",modulo.getDes());
            oResponse.addElement("icono",modulo.getIco());
            oResponse.addElement("fecha",modulo.getFecMod().toString());
            oResponse.addElement("estado",""+modulo.getEstReg());
            miArray.addElement(oResponse);
        }
        
        return WebResponse.crearWebResponseExito("Se Listo correctamente",miArray);        
        //Fin
    }
    
}

