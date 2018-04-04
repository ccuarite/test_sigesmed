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
@Table(name="tipo_organizacion",schema="public")
public class TipoOrganizacion  implements java.io.Serializable {

    @Id
    @Column(name="tip_org_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_tipoorg", sequenceName="tipo_organizacion_tip_org_id_seq" )
    @GeneratedValue(generator="secuencia_tipoorg")
    private short tipOrgId;
    @Column(name="cod", nullable=false, length=4)
    private String cod;
    @Column(name="nom", nullable=false, length=64)
    private String nom;
    @Column(name="des", length=256)
    private String des;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", nullable=false, length=29)
    private Date fecMod;
    @Column(name="usu_mod", nullable=false)
    private int usuMod;
    @Column(name="est_reg", nullable=false, length=1)
    private char estReg;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="tipoOrganizacion")
    private List<Organizacion> organizaciones ;

    public TipoOrganizacion() {
    }
    
    public TipoOrganizacion(short tipOrgId) {
        this.tipOrgId = tipOrgId;
    }

	
    public TipoOrganizacion(short tipOrgId, String cod, String nom, Date fecMod, int usuMod, char estReg) {
        this.tipOrgId = tipOrgId;
        this.cod = cod;
        this.nom = nom;
        this.fecMod = fecMod;
        this.usuMod = usuMod;
        this.estReg = estReg;
    }
    public TipoOrganizacion(short tipOrgId, String cod, String nom, String des, Date fecMod, int usuMod, char estReg, List<Organizacion> organizaciones) {
       this.tipOrgId = tipOrgId;
       this.cod = cod;
       this.nom = nom;
       this.des = des;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
       this.organizaciones = organizaciones;
    }
   
    public short getTipOrgId() {
        return this.tipOrgId;
    }
    
    public void setTipOrgId(short tipOrgId) {
        this.tipOrgId = tipOrgId;
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

    public List<Organizacion> getOrganizaciones() {
        return this.organizaciones;
    }
    public void setOrganizaciones(List<Organizacion> organizaciones) {
        this.organizaciones = organizaciones;
    }




}


