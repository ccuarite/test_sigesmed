/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.logic.login.v1.tx;

import com.dremo.ucsm.gsc.sigesmed.core.datastore.HibernateUtil;
import com.dremo.ucsm.gsc.sigesmed.core.entity.FuncionSistema;
import com.dremo.ucsm.gsc.sigesmed.core.entity.ModuloSistema;
import com.dremo.ucsm.gsc.sigesmed.core.entity.SubModuloSistema;
import com.dremo.ucsm.gsc.sigesmed.core.security.TokenHandler;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebRequest;
import com.dremo.ucsm.gsc.sigesmed.core.service.WebResponse;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import com.dremo.ucsm.gsc.sigesmed.core.entity.Usuario;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrador
 */
public class SignInUsuarioTx implements ITransaction{

    @Override
    public WebResponse execute(WebRequest wr) {
        
        /*
        *   Parte para la lectura, verificacion y validacion de datos
        */
        String username = "";
        String password = "";
        try{
            BaseObject requestData = (BaseObject)wr.getData();
            username = requestData.getElementString("nombre");
            password = requestData.getElementString("password");        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("Los datos enviados son incorrectos", e.getMessage() );
        }
        
        /*
        *  Parte para la operacion en la Base de Datos
        */
        Usuario usuario = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        //consulta para buscar al usuario por su nombre y contraseÃ±a
        String hql = "SELECT u FROM Usuario u JOIN FETCH u.organizacion JOIN FETCH u.rol r WHERE u.nom =:nombre AND u.pas =:password and u.estReg='A'";
        try{
            Query query = session.createQuery(hql);
            query.setParameter("nombre", username);
            query.setParameter("password", password);
            query.setMaxResults(1);
            //buscando 
            usuario =  (Usuario)query.uniqueResult(); 
        
        }catch(Exception e){
            return WebResponse.crearWebResponseError("No se pudo registrar, ", e.getMessage() );
        }
        finally{
            session.close();
        }
        
        
        //validar el usuario
        if(usuario != null){
            
            List<ModuloSistema> modulos = new ArrayList<ModuloSistema>();
            session = HibernateUtil.getSessionFactory().openSession();
            try{
                hql = "SELECT rf.claveRolFuncion.funcionSistema FROM RolFuncion rf JOIN FETCH rf.claveRolFuncion.funcionSistema.subModuloSistema sm JOIN FETCH sm.moduloSistema WHERE rf.claveRolFuncion.rol.rolId=?1 ";
                
                Query query = session.createQuery(hql);
                query.setParameter("1", usuario.getRol().getRolId());
                List<FuncionSistema> funciones = query.list();
                
                for(FuncionSistema f :funciones)                    
                    buscarModulo(modulos,f);

            }catch(Exception e){
                return WebResponse.crearWebResponseError("No se pudo Listar los modulos del Sistema ", e.getMessage() );
            }
            finally{
                session.close();
            }
            
            BaseArray aModulos = new BaseArray();
            for(ModuloSistema modulo:modulos ){
                BaseObject oModulo = new BaseObject();
                oModulo.addElement("moduloID",modulo.getModSisId() );
                oModulo.addElement("nombre",modulo.getNom());
                oModulo.addElement("icono",modulo.getIco());
                
                if( modulo.getSubModuloSistemas().size() > 0 ){
                    BaseArray aSubModulos = new BaseArray();
                    for(SubModuloSistema subModulo:modulo.getSubModuloSistemas()){
                        BaseObject oSubModulo = new BaseObject();
                        oSubModulo.addElement("subModuloID",subModulo.getSubModSisId() );
                        oSubModulo.addElement("nombre",subModulo.getNom());
                        oSubModulo.addElement("icono",subModulo.getIco());
                        
                        
                        if( subModulo.getFuncionSistemas().size() > 0 ){
                            BaseArray aFunciones = new BaseArray();
                            
                            String[] atributosFun = {"FunSisId","Nom","Url","ClaNav","NomCon","NomInt","Ico"};
                            String[] etiquetasFun = {"funcionID","nombre","url","clave","controlador","interfaz","icono"};
                            
                            for(FuncionSistema funcion:subModulo.getFuncionSistemas()){
                                /*BaseObject oFuncion = new BaseObject();
                                oFuncion.addElement("funcionID",funcion.getFunSisId() );
                                oFuncion.addElement("nombre",funcion.getNom());
                                oFuncion.addElement("url",funcion.getUrl());
                                oFuncion.addElement("clave",funcion.getClaNav());
                                oFuncion.addElement("controlador",funcion.getNomCon());
                                oFuncion.addElement("interfaz",funcion.getNomInt());
                                oFuncion.addElement("icono",funcion.getIco());
                                aFunciones.addElement(oFuncion);*/                                
                                aFunciones.addElement( BaseObject.builtBaseObjectFromPojo( funcion, atributosFun, etiquetasFun ) );
                            }
                            oSubModulo.addElement("funciones",aFunciones);
                        }
                        
                        
                        aSubModulos.addElement(oSubModulo);
                    }
                    oModulo.addElement("subModulos",aSubModulos);
                }
                
                aModulos.addElement(oModulo);
            }
            
            
            
            BaseObject oResponse = new BaseObject();
            oResponse.addElement("jwt",TokenHandler.getInstance().createTokenForUser(usuario));
            oResponse.addElement("url","/app/");
            oResponse.addElement("nombre",usuario.getNom());
            
            String[] atributosOrg = {"OrgId","Nom"};
            String[] etiquetasOrg = {"organizacionID","nombre"};
            
            oResponse.addElement("organizacion", BaseObject.builtBaseObjectFromPojo(usuario.getOrganizacion(),atributosOrg , etiquetasOrg ) );
            oResponse.addElement("rol",usuario.getRol().getNom());
            oResponse.addElement("modulos",aModulos);
            
            return WebResponse.crearWebResponseExito("el usuario se encuentra en la BD", oResponse);
        }else{
            return WebResponse.crearWebResponseError("el usuario no existe, usuario o contraseÃ±a incorrectos");
        }
    }
    
    public void buscarModulo(List<ModuloSistema> modulos,FuncionSistema funcion){
        
        SubModuloSistema subModulo = funcion.getSubModuloSistema();
        ModuloSistema modulo  = subModulo.getModuloSistema();
        //System.out.println(funcion.getNom() +"  " +subModulo.getNom()+"  "+modulo.getNom());
        for(ModuloSistema m : modulos){
            if(m.getModSisId() == modulo.getModSisId()){
                buscarSubModulo(m.getSubModuloSistemas(),subModulo,funcion);
                return;
            }
        }
        //añadimos un nuevo modulo
        modulo.setSubModuloSistemas( new ArrayList<SubModuloSistema>());
        subModulo.setFuncionSistemas( new ArrayList<FuncionSistema>());
        subModulo.getFuncionSistemas().add(funcion);
        modulo.getSubModuloSistemas().add(subModulo);
        modulos.add(modulo);                
    }
    public void buscarSubModulo(List<SubModuloSistema> subModulos, SubModuloSistema subModulo,FuncionSistema funcion){
        
        for(SubModuloSistema sm : subModulos){
            if(sm.getSubModSisId() == subModulo.getSubModSisId()){
                sm.getFuncionSistemas().add(funcion);
                return;
            }
        }
        //añadimos un nuevo subModulo
        subModulo.setFuncionSistemas( new ArrayList<FuncionSistema>());
        subModulo.getFuncionSistemas().add(funcion);
        subModulos.add(subModulo);
    }
    
}
