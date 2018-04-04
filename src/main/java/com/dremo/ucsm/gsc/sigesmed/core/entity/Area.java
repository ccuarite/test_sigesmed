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
@Table(name="area" ,schema="public")
public class Area  implements java.io.Serializable {

    @Id
    @Column(name="are_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_subare", sequenceName="area_are_id_seq" )
    @GeneratedValue(generator="secuencia_subare")
    private int areId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="org_id", nullable=false)
    private Organizacion organizacion;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="are_pad_id")
    private Area areaPadre;
    @Column(name="cod", nullable=false, length=8)
    private String cod;
    @Column(name="nom", nullable=false, length=48)
    private String nom;
    @Column(name="ali", length=256)
    private String ali;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", length=29)
    private Date fecMod;
    @Column(name="usu_mod")
    private Integer usuMod;
    @Column(name="est_reg", length=1)
    private Character estReg;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="areaPadre")
    private List<Area> areas;

    public Area() {
    }
    public Area(int areId) {
        this.areId = areId;
    }

	
    public Area(int areId, Organizacion organizacion, String cod, String nom) {
        this.areId = areId;
        this.organizacion = organizacion;
        this.cod = cod;
        this.nom = nom;
    }
    public Area(int areId, Organizacion organizacion,String cod, String nom, String ali, Date fecMod, Integer usuMod, Character estReg) {
       this.areId = areId;
       this.organizacion = organizacion;
       this.cod = cod;
       this.nom = nom;
       this.ali = ali;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
    }
    public Area(int areId, Organizacion organizacion, Area areaPadre, String cod, String nom, String ali, Date fecMod, Integer usuMod, Character estReg) {
       this.areId = areId;
       this.organizacion = organizacion;
       this.areaPadre = areaPadre;
       this.cod = cod;
       this.nom = nom;
       this.ali = ali;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
    }
   
    public int getAreId() {
        return this.areId;
    }
    
    public void setAreId(int areId) {
        this.areId = areId;
    }

    public Organizacion getOrganizacion() {
        return this.organizacion;
    }    
    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }
    
    public Area getAreaPadre() {
        return this.areaPadre;
    }    
    public void setAreaPadre(Area areaPadre) {
        this.areaPadre = areaPadre;
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

    
    public String getAli() {
        return this.ali;
    }
    public void setAli(String ali) {
        this.ali = ali;
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
    
    public List<Area> getAreas() {
        return this.areas;
    }
    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }




}


