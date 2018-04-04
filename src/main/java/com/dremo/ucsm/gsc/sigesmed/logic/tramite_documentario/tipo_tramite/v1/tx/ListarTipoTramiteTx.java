/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.tramite_documentario.tipo_tramite.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.RequisitoTramite;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.TipoTramite;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class ListarTipoTramiteTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        try{
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar los Tipos TipoTramites", e.getMessage() );
        }
        //Fin        
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        List<TipoTramite> tramites = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            //listar Tipo TipoTramites
            String hql = "SELECT DISTINCT tt FROM TipoTramite tt JOIN FETCH tt.tipoOrganizacion LEFT JOIN FETCH tt.requisitoTramites WHERE tt.estReg!='E'";
            Query query = session.createQuery(hql);
            tramites = query.list();
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo Listar los Tipos TipoTramites, ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        /*
        *  Repuesta Correcta
        */
        BaseArray miArray = new BaseArray();
        for(TipoTramite tramite:tramites ){
            BaseObject oResponse = new BaseObject();
            oResponse.addElement("tipoTramiteID",tramite.getTipTraId());
            oResponse.addElement("codigo",tramite.getCod());
            oResponse.addElement("nombre",tramite.getNom());
            oResponse.addElement("descripcion",tramite.getDes());
            oResponse.addElement("duracion",tramite.getDur());
            oResponse.addElement("costo",tramite.getCos());            
            oResponse.addElement("tipo",tramite.getTip());
            
            oResponse.addElement("tipoOrganizacionID",tramite.getTipoOrganizacion().getTipOrgId());
            oResponse.addElement("tipoOrganizacion",tramite.getTipoOrganizacion().getNom());
            
            
            if( tramite.getRequisitoTramites().size() > 0 ){
                BaseArray aRequisitos = new BaseArray();
                for(RequisitoTramite rt:tramite.getRequisitoTramites()){
                    BaseObject oRequisito = new BaseObject();
                    oRequisito.addElement("descripcion",rt.getDes() );
                    oRequisito.addElement("nombreArchivo",rt.getNomArcAdj());
                    aRequisitos.addElement(oRequisito);
                }
                oResponse.addElement("requisitos",aRequisitos);
            }
            
            miArray.addElement(oResponse);
        }
        
        return WebResponse.crearWebResponseExito("Se Listo correctamente",miArray);        
        //Fin
    }
    
}

