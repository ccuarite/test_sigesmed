/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.tramite_documentario.tipo_tramite.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Area;
import com.dremo.ucsm.gsc.sigesmed.core.entity.RequisitoTramite;
import com.dremo.ucsm.gsc.sigesmed.core.entity.RutaTramite;
import com.dremo.ucsm.gsc.sigesmed.core.entity.TipoOrganizacion;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.TipoTramite;
import com.dremo.ucsm.gsc.sigesmed.core.service.ServicioREST;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.FileBaseObject;
import com.dremo.ucsm.gsc.sigesmed.util.BuildFile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrador
 */
public class InsertarTipoTramiteTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
                
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        TipoTramite nuevoTipoTramite = null;
        List<FileBaseObject> listaArchivos = new ArrayList<FileBaseObject>();
        try{
            /*
            String aTipTra[] = {"Nom","Des","Dur","Cos","Tip","TipOrgId"};
            String eTipTra[] = {"nombre","descripcion","duracion","costo","tipo","tipoOrganizacionID"};
            Class tTipTra[] = {String.class,String.class,Short.class,Double.class,Boolean.class,Short.class};*/
            
            
            BaseObject requestData = (BaseObject)wr.getData();
            String nombre = requestData.getElementString("nombre");
            String descripcion = requestData.getElementString("descripcion");
            short duracion = requestData.getElementShort("duracion");
            double costo = requestData.getElementDouble("costo");
            boolean tipo = requestData.getElementBoolean("tipo");
            short tipoOrganizacion = requestData.getElementShort("tipoOrganizacionID");
            BaseArray listaRequisitos = requestData.getElementBaseArray("requisitos");
            BaseArray listaRutas = requestData.getElementBaseArray("rutas");
            
            nuevoTipoTramite = new TipoTramite(0, "asd", nombre, descripcion, duracion, new BigDecimal(costo), tipo, new Date(), 1, 'A', new TipoOrganizacion(tipoOrganizacion));
            
            String atributosReq[] = {"Des","NomArcAdj"};
            String etiquetasReq[] = {"descripcion","archivo"};
            Class tiposReq[] = {String.class,String.class};
            
            //leendo los requisitos            
            if(listaRequisitos.getList().size() > 0){
                nuevoTipoTramite.setRequisitoTramites( new ArrayList<RequisitoTramite>());
                for( Object o : listaRequisitos.getList() ){
                    BaseObject bo = (BaseObject)o;

                    String nombreArchivo = "";
                    String rutaDescripcion = bo.getElementString("descripcion");
                    
                    //verificamos si existe un archivo adjunto al requisito
                    if( bo.getElementObject("archivo") instanceof BaseObject ){
                        FileBaseObject miF = new FileBaseObject( bo.getElementBaseObject("archivo") );
                        nombreArchivo = miF.getName();
                        listaArchivos.add(miF);
                    }

                    nuevoTipoTramite.getRequisitoTramites().add( new RequisitoTramite((short)0, nuevoTipoTramite,rutaDescripcion,nombreArchivo,new Date(),1,'A') );
                }
            }
            //leendo las rutas
            if(listaRutas.getList().size() > 0){
                nuevoTipoTramite.setRutaTramites( new ArrayList<RutaTramite>() );
                for(Object o : listaRutas.getList()){
                    BaseObject bo = (BaseObject)o;

                    int areaOriID = bo.getElementInteger("areaOriID");
                    int areaDesID = bo.getElementInteger("areaDesID");
                    String areaDescripcion = bo.getElementString("descripcion");

                    nuevoTipoTramite.getRutaTramites().add( new RutaTramite((short)0,nuevoTipoTramite,areaDescripcion, new Area(areaOriID),new Area(areaDesID), new Date(), 1,'A'  ) );
                }
            }
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo registrar, datos incorrectos", e.getMessage() );
        }
        //Fin
        
        /*
        *   Parte de Logica de Negocio
        *   descripcion: El Sitema debe generar el codigo para el nuevo Tipo de Tramite antes de insertar a la BD
        */
        nuevoTipoTramite.setCod("COD-01");
        
        //Fin
        
        /*
        *  Parte para la operacion en la Base de Datos
        */        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction miTx = session.beginTransaction();
        try{
            //insertanto mi tipo Tramite
            session.persist( nuevoTipoTramite);
            miTx.commit();
        
        }catch(Exception e){
            miTx.rollback();
            return WebResponse.crearWebResponseError("No se pudo registrar el tipo de tramite ", e.getMessage() );
        }
        finally{
            session.close();
        }
        //Fin
        
        //si ya se registro el tipo de tramite 
        //ahora creamos los archivos que se desean subir
        for(FileBaseObject archivo : listaArchivos){
            BuildFile.buildFromBase64(ServicioREST.PATH_SIGESMED+"/archivos", archivo.getName(), archivo.getData());
        }
        /*
        *  Repuesta Correcta
        */
        BaseObject oResponse = new BaseObject();
        oResponse.addElement("tipoTramiteID",nuevoTipoTramite.getTipTraId());
        oResponse.addElement("codigo",nuevoTipoTramite.getCod());
        return WebResponse.crearWebResponseExito("El registro de Tipo Tramite se realizo correctamente", oResponse);
        //Fin
    }
    
}
