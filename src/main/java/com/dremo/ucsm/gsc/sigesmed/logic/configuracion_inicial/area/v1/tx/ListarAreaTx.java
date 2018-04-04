/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.area.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Area;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class ListarAreaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<Area> areas = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar las Areaes
            String hql = "SELECT a FROM Area a JOIN FETCH a.organizacion LEFT JOIN FETCH a.areaPadre WHERE a.estReg!='E'" ;
            Query query = session.createQuery(hql);
            areas = query.list();
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar las Areas", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */
        BaseArray miArray = new BaseArray();
        for(Area area:areas ){
            BaseObject oResponse = new BaseObject();
            oResponse.addElement("areaID",area.getAreId() );
            oResponse.addElement("organizacionID",area.getOrganizacion().getOrgId() );
            if(area.getAreaPadre()!=null)
                oResponse.addElement("areaPadreID",area.getAreaPadre().getAreId() );
            oResponse.addElement("codigo",area.getCod());
            oResponse.addElement("nombre",area.getNom());
            oResponse.addElement("alias",area.getAli());
            oResponse.addElement("fecha",area.getFecMod().toString());
            oResponse.addElement("estado",""+area.getEstReg());
            miArray.addElement(oResponse);
        }
        
        return WebResponse.crearWebResponseExito("Se Listo correctamente",miArray);        
        //Fin
    }
    
}

