/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.sub_modulo_sistema.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.SubModuloSistema;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class ListarSubModuloSistemaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<SubModuloSistema> subModulos = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar Sub modulos
            String hql = "SELECT sm FROM SubModuloSistema sm JOIN FETCH sm.moduloSistema WHERE sm.estReg!='E' ORDER BY sm.subModSisId" ;
            Query query = session.createQuery(hql);
            subModulos = query.list();
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar los Sub Modulos del Sistema", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */
        BaseArray miArray = new BaseArray();
        for(SubModuloSistema subModulo:subModulos ){
            BaseObject oResponse = new BaseObject();
            oResponse.addElement("subModuloID",subModulo.getSubModSisId() );
            oResponse.addElement("moduloID",subModulo.getModuloSistema().getModSisId() );
            oResponse.addElement("codigo",subModulo.getCod());
            oResponse.addElement("nombre",subModulo.getNom());
            oResponse.addElement("descripcion",subModulo.getDes());
            oResponse.addElement("icono",subModulo.getIco());
            oResponse.addElement("fecha",subModulo.getFecMod().toString());
            oResponse.addElement("estado",""+subModulo.getEstReg());
            miArray.addElement(oResponse);
        }
        
        return WebResponse.crearWebResponseExito("Se Listo correctamente",miArray);        
        //Fin
    }
    
}

