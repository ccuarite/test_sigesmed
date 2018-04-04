/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service.interfaces;

import org.json.JSONObject;


/**
 *
 * @author Administrador
 */
public interface INode {
    public final static String NODE_TYPE_STR    = "i.type";
    public final static String NODE_TYPE_OBJECT    = "object";
    public final static String NODE_TYPE_ARRAY    = "array";
    public void fromJSON(JSONObject o);
    public StringBuilder toJSON();
}
