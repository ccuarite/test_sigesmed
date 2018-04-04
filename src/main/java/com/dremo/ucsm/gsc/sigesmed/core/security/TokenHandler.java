/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.security;

import com.dremo.ucsm.gsc.sigesmed.core.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Administrador
 */
public class TokenHandler {
    private static final Key key = MacProvider.generateKey();
    private Map<String,String> tokens = new TreeMap<String, String>();
    private static TokenHandler mInstance = null;
    private TokenHandler(){
        
    }
    public static TokenHandler getInstance(){
        if(mInstance == null)
            mInstance = new TokenHandler();
        return mInstance;
    }
    public String getId(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getId();
    }
    public String getSubject(String token){
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public String createTokenForUser(Usuario user){
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 2 );
        //cal.add(Calendar.MINUTE, 0);
        String token = Jwts.builder().setSubject(user.getNom())
                .setId(String.valueOf(user.getUsuId()))
                .setExpiration(cal.getTime())
                .signWith(SignatureAlgorithm.HS512, key).compact();
        //guardar usuario en bd
        //tokens.put(user.getNombre(),token);
        return token;
    }
    public boolean validarToken(String token){
        
        try {
            String subject = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
            //if( tokens.containsKey( subject ))
            return true;
            //System.out.println("TOEKEN NO PERTENECE A NINGUN USUARIO");
            //return false;
        }catch (ExpiredJwtException e) {
            System.out.println("TOKEN EXPIRADO: "+e);
            return false;
        }catch (Exception e) {
            System.out.println("OTRO ERROR "+e.getClass().getName());
            return false;
        }
    }
    public String getTokenForUser(String user){
        if(tokens.containsKey(user)){
            return tokens.get(user);
        }
        return null;
    }
}
