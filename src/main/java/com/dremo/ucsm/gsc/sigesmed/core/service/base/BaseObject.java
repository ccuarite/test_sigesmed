/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service.base;

import com.dremo.ucsm.gsc.sigesmed.core.service.constantes.Mensajes;
import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.INode;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Administrador
 */
public class BaseObject implements INode{

    
    /** Encapsulado de las variables */
    private final static String OBJECT_STR = "object";
    private final static String OBJECT_DATA_STR = "o";
    private final static String OBJECT_ID_STR = "id";
    private final static String OBJECT_VERSION_STR = "v";
    private final static String OBJECT_ELEMENT_STR = "element";
    private final static String OBJECT_OBJECT_STR = "object";
    private final static String OBJECT_STRING_STR = "string";
    private final static String OBJECT_ARRAY_STR = "array";
    
    private String mId = null;
    private String mVersion = null;
    
    private TreeMap<String, Object> mElements = new TreeMap<>();
    
    public BaseObject(){
        mId = "";
        mVersion = "";
    }
    public BaseObject(String id, String version){
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getVersion() {
        return mVersion;
    }

    public BaseObject setVersion(String mVersion) {
        this.mVersion = mVersion;
        return this;
    }
    public BaseObject addElement(String id, String value){
        mElements.put(id, value);
        return this;
    }
    public BaseObject addElement(String id, Object value){
        mElements.put(id, value);
        return this;
    }
    //cambie
    public BaseObject addElement(String id, Boolean value){
        mElements.put(id, value);
        return this;
    }
    public BaseObject addElement(String id, Float value){
        mElements.put(id, Float.toString(value));
        return this;
    }
    public BaseObject addElement(String id, Double value){
        mElements.put(id, Double.toString(value));
        return this;
    }
    public BaseObject addElement(String id, Integer value){
        mElements.put(id, Integer.toString(value));
        return this;
    }
    public BaseObject addElement(String id, Short value){
        mElements.put(id, Short.toString(value));
        return this;
    }
    public BaseObject addElement(String id, char value){
        String c = ""+value;
        mElements.put(id, c);
        return this;
    }
    public BaseObject addElement(String id, Long value){
        mElements.put(id, Long.toString(value));
        return this;
    }
    public BaseObject addElement(String id, Date value){
        //mElements.put(id, Long.toString(value.getTime(),10));
        mElements.put(id, value.toString());
        return this;
    }
    
    public BaseObject addElement(String id, BigInteger value){
        mElements.put(id, value.toString());
        return this;
    }
    public BaseObject addElement(String id, BigDecimal value){
        mElements.put(id, value.toString());
        return this;
    }
    public BaseObject addElement(String id, BaseArray value){
        mElements.put(id, value );
        return this;
    }
    public BaseObject addElement(String id, BaseObject value){
        mElements.put(id, value);
        return this;
    }
    
    public List<String> getElementsKeys(){
        LinkedList<String> keys = new LinkedList();
        for( String s : mElements.keySet()){
            keys.add(s);
        }
        return keys;
    }
    public Object getElementObject(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null){
            return mElements.get(id);
        }
        return null;
    }
    public BaseArray getElementBaseArray(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null)
            return mElements.get(id) instanceof BaseArray ? (BaseArray)mElements.get(id) : null;
        return null;
    }
    public String getElementString(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null){
            return mElements.get(id) instanceof String ? (String)mElements.get(id) : null;
        }
        return null;
    }
    public BaseObject getElementBaseObject(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null){
            return mElements.get(id) instanceof BaseObject ? (BaseObject)mElements.get(id) : null;
        }
        return null;
    }
    public Boolean getElementBoolean(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null){
            return mElements.get(id) instanceof Boolean ? (Boolean)mElements.get(id) : null;
        }
        return null;
    }
    public Float getElementFloat(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null){
            return mElements.get(id) instanceof String ? Float.parseFloat((String) mElements.get(id)) : null;
        }
        return null;
    }
    public Double getElementDouble(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null){
            return mElements.get(id) instanceof String ? Double.parseDouble((String) mElements.get(id)) : null;
        }
        return null;
    }
    public Integer getElementInteger(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null){
            return mElements.get(id) instanceof String ? Integer.parseInt((String) mElements.get(id)) : null;
        }
        return null;
    }
    public Short getElementShort(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null){
            return mElements.get(id) instanceof String ? Short.parseShort((String) mElements.get(id)) : null;
        }
        return null;
    }
    public Long getElementLong(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null){
            return mElements.get(id) instanceof String ? Long.parseLong((String) mElements.get(id)) : null;
        }
        return null;
    }
    public Date getElementDate(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null && mElements.get(id) instanceof String){
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.parseLong((String)mElements.get(id),10));
            return cal.getTime();
        }
        return null;
    }
    public BigInteger getElementBigInteger(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null && mElements.get(id) instanceof String){
            return new BigInteger((String)mElements.get(id));
        }
        return null;
    }
    public BigDecimal getElementBigDecimal(String id){
        if(mElements.containsKey(id) && mElements.get(id) != null && mElements.get(id) instanceof String){
            return new BigDecimal((String)mElements.get(id));
        }
        return null;
    }
    @Override
    public void fromJSON(JSONObject node) {
        try{
            /*setId(node.getString(OBJECT_ID_STR));
            setVersion(node.getString(OBJECT_VERSION_STR));*/

            //JSONObject obj = node.getJSONObject(OBJECT_DATA_STR);

            if(node != null){
                Set<String> keys = node.keySet();
                for(String k : keys){
                    if(node.get(k) instanceof JSONObject){
                        JSONObject o = node.getJSONObject(k);
                        /*INode auxNode = NodeBuilder.getInstance().createEspecificNode(o.getString(NODE_TYPE_STR));
                        auxNode.fromJSON(o);*/
                        BaseObject object = new BaseObject();
                        object.fromJSON(o);
                        mElements.put(k,object);
                    }
                    else if(node.get(k) instanceof JSONArray){
                        JSONArray a = node.getJSONArray(k);
                        BaseArray object = new BaseArray();
                        object.fromJSON(a);
                        mElements.put(k,object);
                    }
                    else if(node.get(k) instanceof Integer){
                        int i = node.getInt(k);
                        mElements.put(k,i);
                    }else if(node.get(k) instanceof Double){
                        double d = node.getDouble(k);
                        mElements.put(k,d);
                    }else if(node.get(k) instanceof Boolean){
                        boolean b = node.getBoolean(k);
                        mElements.put(k,b);
                    }else{
                        String s = node.getString(k);
                        mElements.put(k,s);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }

    @Override
    /*
    public StringBuilder toJSON() {
        StringBuilder buffer =new StringBuilder();

        buffer.append("{");
        buffer.append("\"").append(OBJECT_ID_STR).append("\" : \"").append(getId()).append("\", ");
        buffer.append("\"").append(OBJECT_VERSION_STR).append("\" : \"").append(getVersion()).append("\", ");
        buffer.append("\"").append(NODE_TYPE_STR).append("\" : \"").append(OBJECT_STR).append("\",");
        buffer.append("\"").append(OBJECT_DATA_STR).append("\" : {");
        Set<Map.Entry<String,Object>> entrySet = mElements.entrySet();
        for(Map.Entry<String,Object> entry : entrySet){
            Object o = entry.getValue();
            if(o instanceof BaseObject){
                buffer.append("\"").append(entry.getKey()).append("\":").append(((BaseObject)o).toJSON()).append(",");                
            }
            else if(o instanceof BaseArray){
                buffer.append("\"").append(entry.getKey()).append("\":").append(((BaseArray)o).toJSON()).append(",");
            }
            else if(o instanceof String){
                buffer.append("\"").append(entry.getKey()).append("\":\"").append(((String)o)).append("\",");
            }
        }
        buffer.delete(buffer.length()-1, buffer.length());
        buffer.append("}");
        buffer.append("}");
        return buffer;
    }*/
    
    public StringBuilder toJSON() {
        StringBuilder buffer =new StringBuilder();

        buffer.append("{");
        Set<Map.Entry<String,Object>> entrySet = mElements.entrySet();
        for(Map.Entry<String,Object> entry : entrySet){
            Object o = entry.getValue();
            if(o instanceof BaseObject){
                buffer.append("\"").append(entry.getKey()).append("\":").append(((BaseObject)o).toJSON()).append(",");                
            }
            else if(o instanceof BaseArray){
                buffer.append("\"").append(entry.getKey()).append("\":").append(((BaseArray)o).toJSON()).append(",");
            }
            else if(o instanceof String){
                buffer.append("\"").append(entry.getKey()).append("\":\"").append(((String)o)).append("\",");
            }
            else if(o instanceof Boolean){
                buffer.append("\"").append(entry.getKey()).append("\":").append(((Boolean)o)).append(",");
            }
        }
        buffer.delete(buffer.length()-1, buffer.length());
        buffer.append("}");
        return buffer;
    }
    
    public static BaseObject builtBaseObjectFromPojo(Object obj, String[] atributos,String[] etiquetas){
        
        BaseObject nuevo = null;
        
        Class miClase = obj.getClass();
        
        try {
            Object objResult = null;
            nuevo = new BaseObject();
            for(int i=0;i<atributos.length;i++){
                
                objResult = miClase.getMethod("get"+atributos[i]).invoke(obj);
                
                if(objResult instanceof Boolean)
                    nuevo.addElement(etiquetas[i], (Boolean)objResult );
                else
                    nuevo.addElement(etiquetas[i],objResult.toString());                
                
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BaseObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERROR al construir un BaseObject a partir de la clase : "+ miClase.getName());
        }
        
        //System.out.println( nuevo.toJSON() );
        
        return nuevo;        
    }
    
    //convertir de un BaseObject a una pojo
    //miClase : clase del pojo a convertir
    //atributos : nombres de los atributos del pojo
    //tipos : tipos de los atributos del pojo
    //etiquetas : nombres de las etiquetas del BaseObject
    public Object builtPojoFromBaseObject(Class miClase, String[] atributos, Class[] tipos, String[] etiquetas){
                
        Object miPojo = null;
        try {
            miPojo = miClase.newInstance();
        } catch (InstantiationException | IllegalAccessException  ex) {
            Logger.getLogger(BaseObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERROR al instancear el pojo : "+ miClase.getName());
        }
        try {            
            for(int i=0;i<atributos.length;i++){
                Class[] cArg = new Class[1];
                cArg[0] = tipos[i];                
                miClase.getMethod("set"+atributos[i], cArg ).invoke(miPojo , this.getElementObject( etiquetas[i] ) );
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BaseObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERROR al construir un pojo de tipo: "+ miClase.getName());
        }
        
        return miPojo;
    }
}
