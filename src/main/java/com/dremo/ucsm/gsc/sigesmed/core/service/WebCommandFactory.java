/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service;

import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Mensajes;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import java.util.TreeMap;

/**
 *
 * @author Administrador
 */
public final class WebCommandFactory {
     private static WebCommandFactory mInstance = null;
     private TreeMap<String, TreeMap<String,WebComponent>> mDomainMap = null;     
     private TreeMap<String, TreeMap<String, Class<ITransaction>>> mTxmap = null;
     
     private TreeMap<Integer , TreeMap<String, Class<ITransaction>>> modules = null;     
     private final String TAG = getClass().getName();
     
     public static WebCommandFactory getInstance(){
         if(mInstance == null){
             mInstance = new WebCommandFactory();
         }
         return mInstance;
     }
     private WebCommandFactory(){
         mDomainMap = new TreeMap<>();
         mTxmap = new TreeMap<>();
         modules = new TreeMap<>();
     }
     public void registerComponent(WebComponent component) throws Exception{
         if(!mDomainMap.containsKey(component.getName())){
             //si no existe el dominio
             //verificamos que las dependencias y sus versiones existan
             String dependencies[] = component.getDependencies();
             for(int i = 0 ; i < dependencies.length; i++){
                 String componentVers[]= dependencies[i].split("@");
                 if(!mDomainMap.containsKey(componentVers[0])){
                     System.out.println("No existe la dependencia:[ " + dependencies[i] + "] en el dominio[" + component.getName() +"]");
                     throw new Exception("No existe la dependencia");
                 }
                 if(!mDomainMap.get(componentVers[0]).containsKey(componentVers[1])){
                     System.out.println("No existe la version de la dependencia");
                     throw new Exception("No existe la version de la dependencia");
                 }
             }
             //registramos el nuevo dominio
             TreeMap<String, WebComponent> componentVersionMap = new TreeMap<>();
             componentVersionMap.put(String.valueOf(component.getVersion()), component);
             mDomainMap.put(component.getName(), componentVersionMap);
             System.out.println("Se Registro el dominio [" + component.getName()  + "] version [" + component.getVersion() + "]" );
         }else{
             //si existe el dominio
             //verificamos que nos se duplique la version
             if(!mDomainMap.get(component.getName()).containsKey(String.valueOf(component.getVersion()))){
                 TreeMap<String, WebComponent> componentVersionMap = mDomainMap.get(component.getName());
                 componentVersionMap.put(String.valueOf(component.getVersion()), component);
                 System.out.println("Se Registro nueva version del dominio [" + component.getName()  + "] version [" + component.getVersion() + "]" );
             }else{
                 System.out.println("Version duplicada para el dominio");
                 throw new Exception("Version duplicada para el dominio [" + component.getName()  + "]");
             }
         }
         //registramos las transacciones
         TreeMap<String, Class<ITransaction>> transactionMap = new TreeMap<>();
         String transactions[] = component.listTransactionsID();
         
         System.out.println("Registrando TX para el dominio [" + component.getName() + "] version[" + component.getVersion() + "]");
         for(int i = 0 ; i < transactions.length; i++){
             Class<ITransaction> txImpl = component.getTransactionImpl(transactions[i]);
             //si no existe la transaccion entonces borramos su version
             if(txImpl == null){
                 mDomainMap.get(component.getName()).remove(String.valueOf(component.getVersion()));
                 //if es la unica version entonces borramos el dominio
                 if(mDomainMap.get(component.getName()).size() == 0){
                     mDomainMap.remove(component.getName());
                 }
                 System.out.println("No se encontro la TX [" + transactions[i] + "]");
                 throw new Exception("No se encontro la TX [" + transactions[i] + "]");
             }
             transactionMap.put(transactions[i], txImpl);
             System.out.println("Se registro la Tx [" + component.getName()+"@" + component.getVersion() + ":" + transactions[i] + "] => " + txImpl);
         }
         mTxmap.put(component.getName() + "@" + component.getVersion(), transactionMap);
         System.out.println("Se registro la Tx [" + component.getName()+"@" + component.getVersion()  + "] Fin " );
     }
     public void registerComponent2(WebComponent component) throws Exception{
         
         //
         int moduleID = component.getIDModulo();
         
         //contenedor de componentes del modulo
         TreeMap<String, Class<ITransaction>> moduleComponents = modules.get( moduleID );
         
        //si no estan el contenedor de components del module creamos uno
         if( moduleComponents == null ){
             
             //creando un contenedor de componentes para el modulo
             moduleComponents = new TreeMap<>();
             //añadimos el nuevo modulo
             System.out.println("Se Registro el Modulo [" + component.getIDModulo()+ "]" ) ;
             modules.put( moduleID, moduleComponents );
             
             //obtenemos las trasacciones del componente a insertar
             TreeMap<String, Class<ITransaction>> componentTransactions = component.getTxMap();
             
             //obtenemos el id del componente formado por = nombre + @ + version              
             String componentID = component.getName() + "@" + component.getVersion() ;             
             System.out.println("Componente [" + component.getName() + "] version[" + component.getVersion() + "]");
             
             //añadiendo las trasacciones del componente
             for( String transactionID : componentTransactions.keySet() ){
                 
                 Class<ITransaction> transaction = componentTransactions.get(transactionID );
                 //formamos el id de la trasaccion 
                 String newTransactionID = componentID + ":" + transactionID;
                 moduleComponents.put( newTransactionID , transaction );
                 System.out.println("Se registro la Tx [" + newTransactionID+ "] => " + transaction);
             }
             
         }else{
             
             //obtenemos las trasacciones del componente a insertar
             TreeMap<String, Class<ITransaction>> componentTransactions = component.getTxMap();
             
             //obtenemos el id del componente formado por = nombre + @ + version              
             String componentID = component.getName() + "@" + component.getVersion() ;
             System.out.println("Componente [" + component.getName() + "] version[" + component.getVersion() + "]");
             
             //añadiendo las trasacciones del componente
             for( String transactionID : componentTransactions.keySet() ){
                 
                 Class<ITransaction> transaction = componentTransactions.get(transactionID );
                 //formamos el id de la trasaccion 
                 String newTransactionID = componentID + ":" + transactionID;
                 
                 //verificamos que la transaction no se repita
                 if( moduleComponents.containsKey( newTransactionID ) ){
                     throw new Exception("Duplicado [" + newTransactionID  + "]");
                 }               
                 
                 moduleComponents.put( newTransactionID , transaction );
                 System.out.println("Se registro la Tx [" + newTransactionID+ "] => " + transaction);
             }
         }
     }
     /*
     public ITransaction getTransaction(String componentId,String versionId, String txId ){
         
         String componentVersion = compon;entId + "@" + versionId;
         
         if(!mTxmap.containsKey(componentVersion)){
             throw new RuntimeException(Mensajes.ERR_MODULO_NO_ENCONTRADO + " COM : "+componentVersion);//error;
         }
         if(!mTxmap.get(componentVersion).containsKey(txId)){
             throw new RuntimeException(Mensajes.ERR_TRASACCION_NO_ENCONTRADO + " TX : "+txId);//error;
         }
         try{
             return mTxmap.get(componentVersion).get(txId).newInstance();
         }catch(InstantiationException | IllegalAccessException e){
             throw new RuntimeException(Mensajes.ERR_INSTANCIANDO_COMANDO+" TX : "+txId);//error
             //
         }
     }*/
     
     public ITransaction getTransaction(int moduleID, String componentId,String versionId, String txId ){
         
         TreeMap<String, Class<ITransaction>> moduleComponents = modules.get( moduleID );
         
         if(moduleComponents == null){
             throw new RuntimeException(Mensajes.ERR_MODULO_NO_ENCONTRADO+ " MODULO : "+ moduleID);//error;
         }
         String transactionID = componentId + "@" + versionId +":" + txId;
         Class<ITransaction> transactionClass = moduleComponents.get(transactionID);
         if( transactionClass == null){
             throw new RuntimeException(Mensajes.ERR_TRASACCION_NO_ENCONTRADO + " TX : "+txId);//error;
         }
         try{
             return transactionClass.newInstance();
         }catch(InstantiationException | IllegalAccessException e){
             throw new RuntimeException(Mensajes.ERR_INSTANCIANDO_COMANDO+" TX : "+txId);//error
             //
         }
         
     }
     /*
     */
     
}
