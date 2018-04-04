package com.dremo.ucsm.gsc.sigesmed.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="usuario" ,schema="public", uniqueConstraints = @UniqueConstraint(columnNames="nom") )
public class Usuario  implements java.io.Serializable {

    @Id 
    @Column(name="usu_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_usuario", sequenceName="usuario_usu_id_seq" )
    @GeneratedValue(generator="secuencia_usuario")
    private int usuId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="org_id", nullable=false)
    private Organizacion organizacion;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rol_id", nullable=false)
    private Rol rol;
    @Column(name="nom", unique=true, nullable=false, length=64)
    private String nom;
    @Column(name="pas", length=16)
    private String pas;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_cre", nullable=false, length=29)
    private Date fecCre;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", length=29)
    private Date fecMod;
    @Column(name="usu_mod", nullable=false)
    private int usuMod;
    @Column(name="est_reg", nullable=false, length=1)
    private char estReg;

    public Usuario() {
    }

	
    public Usuario(int usuId, Organizacion organizacion, Rol rol, String nom, Date fecCre, int usuMod, char estReg) {
        this.usuId = usuId;
        this.organizacion = organizacion;
        this.rol = rol;
        this.nom = nom;
        this.fecCre = fecCre;
        this.usuMod = usuMod;
        this.estReg = estReg;
    }
    public Usuario(int usuId, Organizacion organizacion, Rol rol, String nom, String pas, Date fecCre, Date fecMod, int usuMod, char estReg) {
       this.usuId = usuId;
       this.organizacion = organizacion;
       this.rol = rol;
       this.nom = nom;
       this.pas = pas;
       this.fecCre = fecCre;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
    }
   
     
    public int getUsuId() {
        return this.usuId;
    }
    
    public void setUsuId(int usuId) {
        this.usuId = usuId;
    }

    public Organizacion getOrganizacion() {
        return this.organizacion;
    }
    
    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public Rol getRol() {
        return this.rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    public String getPas() {
        return this.pas;
    }
    
    public void setPas(String pas) {
        this.pas = pas;
    }

    public Date getFecCre() {
        return this.fecCre;
    }
    
    public void setFecCre(Date fecCre) {
        this.fecCre = fecCre;
    }

    public Date getFecMod() {
        return this.fecMod;
    }
    
    public void setFecMod(Date fecMod) {
        this.fecMod = fecMod;
    }

    
    public int getUsuMod() {
        return this.usuMod;
    }
    
    public void setUsuMod(int usuMod) {
        this.usuMod = usuMod;
    }

    
    public char getEstReg() {
        return this.estReg;
    }
    
    public void setEstReg(char estReg) {
        this.estReg = estReg;
    }




}


