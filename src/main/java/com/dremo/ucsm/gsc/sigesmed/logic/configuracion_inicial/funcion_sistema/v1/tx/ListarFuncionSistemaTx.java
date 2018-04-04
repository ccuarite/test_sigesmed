/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.funcion_sistema.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.FuncionSistema;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class ListarFuncionSistemaTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<FuncionSistema> funciones = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar Funciones del sistema
            String hql = "SELECT f FROM FuncionSistema f JOIN FETCH f.subModuloSistema WHERE f.estReg!='E' ORDER BY f.funSisId" ;
            Query query = session.createQuery(hql);
            funciones = query.list();
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar las funciones del Sistema ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */
        BaseArray miArray = new BaseArray();
        for(FuncionSistema funcion:funciones ){
            BaseObject oResponse = new BaseObject();
            oResponse.addElement("funcionID",funcion.getFunSisId() );
            oResponse.addElement("subModuloID",funcion.getSubModuloSistema().getSubModSisId() );
            oResponse.addElement("nombre",funcion.getNom());
            oResponse.addElement("descripcion",funcion.getDes());
            oResponse.addElement("url",funcion.getUrl());
            oResponse.addElement("clave",funcion.getClaNav());
            oResponse.addElement("controlador",funcion.getNomCon());
            oResponse.addElement("interfaz",funcion.getNomInt());
            oResponse.addElement("icono",funcion.getIco());
            oResponse.addElement("fecha",funcion.getFecMod().toString());
            oResponse.addElement("estado",""+funcion.getEstReg());
            miArray.addElement(oResponse);
        }
        
        return WebResponse.crearWebResponseExito("Se Listo correctamente",miArray);        
        //Fin
    }
    
}

