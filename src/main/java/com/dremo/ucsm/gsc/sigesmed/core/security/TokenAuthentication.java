/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.security;

import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class TokenAuthentication {
    private static Logger logger = Logger.getLogger(TokenAuthentication.class.getName());
    
    public boolean isValidAuthentication(String tokenRequest){
        return TokenHandler.getInstance().validarToken(tokenRequest);
    }
}
