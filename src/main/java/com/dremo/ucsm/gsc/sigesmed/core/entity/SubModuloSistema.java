package com.dremo.ucsm.gsc.sigesmed.core.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="sub_modulo_sistema" ,schema="public")
public class SubModuloSistema  implements java.io.Serializable {


    @Id
    @Column(name="sub_mod_sis_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_submodsis", sequenceName="sub_modulo_sistema_sub_mod_sis_id_seq" )
    @GeneratedValue(generator="secuencia_submodsis")
    private int subModSisId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mod_sis_id", nullable=false)
    private ModuloSistema moduloSistema;
    @Column(name="cod", nullable=false, length=8)
    private String cod;
    @Column(name="nom", nullable=false, length=48)
    private String nom;
    @Column(name="des", length=256)
    private String des;
    @Column(name="ico", length=32)
    private String ico;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", length=29)
    private Date fecMod;
    @Column(name="usu_mod")
    private Integer usuMod;
    @Column(name="est_reg", length=1)
    private Character estReg;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="subModuloSistema")
    private List<FuncionSistema> funcionSistemas;

    public SubModuloSistema() {
    }
    public SubModuloSistema(int subModSisId) {
        this.subModSisId = subModSisId;
    }

	
    public SubModuloSistema(int subModSisId, ModuloSistema moduloSistema, String cod, String nom) {
        this.subModSisId = subModSisId;
        this.moduloSistema = moduloSistema;
        this.cod = cod;
        this.nom = nom;
    }
    public SubModuloSistema(int subModSisId, ModuloSistema moduloSistema, String cod, String nom, String des, String ico, Date fecMod, Integer usuMod, Character estReg, List<FuncionSistema> funcionSistemas) {
       this.subModSisId = subModSisId;
       this.moduloSistema = moduloSistema;
       this.cod = cod;
       this.nom = nom;
       this.des = des;
       this.ico = ico;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
       this.funcionSistemas = funcionSistemas;
    }
   
    public int getSubModSisId() {
        return this.subModSisId;
    }
    
    public void setSubModSisId(int subModSisId) {
        this.subModSisId = subModSisId;
    }

    public ModuloSistema getModuloSistema() {
        return this.moduloSistema;
    }
    
    public void setModuloSistema(ModuloSistema moduloSistema) {
        this.moduloSistema = moduloSistema;
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
    
    public List<FuncionSistema> getFuncionSistemas() {
        return this.funcionSistemas;
    }
    public void setFuncionSistemas(List<FuncionSistema> funcionSistemas) {
        this.funcionSistemas = funcionSistemas;
    }




}


