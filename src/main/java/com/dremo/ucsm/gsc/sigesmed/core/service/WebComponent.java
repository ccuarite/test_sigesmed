/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service;

import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.TypeTransaction;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.ITransaction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Administrador
 */
public class WebComponent {
    
    //codigo del modulo al que pertenece
    private int idModulo = 0;
    //nombre del componente
    private String mName = null;
    private int mVersion = 0;
    private TreeMap<String,Class<ITransaction>> mTxMap = null;
    private ArrayList<String> mDependencies = null;
    public WebComponent(int idModulo){
        this.idModulo = idModulo;
        
        mName = "";
        mVersion = 0;
        mTxMap = new TreeMap<>();
        mDependencies = new ArrayList<>();
    }
    
    public void setIDModulo(int idModulo) {
        this.idModulo = idModulo;
    }
    public int getIDModulo() {
        return idModulo;
    }
    
    public void setName(String mName) {
        this.mName = mName;
    }
    public String getName() {
        return mName;
    }

    public void setVersion(int mVersion) {
        this.mVersion = mVersion;
    }
    public int getVersion() {
        return mVersion;
    }

    public void setTxMap(TreeMap<String, Class<ITransaction>> mTxMap) {
        this.mTxMap = mTxMap;
    }

    public TreeMap<String, Class<ITransaction>> getTxMap() {
        return mTxMap;
    }
    public void setTransaction(String txId, Class txtImpl){
        mTxMap.put(txId,txtImpl);
    }
    public void addTransactionPOST(String txId, Class txtImpl){
        mTxMap.put(txId + TypeTransaction.type_transaction_POST ,txtImpl);
    }
    public void addTransactionGET(String txId, Class txtImpl){
        mTxMap.put(txId + TypeTransaction.type_transaction_GET ,txtImpl);
    }
    public void addTransactionPUT(String txId, Class txtImpl){
        mTxMap.put(txId + TypeTransaction.type_transaction_PUT,txtImpl);
    }
    public void addTransactionDELETE(String txId, Class txtImpl){
        mTxMap.put(txId + TypeTransaction.type_transaction_DELETE,txtImpl);
    }    
    public Class<ITransaction> getTransactionImpl(String txId){
        return mTxMap.get(txId);
    }
    public String[] listTransactionsID(){
        String keys[] = new String[mTxMap.size()];
        Iterator<String>  iterator = mTxMap.keySet().iterator();
        int count = 0;
        while(iterator.hasNext()){
            keys[count++] = iterator.next();
        }
        return keys;
    }
    public void setDependency(String domainID,int version){
        mDependencies.add(domainID + "@" + version);
    }
    public String[] getDependencies(){
        int size = mDependencies.size();
        return mDependencies.toArray(new String[size]);
    }
}
