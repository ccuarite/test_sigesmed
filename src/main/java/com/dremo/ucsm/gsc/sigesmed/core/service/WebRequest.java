/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service;

import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Mensajes;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.INode;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Geank
 */
//Manejar las consultas que se envien a los datos
public class WebRequest implements INode{

    public final static String REQUEST_STR = "inet-req";
    private static final long serialVersionUID = 8581529465058438212L;
    private final static String REQUEST_ID_STR = "cmd";//"cmd":user@1:findUserByID()
    private final static String REQUEST_IDENTITY_STR = "identity";
    private final static String REQUEST_SCOPE_STR = "scope";
    private final static String REQUEST_META_STR = "meta";//"meta":{"alumno.dni":['363463634'],"alumni.id":['dadas']}
    private final static String REQUEST_DATA_STR = "data";
    ///
    
    private TreeMap<String, String[]> mMetadata = new TreeMap();
    private String mCurrentCommand;
    private String mIdentity;
    private String mScope;
    
    private INode mData = null;
    ////
    
    public static WebRequest create(){
        return new WebRequest();
    }
    public static WebRequest createFromJSON(JSONObject jsonWebRequest){
        return new WebRequest(jsonWebRequest);
    }
    private WebRequest(){
    }
    private WebRequest(JSONObject jsonWebRequest){
        this.fromJSON(jsonWebRequest);
    }
    
    public void setMetadata(TreeMap mMetadata) {
        this.mMetadata = mMetadata;
    }
    public String getCmd() {
        return mCurrentCommand;
    }

    public void setCmd(String mCurrentCommand) {
        this.mCurrentCommand = mCurrentCommand;
    }

    public String getIdentity() {
        return mIdentity;
    }

    public void setIdentity(String mIdentity) {
        this.mIdentity = mIdentity;
    }

    public String getScope() {
        return mScope;
    }

    public void setScope(String mScope) {
        this.mScope = mScope;
    }

    public INode getData() {
        return mData;
    }

    public void setData(INode mData) {
        this.mData = mData;
    }
    
    public void setMetadataValue(String key,String value){
        String values[] = new String[1];
        values[0] = value;
        mMetadata.put(key, values);
    }
    public void setMetadataValues(String key, String[] values){
        mMetadata.put(key,values);
    }
    public void setMetadataValues(String key, List<String> values){
        if(values != null && !values.isEmpty()){
            setMetadataValues(key, values.toArray(new String[values.size()]));
        }
    }
    public void addMetadataValue(String key, String value){
        if(key != null && mMetadata.containsKey(key)){
            String[] values  = mMetadata.get(key);
            String[] newValues = new String[values.length + 1];
            System.arraycopy(values, 0, newValues, 0, values.length);
            newValues[values.length] = value;
            mMetadata.put(key, newValues);
        }else{
            mMetadata.put(key, new String[]{value});
        }
    }
    public String getMetadataValue(String key){
        if(mMetadata.containsKey(key)){
            String result = mMetadata.get(key)[0];
            return result == null ? "" : result;
        }
        return "";
    }
    public String[] getMetadataValues(String key){
        if(mMetadata.containsKey(key)){
            return mMetadata.get(key);
        }
        return new String[0];
    }
    public final String[] listMetadataKeys(){
        String result[] = new String[mMetadata.size()];
        Iterator<String> iterator = mMetadata.keySet().iterator();
        int currentCount = 0;
        while(iterator.hasNext()){
            result[currentCount++] = iterator.next();
        }
        return result;
    }
    public final String[] removeMetadaProperty(String key){
        if(mMetadata.containsKey(key)){
            return mMetadata.remove(key);
        }
        return null;
    }
    public final void removeMetadataProperies(String keyPrefix){
        Set<String> keys = new LinkedHashSet<>();
        for(String key : mMetadata.keySet()){
            if(key.startsWith(keyPrefix)){
                keys.add(key);
            }
        }
        for(String key : keys){
            mMetadata.remove(key);
        }
    }
    public final void removeMetadataProperies(String... keys){
        for(String key : keys){
            if(key != null && !key.isEmpty()){
                mMetadata.remove(key);
            }
        }
    }
    public void clearMetadata(){
        mMetadata.clear();
    }
    protected void internalJSONToMetadata(JSONObject node){
        mMetadata.clear();
        try{
            Iterator<String> iterator = node.keys();
            
            while(iterator.hasNext()){
                String k = iterator.next();
                JSONArray array = node.getJSONArray(k);
                for(int i = 0; i < array.length(); i++){
                    addMetadataValue(k, array.getString(i));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    protected StringBuilder internalMetadataToJSON(){
        StringBuilder buffer = new StringBuilder();
        if(!mMetadata.isEmpty()){
            buffer.append("\"").append(REQUEST_META_STR).append("\" :");
            buffer.append("{");
            String keys[] = listMetadataKeys();
            for(int i = 0; i < keys.length; i++){
                buffer.append("\"").append(keys[i]).append("\" :");
                buffer.append("[");
                String values[] = mMetadata.get(keys[i]);
                for(int j = 0; j < values.length; j++){
                    buffer.append("\"").append(values[j]).append("\"");
                    if(j != (values.length - 1)){
                        buffer.append(",");
                    }
                }
                buffer.append("]");
                if(i != (keys.length - 1)){
                    buffer.append(",");
                }
            }
            buffer.append("}");
        }else{
            buffer.append("\"").append(REQUEST_META_STR).append("\":{}");
        }
        return buffer;
    }
    //metodo que valida el formato del JSON recibido
    //verificar que no ayan otros datos al establecido por la Estructura Json
    protected void verificarEstructuraJson(JSONObject node){
        Set<String> keys = node.keySet();
        
        for( String key : keys  ){
            if( key.contentEquals(NODE_TYPE_STR) || key.contentEquals(REQUEST_SCOPE_STR) || key.contentEquals(REQUEST_IDENTITY_STR) ||
                key.contentEquals(REQUEST_META_STR) || key.contentEquals(REQUEST_ID_STR) || key.contentEquals(REQUEST_DATA_STR)){                
            }
            else{
                throw new RuntimeException(Mensajes.ERR_SIN_FORMATO_JSON);
            }
        }
    }
    //metodo que valida el la cebezera del Json
    //verifica que la cabezera este siempre presente en cada Json Enviado, y que los datos no esten vacios
    private void leerCabezeraJson(JSONObject node){
        if( !REQUEST_STR.contentEquals(node.getString(NODE_TYPE_STR) ) ){
            throw new RuntimeException(Mensajes.ERR_FIRMA_JSON);
        }
        try{
            setScope(node.getString(REQUEST_SCOPE_STR));
            setIdentity(node.getString(REQUEST_IDENTITY_STR));                
            internalJSONToMetadata(node.getJSONObject(REQUEST_META_STR) );
            setCmd(node.getString(REQUEST_ID_STR));
        }
        catch(Exception e){
            throw new RuntimeException(Mensajes.ERR_FALTA_DATO+" : "+ e.getMessage());
        }
        if( this.mScope == null || this.mScope.equals("") ||
            this.mIdentity == null || this.mIdentity.equals("") ||
            this.mCurrentCommand == null || this.mCurrentCommand.equals("")){
            
            throw new RuntimeException(Mensajes.ERR_DATO_VACIO);
        }
                
    }
    @Override
    public void fromJSON(JSONObject node) {
        verificarEstructuraJson(node);
        leerCabezeraJson(node);            
        if(node.has(REQUEST_DATA_STR)) {
            Object oo = node.get(REQUEST_DATA_STR);
            if(oo instanceof JSONObject){
                BaseObject bo = new BaseObject();
                bo.fromJSON((JSONObject)oo);
                setData(bo);
            }else if( oo instanceof JSONArray){
                BaseArray ba = new BaseArray();
                ba.fromJSON2((JSONArray)oo);
                setData(ba);
            }
        }
    }
    @Override
    public StringBuilder toJSON() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append("\"").append(NODE_TYPE_STR).append("\" : \"").append(REQUEST_STR).append("\",");
        buffer.append("\"").append(REQUEST_ID_STR).append("\" : \"").append(getCmd()).append("\",");
        buffer.append("\"").append(REQUEST_IDENTITY_STR).append("\" : \"").append(getIdentity()).append("\",");
        buffer.append("\"").append(REQUEST_SCOPE_STR).append("\" : \"").append(getScope()).append("\",");
        buffer.append(internalMetadataToJSON());
        if(getData() != null){
            buffer.append(",");
            buffer.append("\"").append(REQUEST_DATA_STR).append("\" : ");
            buffer.append(getData().toJSON());
        }
        buffer.append("}");
        return buffer;
    }
    public WebResponse invoke(int moduleId){
        String requestCommand[] = mCurrentCommand.split("@");
        //dominio
        String domain = requestCommand[0];
        String txtVers[] = requestCommand[1].split(":");
        //version del txt
        String version = txtVers[0];
        //comando
        String command = txtVers[1];
        return WebExecutor.executeRequest(moduleId,domain, version, command, this);
    }
    public WebResponse invoke(int moduleId ,String tipoTransaction){
        String requestCommand[] = mCurrentCommand.split("@");
        //dominio
        String domain = requestCommand[0];
        String txtVers[] = requestCommand[1].split(":");
        //version del txt
        String version = txtVers[0];
        //comando con un determinado typo
        String command = txtVers[1] + tipoTransaction;
        return WebExecutor.executeRequest(moduleId,domain, version, command, this);
    }
}
