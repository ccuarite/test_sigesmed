package com.dremo.ucsm.gsc.sigesmed.core.entity;

import java.util.Date;
import java.util.List;
import java.util.List;
import javax.persistence.CascadeType;
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
@Table(name="rol" ,schema="public")
public class Rol  implements java.io.Serializable {


    @Id
    @Column(name="rol_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_rol", sequenceName="rol_rol_id_seq",allocationSize=1 )
    @GeneratedValue(generator="secuencia_rol")
    private int rolId;
    @Column(name="abr", length=8)
    private String abr;
    @Column(name="nom", nullable=false, length=32)
    private String nom;
    @Column(name="des", length=256)
    private String des;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", length=29)
    private Date fecMod;
    @Column(name="usu_mod")
    private Integer usuMod;
    @Column(name="est_reg", length=1)
    private Character estReg;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="claveRolFuncion.rol",cascade=CascadeType.ALL)
    private List<RolFuncion> rolFunciones;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="rol")
    private List<Usuario> usuarios ;

    public Rol() {
    }
    public Rol(int rolId) {
        this.rolId = rolId;
    }
	
    public Rol(int rolId, String nom) {
        this.rolId = rolId;
        this.nom = nom;
    }
    public Rol(int rolId, String abr, String nom, String des, Date fecMod, Integer usuMod, Character estReg, List<RolFuncion> rolFunciones, List<Usuario> usuarios) {
       this.rolId = rolId;
       this.abr = abr;
       this.nom = nom;
       this.des = des;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
       this.rolFunciones = rolFunciones;
       this.usuarios = usuarios;
    }
   
    public int getRolId() {
        return this.rolId;
    }
    
    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    
    public String getAbr() {
        return this.abr;
    }
    
    public void setAbr(String abr) {
        this.abr = abr;
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
    
    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }




}


