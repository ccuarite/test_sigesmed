/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.tipo_organizacion.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.TipoOrganizacion;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrador
 */
public class ActualizarTipoOrganizacionTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
                
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        TipoOrganizacion actualizarTipoOrganizacion = null;
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            short tipoOrganizacionID = requestData.getElementShort("tipoOrganizacionID");
            String codigo = requestData.getElementString("codigo");
            String nombre = requestData.getElementString("nombre");
            String descripcion = requestData.getElementString("descripcion");
            String estado = requestData.getElementString("estado");
            
            actualizarTipoOrganizacion = new TipoOrganizacion(tipoOrganizacionID, codigo, nombre, descripcion, new Date(), 1, estado.charAt(0), null);
            //public TipoOrganizacion(short tipOrgId, String cod, String nom, String des, Date fecMod, int usuMod, char estReg, null)
        
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
            //actualizando TipoOrganizacion
            session.update(actualizarTipoOrganizacion);   
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();            
            return WebResponse.crearWebResponseError("No se pudo actualizar Tipo de Organizacion, ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */        
        return WebResponse.crearWebResponseExito("El Tipo de Organizacion se actualizo correctamente");
        //Fin
    }
    
}
