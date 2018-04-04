/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.organizacion.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Organizacion;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class ListarOrganizacionTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<Organizacion> organizaciones = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar las Organizaciones
            String hql = "SELECT o FROM Organizacion o JOIN FETCH o.tipoOrganizacion to LEFT JOIN FETCH o.organizacionPadre WHERE o.estReg!='E' ORDER BY to.tipOrgId" ;
            Query query = session.createQuery(hql);
            organizaciones = query.list();
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar las Organizaciones", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */
        BaseArray miArray = new BaseArray();
        for(Organizacion organizacion:organizaciones ){
            BaseObject oResponse = new BaseObject();
            oResponse.addElement("organizacionID",organizacion.getOrgId() );
            oResponse.addElement("tipoOrganizacionID",organizacion.getTipoOrganizacion().getTipOrgId() );
            oResponse.addElement("tipoOrganizacion",organizacion.getTipoOrganizacion().getNom() );
            if(organizacion.getOrganizacionPadre()!=null)
                oResponse.addElement("organizacionPadreID",organizacion.getOrganizacionPadre().getOrgId() );
            oResponse.addElement("codigo",organizacion.getCod());
            oResponse.addElement("nombre",organizacion.getNom());
            oResponse.addElement("alias",organizacion.getAli());
            oResponse.addElement("descripcion",organizacion.getDes());
            oResponse.addElement("direccion",organizacion.getDir());
            oResponse.addElement("fecha",organizacion.getFecMod().toString());
            oResponse.addElement("estado",""+organizacion.getEstReg());
            miArray.addElement(oResponse);
        }
        
        return WebResponse.crearWebResponseExito("Se Listo correctamente",miArray);        
        //Fin
    }
    
}

