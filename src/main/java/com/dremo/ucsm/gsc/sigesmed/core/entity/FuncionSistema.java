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
@Table(name="funcion_sistema",schema="public")
public class FuncionSistema  implements java.io.Serializable {

    @Id
    @Column(name="fun_sis_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_funsis", sequenceName="funcion_sistema_fun_sis_id_seq" )
    @GeneratedValue(generator="secuencia_funsis")
    private int funSisId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sub_mod_sis_id", nullable=false)
    private SubModuloSistema subModuloSistema;

    @Column(name="nom", nullable=false, length=32)
    private String nom;     
    @Column(name="des", length=256)
    private String des;
    @Column(name="url", nullable=false, length=64)
    private String url;
    @Column(name="cla_nav", nullable=false, length=64)
    private String claNav;
    @Column(name="nom_con", nullable=false, length=64)
    private String nomCon;
    @Column(name="nom_int", nullable=false, length=64)
    private String nomInt;
    @Column(name="ico", nullable=false, length=32)
    private String ico;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", length=29)
    private Date fecMod;
    @Column(name="usu_mod")
    private Integer usuMod;
    @Column(name="est_reg", length=1)
    private Character estReg;
     
     @OneToMany(fetch=FetchType.LAZY, mappedBy="claveRolFuncion.funcionSistema")
     private List<RolFuncion> rolFunciones ;

    public FuncionSistema() {
    }
    
    public FuncionSistema(int funSisId) {
        this.funSisId = funSisId;
    }

	
    public FuncionSistema(int funSisId, SubModuloSistema subModuloSistema, String nom, String url, String claNav, String nomCon, String nomInt, String ico) {
        this.funSisId = funSisId;
        this.subModuloSistema = subModuloSistema;
        this.nom = nom;
        this.url = url;
        this.claNav = claNav;
        this.nomCon = nomCon;
        this.nomInt = nomInt;
        this.ico = ico;
    }
    public FuncionSistema(int funSisId, SubModuloSistema subModuloSistema, String nom, String des, String url, String claNav, String nomCon, String nomInt, String ico, Date fecMod, Integer usuMod, Character estReg, List<RolFuncion> rolFunciones) {
       this.funSisId = funSisId;
       this.subModuloSistema = subModuloSistema;
       this.nom = nom;
       this.des = des;
       this.url = url;
       this.claNav = claNav;
       this.nomCon = nomCon;
       this.nomInt = nomInt;
       this.ico = ico;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
       this.rolFunciones = rolFunciones;
    }
   
     
    public int getFunSisId() {
        return this.funSisId;
    }
    public void setFunSisId(int funSisId) {
        this.funSisId = funSisId;
    }


    public SubModuloSistema getSubModuloSistema() {
        return this.subModuloSistema;
    }
    public void setSubModuloSistema(SubModuloSistema subModuloSistema) {
        this.subModuloSistema = subModuloSistema;
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
    
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }    
    
    public String getClaNav() {
        return this.claNav;
    }    
    public void setClaNav(String claNav) {
        this.claNav = claNav;
    }
    
    public String getNomCon() {
        return this.nomCon;
    }
    public void setNomCon(String nomCon) {
        this.nomCon = nomCon;
    }
    
    public String getNomInt() {
        return this.nomInt;
    }
    public void setNomInt(String nomInt) {
        this.nomInt = nomInt;
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


    public List<RolFuncion> getRolFunciones() {
        return this.rolFunciones;
    }
    public void setRolFunciones(List<RolFuncion> rolFunciones) {
        this.rolFunciones = rolFunciones;
    }




}


