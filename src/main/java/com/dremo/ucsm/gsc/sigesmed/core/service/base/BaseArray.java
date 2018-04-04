/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service.base;

import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.INode;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Administrador
 */
public class BaseArray implements INode{

    private static final long serialVersionUID = -3593899751042330646L;
    
    private final static String ARRAY_DATA_STR = "a";
    private ArrayList<Object> mArray = new ArrayList<>();
   
    private String mId = null;

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }
    
    public void addElement(String value){
        mArray.add(value);
    }
    public void addElement(Boolean value){
        mArray.add(Boolean.toString(value));
    }
    public void addElement(Double value){
        mArray.add(Double.toString(value));
    }
    public void addElement(BigInteger value){
        mArray.add(value.toString());
    }
    public void addElement(BigDecimal value){
        mArray.add(value.toString());
    }
    public void addElement(Float value){
        mArray.add(Float.toString(value));
    }
    public void addElement(Integer value){
        mArray.add(Integer.toString(value));
    }
    public void addElement(Long value){
        mArray.add(Long.toString(value));
    }
    public void addElement(Date value){
        mArray.add(Long.toString(value.getTime(),10));
    }
    public void addElement(BaseArray value){
        mArray.add(value);
    }
    public void addElement(INode value){
        mArray.add(value);
    }
    public void addElement(BaseObject value){
        mArray.add(value);
    }
    public Object getElementObject(int index){
        if(index >= 0 && index < mArray.size()){
            return mArray.get(index);
        }
        return null;
    }
    public BaseObject getElementBaseObject(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index)!= null){
            return mArray.get(index) instanceof BaseObject ? (BaseObject)mArray.get(index) : null;
        }
        return null;
    }
    public String getElementString(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index)!= null){
            return mArray.get(index) instanceof String ? (String) mArray.get(index) : null;
        }
        return null;
    }
    public Boolean getElementBoolean(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index)!= null){
            return mArray.get(index) instanceof String ? Boolean.parseBoolean((String) mArray.get(index)) : null;
        }
        return null;
    }
    public Float getElementFloat(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index)!= null){
            return mArray.get(index) instanceof String ? Float.parseFloat((String) mArray.get(index)) : null;
        }
        return null;
    }
    public Double getElementDouble(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index)!= null){
            return mArray.get(index) instanceof String ? Double.parseDouble((String) mArray.get(index)) : null;
        }
        return null;
    }
    public Integer getElementInteger(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index)!= null){
            return mArray.get(index) instanceof String ? Integer.parseInt((String) mArray.get(index)) : null;
        }
        return null;
    }
    public Long getElementLong(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index)!= null){
            return mArray.get(index) instanceof String ? Long.parseLong((String) mArray.get(index)) : null;
        }
        return null;
    }
    public Date getElementDate(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index) instanceof String){
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.parseLong((String)mArray.get(index),10));
            return cal.getTime();
        }
        return null;
    }
    public BigInteger getElemetBigInteger(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index) instanceof String){
            return new BigInteger((String)mArray.get(index));
        }
        return null;
    }
    public BigDecimal getElemetBigDecimal(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index) instanceof String){
            return new BigDecimal((String)mArray.get(index));
        }
        return null;
    }
    public BaseArray getElementBaseArray(int index){
        if(index >= 0 && index < mArray.size() && mArray.get(index)!= null){
            return mArray.get(index) instanceof BaseArray ? (BaseArray)mArray.get(index) : null;
        }
        return null;
    }
    public int size(){
        return mArray.size();
    }
    public List<Object>  getList(){
        return mArray;
    }
    
    @Override
    public void fromJSON(JSONObject o) {
        JSONArray array = o.getJSONArray(ARRAY_DATA_STR);
        fromJSON(array);
    }
    //verificamos el tipo en cada elemento
    public void fromJSON(JSONArray node) {
        try{
            for(int i = 0; i < node.length(); i++){
                if(node.get(i) instanceof JSONObject){
                    JSONObject object = node.getJSONObject(i);
                    
                    /*INode auxNode = NodeBuilder.getInstance().createEspecificNode(object.getString(NODE_TYPE_STR));
                    auxNode.fromJSON(object);
                    addElement(auxNode);*/
                    BaseObject bo = new BaseObject();
                    bo.fromJSON(object);
                    addElement(bo);
                }
                else if(node.get(i) instanceof JSONArray){
                    JSONArray array = node.getJSONArray(i);
                    BaseArray object = new BaseArray();
                    object.fromJSON(array);
                    mArray.add(object);
                }
                else{
                    String s = node.getString(i);
                    mArray.add(s);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //considerando un array donde todos los elementos son del mismo tipo
    //basta con comprobar el tipo del primer elmento
    public void fromJSON2(JSONArray node) {
        try{
            if(node.get(0) instanceof JSONObject){
                for(int i = 0; i < node.length(); i++){
                    BaseObject bo = new BaseObject();
                    bo.fromJSON(node.getJSONObject(i));
                    mArray.add(bo);
                }
            }
            else if(node.get(0) instanceof JSONArray){
                for(int i = 0; i < node.length(); i++){
                    BaseArray ba = new BaseArray();
                    ba.fromJSON(node.getJSONArray(i));
                    mArray.add(ba);
                }
            }
            else{
                for(int i = 0; i < node.length(); i++){
                    mArray.add(node.getString(i));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public StringBuilder toJSON() {
        StringBuilder buffer = new StringBuilder();
        List<StringBuilder> aux = new ArrayList<>();
        for(int i = 0; i < mArray.size() ; i++){
            Object o = mArray.get(i);
            if(o instanceof BaseObject){
                aux.add(((BaseObject)o).toJSON());
            }
            else if(o instanceof BaseArray) {
                aux.add(((BaseArray)o).toJSON());
            }
            else if(o instanceof String){
                aux.add(new StringBuilder().append("\"").append((String)o).append("\""));
            }
        }
        buffer.append(aux.toString().replace(", ",","));
        return buffer;
    }
    //convertir de un BaseArray a una List<pojo>
    public List<?> builtListPojoFromBaseObject(Class miClase, String[] atributos,Class[] tipos,String[] etiquetas){
        
        List<Object> lista = new ArrayList();
        try {
            for( Object o: mArray ){

                Object miPojo = null;
                miPojo = miClase.newInstance();
                
                for(int i=0;i<atributos.length;i++){
                    Class[] cArg = new Class[1];
                    cArg[0] = tipos[i];                
                    miClase.getMethod("set"+atributos[i], cArg ).invoke(miPojo , ((BaseObject)o).getElementObject( etiquetas[i] ) );
                }
                lista.add(miPojo);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BaseObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERROR al instancear el pojo : "+ miClase.getName());
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BaseObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERROR al construir un pojo de tipo: "+ miClase.getName());
        }
        return lista;
    }
}
