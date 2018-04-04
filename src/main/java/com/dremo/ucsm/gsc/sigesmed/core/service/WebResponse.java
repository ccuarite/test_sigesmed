/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service;

import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseArray;
import com.dremo.ucsm.gsc.sigesmed.core.service.base.BaseObject;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.INode;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Administrador
 */
public class WebResponse implements INode {
    
    public final static String RESPONSE_STR = "inet-res";
    private static final long serialVersionUID = -8581174746407977943L;
    private final static String RESPONSE_META_STR = "meta";;
    private final static String RESPONSE_ID_STR = "response";
    private final static String RESPONSE_STA_STR = "responseSta";
    private final static String RESPONSE_MSG_STR = "responseMsg";
    private final static String RESPONSE_SCOPE_STR = "scope";
    private final static String RESPONSE_DATA_STR = "data";
    public static String OK_RESPONSE = "OK";
    public static String BAD_RESPONSE = "BAD";
    private String mResponse = null;
    private boolean mResponseSta = false;
    private String mResponseMsg = null;
    private String mScope = null;
    
    private INode mData = null;
    private java.util.TreeMap<String, String[]> mMetadata = new java.util.TreeMap<>() ;

    public String getResponse() {
        return mResponse;
    }

    public boolean getResponseSta() {
        return mResponseSta;
    }

    public String getResponseMsg() {
        return mResponseMsg;
    }

    public String getScope() {
        return mScope;
    }

    public void setResponse(String mResponse) {
        this.mResponse = mResponse;
    }

    public void setResponseSta(boolean mResponseSta) {
        this.mResponseSta = mResponseSta;
    }

    public void setResponseMsg(String mResponseMsg) {
        this.mResponseMsg = mResponseMsg;
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
    
    public void setMetadata(TreeMap<String, String[]> mMetadata) {
        this.mMetadata = mMetadata;
    }
    
   
    public void setMetadataValue(String key, String value){
        String[] values = new String[1];
        values[0] = value;
        mMetadata.put(key, values);
    }
    public void setMetadataValues(String key, String[] values){
        mMetadata.put(key, values);
    }
    public void setMetadataValues(String key, List<String> values){
        if(values != null && !values.isEmpty()){
            setMetadataValues(key, values.toArray(new String[values.size()]));
        }
    }
    public void addMetadaValue(String key, String value){
        if(key != null && mMetadata.containsKey(key)){
            String values[] = mMetadata.get(key);
            String newValues[] = new String[values.length + 1];
            newValues[values.length] = value;
            mMetadata.put(key, newValues);          
        }else{
            mMetadata.put(key,new String[]{value});
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
        int count = 0;
        while(iterator.hasNext()){
            result[count++] = iterator.next();
        }
        return result;
    }
    public String[] removeMetadataProperty(String key){
        if(mMetadata.containsKey(key)){
           return mMetadata.remove(key);
        }
        return null;
    }
    public void removeMetadataProperties(String keyPrefix){
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
    public void clearMetadata(){
        mMetadata.clear();
    }
    protected void internalJSONToMetadata(JSONObject node){
        mMetadata.clear();
        try{
            Iterator<String> iterator = node.keys();
            while(iterator.hasNext()){
                String key = iterator.next();
                JSONArray array = node.getJSONArray(key);
                for(int i = 0; i < array.length(); i++){
                    addMetadaValue(key, array.getString(i));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    protected StringBuilder internalMetadataToJSON(){
        StringBuilder buffer = new StringBuilder();
        if(!mMetadata.isEmpty() && mMetadata.size() > 0){
            buffer.append("\"").append(RESPONSE_META_STR).append("\":");
            String[] keys = listMetadataKeys();
            buffer.append("{");
            for(int i = 0; i < keys.length; i++){
                String data[] = mMetadata.get(keys[i]);
                buffer.append("\"").append(keys[i]).append("\": ");
                buffer.append("[");
                for(int j = 0; j < data.length; j++){
                    buffer.append("\"").append(data).append("\"");
                    if(j != (data.length - 1)){
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
            buffer.append("\"").append(RESPONSE_META_STR).append("\":{}");
        }
        return buffer;
    }
    @Override
    public void fromJSON(JSONObject node) {
        try{
            if(!RESPONSE_STR.equals(node.getString(NODE_TYPE_STR))){
                System.out.println("El objeto, no cuenta con la firma esperada");
                return;
            }
            setResponse(node.getString(RESPONSE_ID_STR));
            setResponseSta(node.getBoolean(RESPONSE_STA_STR));
            setResponseMsg(node.getString(RESPONSE_MSG_STR));

            setScope(node.getString(RESPONSE_SCOPE_STR));
            
            JSONObject meta = node.getJSONObject(RESPONSE_META_STR);
            if(meta != null){
                internalJSONToMetadata(meta);
            }
            if(node.has(RESPONSE_DATA_STR)){
                Object oo = node.get(RESPONSE_DATA_STR);
                if(oo instanceof JSONObject){
                    BaseObject bo = new BaseObject();
                    bo.fromJSON((JSONObject)oo);
                    setData(bo);
                }else if( oo instanceof JSONArray){
                    BaseArray ba = new BaseArray();
                    ba.fromJSON((JSONArray)oo);
                    setData(ba);
                }
                /*Object o = node.get(RESPONSE_DATA_STR);
                if(o instanceof JSONObject){
                    JSONObject dataObj = (JSONObject)o;
                    if(dataObj != null){
                        if (dataObj.has(NODE_TYPE_STR)){
                            INode auxNode = NodeBuilder.getInstance().createEspecificNode(dataObj.getString(NODE_TYPE_STR));
                            if(auxNode != null){
                                auxNode.fromJSON(dataObj);
                                setData(auxNode);
                            }
                        }
                    }
                }*/
                /*if(o instanceof JSONArray){
                    JSONArray arrayObj = (JSONArray)o;
                    BaseArray arrayData = new BaseArray();
                    if(arrayObj != null){
                        for(int i = 0; i < arrayObj.length(); ++i){
                            JSONObject dataObj = arrayObj.getJSONObject(i);
                            if (dataObj.has(NODE_TYPE_STR)) {
                                INode auxNode = NodeBuilder.getInstance().createEspecificNode(dataObj.getString(NODE_TYPE_STR));
                                if (auxNode != null) {
                                    auxNode.fromJSON(dataObj);
                                    arrayData.addElement((BaseObject)auxNode);
                                }
                            }
                        }
                    }
                    setData(arrayData);
                }*/
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public StringBuilder toJSON() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append("\"").append(NODE_TYPE_STR).append("\" : \"").append(RESPONSE_STR).append("\",");
        buffer.append("\"").append(RESPONSE_ID_STR).append("\" : \"").append( getResponse() == null ? "" : getResponse()).append("\",");
        buffer.append("\"").append(RESPONSE_STA_STR).append("\" : ").append( getResponseSta() ).append(",");
        buffer.append("\"").append(RESPONSE_MSG_STR).append("\" : \"").append( getResponseMsg() == null ? "" : getResponseMsg()).append("\",");
        buffer.append("\"").append(RESPONSE_SCOPE_STR).append("\" : \"").append( getScope() == null ? "" : getScope()).append("\",");
        buffer.append(internalMetadataToJSON());
        
        if(getData() != null){
            buffer.append(",");

            buffer.append("\"").append(RESPONSE_DATA_STR).append("\":");
            buffer.append(getData().toJSON());
        }else{
            buffer.append(",");
            buffer.append("\"").append(RESPONSE_DATA_STR).append("\": { } ");
        }

        buffer.append("}");
        return buffer;
    }    
    
    public static WebResponse crearWebResponseError(String msg){
        WebResponse wResponse = new WebResponse();
        wResponse.setResponseMsg(msg);
        return wResponse;
    }
    public static WebResponse crearWebResponseExito(String msg){
        WebResponse wResponse = new WebResponse();
        wResponse.setResponseSta(true);
        wResponse.setResponseMsg(msg);
        return wResponse;
    }
    public static WebResponse crearWebResponseError(String msg,String response){
        WebResponse wResponse = new WebResponse();
        wResponse.setResponseMsg(msg);
        wResponse.setResponse(response);
        return wResponse;
    }
    public static WebResponse crearWebResponseExito(String msg,String response){
        WebResponse wResponse = new WebResponse();
        wResponse.setResponseSta(true);
        wResponse.setResponseMsg(msg);
        wResponse.setResponse(response);
        return wResponse;
    }
    public static WebResponse crearWebResponseError(String msg,INode data){
        WebResponse wResponse = new WebResponse();
        wResponse.setResponseMsg(msg);
        wResponse.setData(data);
        return wResponse;
    }
    public static WebResponse crearWebResponseExito(String msg,INode data){
        WebResponse wResponse = new WebResponse();
        wResponse.setResponseSta(true);
        wResponse.setResponseMsg(msg);
        wResponse.setData(data);
        return wResponse;
    }
    
    
}
