/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.tipo_organizacion.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.TipoOrganizacion;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class ListarTipoOrganizacionTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
            
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<TipoOrganizacion> tipoOrganizaciones = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar Tipo TipoTramites
            String hql = "SELECT to FROM TipoOrganizacion to WHERE to.estReg!='E'" ;
            Query query = session.createQuery(hql);
            tipoOrganizaciones = query.list();
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar los Tipo de Organizaciones", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */
        BaseArray miArray = new BaseArray();
        for(TipoOrganizacion tipoOrganizacion:tipoOrganizaciones ){
            BaseObject oResponse = new BaseObject();
            oResponse.addElement("tipoOrganizacionID",tipoOrganizacion.getTipOrgId() );
            oResponse.addElement("codigo",tipoOrganizacion.getCod());
            oResponse.addElement("nombre",tipoOrganizacion.getNom());
            oResponse.addElement("descripcion",tipoOrganizacion.getDes());
            oResponse.addElement("fecha",tipoOrganizacion.getFecMod().toString());
            oResponse.addElement("estado",""+tipoOrganizacion.getEstReg());
            miArray.addElement(oResponse);
        }
        
        return WebResponse.crearWebResponseExito("Se Listo correctamente",miArray);        
        //Fin
    }
    
}

