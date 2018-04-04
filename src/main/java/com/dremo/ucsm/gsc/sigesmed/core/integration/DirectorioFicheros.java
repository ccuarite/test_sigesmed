/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dremo.ucsm.gsc.sigesmed.core.integration;

import java.io.File;

/**
 *
 * @author Administrador
 */
public class DirectorioFicheros {
    public DirectorioFicheros(String nombre,File[]archivos){
            this.nombre = nombre;
            this.archivos = archivos;
        }
        public String nombre;
        public File[] archivos;
}
