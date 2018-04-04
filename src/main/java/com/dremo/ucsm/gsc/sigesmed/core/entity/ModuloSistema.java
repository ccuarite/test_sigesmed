package com.dremo.ucsm.gsc.sigesmed.core.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="modulo_sistema",schema="public")
public class ModuloSistema  implements java.io.Serializable {


    @Id
    @Column(name="mod_sis_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_modsis", sequenceName="modulo_sistema_mod_sis_id_seq" )
    @GeneratedValue(generator="secuencia_modsis")
    private short modSisId;
    @Column(name="cod", nullable=false, length=4)
    private String cod;
    @Column(name="nom", nullable=false, length=32)
    private String nom;
    @Column(name="des", length=256)
    private String des;
    @Column(name="ico", length=20)
    private String ico;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", length=29)
    private Date fecMod;
    @Column(name="usu_mod")
    private Integer usuMod;
    @Column(name="est_reg", length=1)
    private Character estReg;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="moduloSistema")
    private List<SubModuloSistema> subModuloSistemas;

    public ModuloSistema() {
    }
    public ModuloSistema(short modSisId) {
        this.modSisId = modSisId;
    }
	
    public ModuloSistema(short modSisId, String cod, String nom) {
        this.modSisId = modSisId;
        this.cod = cod;
        this.nom = nom;
    }
    public ModuloSistema(short modSisId, String cod, String nom, String des, String ico, Date fecMod, Integer usuMod, Character estReg, List<SubModuloSistema> subModuloSistemas) {
       this.modSisId = modSisId;
       this.cod = cod;
       this.nom = nom;
       this.des = des;
       this.ico = ico;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
       this.subModuloSistemas = subModuloSistemas;
    }
   
    public short getModSisId() {
        return this.modSisId;
    }
    
    public void setModSisId(short modSisId) {
        this.modSisId = modSisId;
    }

    
    public String getCod() {
        return this.cod;
    }
    
    public void setCod(String cod) {
        this.cod = cod;
    }

    
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }

    
    public String getIco() {
        return this.ico;
    }
    
    public void setIco(String ico) {
        this.ico = ico;
    }

    public Date getFecMod() {
        return this.fecMod;
    }
    
    public void setFecMod(Date fecMod) {
        this.fecMod = fecMod;
    }

    
    public Integer getUsuMod() {
        return this.usuMod;
    }
    
    public void setUsuMod(Integer usuMod) {
        this.usuMod = usuMod;
    }

    
    public Character getEstReg() {
        return this.estReg;
    }
    
    public void setEstReg(Character estReg) {
        this.estReg = estReg;
    }


    public List<SubModuloSistema> getSubModuloSistemas() {
        return this.subModuloSistemas;
    }
    
    public void setSubModuloSistemas(List<SubModuloSistema> subModuloSistemas) {
        this.subModuloSistemas = subModuloSistemas;
    }




}


