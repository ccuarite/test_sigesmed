/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.service.base;

import com.dremo.ucsm.gsc.sigesmed.core.service.interfaces.INode;
import java.util.TreeMap;

/**
 *
 * @author Administrador
 */
public class NodeBuilder {
    private static NodeBuilder mInstance = null;
    private TreeMap<String,Class> mNodeMap = new TreeMap<>();
    
    private NodeBuilder(){
        mNodeMap.put("array", BaseArray.class);
        mNodeMap.put("object", BaseObject.class);
    }
    
    public static NodeBuilder getInstance(){
        if(mInstance == null){
            mInstance = new NodeBuilder();
        }
        return mInstance;
    }
    public INode createEspecificNode(String nodeId) {
        if(!mNodeMap.containsKey(nodeId))
            return null;
        
        INode nodeResult = null;

        try {
            Class<?> clazz = mNodeMap.get(nodeId);
            nodeResult = (INode) clazz.newInstance();
            return nodeResult;

        }catch(Exception e){
                e.printStackTrace();
        }

        return null;
		
    }
}
