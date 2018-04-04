/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.area.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Area;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Organizacion;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrador
 */
public class InsertarAreaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
                
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        Area nuevaArea = null;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            
            int organizacionID = requestData.getElementInteger("organizacionID");
            int areaPadreID = requestData.getElementShort("areaPadreID");
            String codigo = requestData.getElementString("codigo");
            String nombre = requestData.getElementString("nombre");
            String alias = requestData.getElementString("alias");
            String estado = requestData.getElementString("estado");
            
            if(areaPadreID == 0)
                nuevaArea = new Area(0,new Organizacion(organizacionID), codigo, nombre, alias, new Date(), 1, estado.charAt(0));            
            else
                nuevaArea = new Area(0,new Organizacion(organizacionID),new Area(areaPadreID), codigo, nombre, alias, new Date(), 1, estado.charAt(0));
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo registrar, datos incorrectos", e.getMessage() );
        }
        //Fin
        
        
        /*
        *  Parte para la operacion en la Base de Datos
        */        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction miTx = session.beginTransaction();
        try{
            session.persist( nuevaArea);
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();
            return WebResponse.crearWebResponseError("No se pudo registrar", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        
        /*
        *  Repuesta Correcta
        */
        BaseObject oResponse = new BaseObject();
        oResponse.addElement("areaID",nuevaArea.getAreId());
        oResponse.addElement("fecha",nuevaArea.getFecMod().toString());
        return WebResponse.crearWebResponseExito("El registro del Area se realizo correctamente", oResponse);
        //Fin
    }
    
}
