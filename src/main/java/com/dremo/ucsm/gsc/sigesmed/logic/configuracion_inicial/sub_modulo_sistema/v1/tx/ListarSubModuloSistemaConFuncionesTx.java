/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.configuracion_inicial.sub_modulo_sistema.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.FuncionSistema;
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
public class ListarSubModuloSistemaConFuncionesTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<SubModuloSistema> subModulos = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar sub modulos con sus funciones
            String hql = "SELECT DISTINCT sm FROM SubModuloSistema sm LEFT JOIN FETCH sm.funcionSistemas f WHERE sm.estReg!='E' and f.estReg!='E' ORDER BY sm.subModSisId ASC, f.funSisId ASC" ;
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
            oResponse.addElement("codigo",subModulo.getCod());
            oResponse.addElement("nombre",subModulo.getNom());
            
            
            if( subModulo.getFuncionSistemas().size() > 0 ){
                BaseArray aFunciones = new BaseArray();
                for(FuncionSistema funcion:subModulo.getFuncionSistemas()){
                    BaseObject oFuncion = new BaseObject();
                    oFuncion.addElement("funcionID",funcion.getFunSisId() );
                    oFuncion.addElement("nombre",funcion.getNom());
                    oFuncion.addElement("icono",funcion.getIco());
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

