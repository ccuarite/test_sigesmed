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
@Table(name="organizacion",schema="public")
public class Organizacion  implements java.io.Serializable {


    @Id
    @Column(name="org_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_organizacion", sequenceName="organizacion_org_id_seq" )
    @GeneratedValue(generator="secuencia_organizacion")
    private int orgId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="tip_org_id", nullable=false)
    private TipoOrganizacion tipoOrganizacion;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="org_pad_id")
    private Organizacion organizacionPadre;
    @Column(name="cod", nullable=false, length=16)
    private String cod;
    @Column(name="nom", nullable=false, length=64)
    private String nom;
    @Column(name="ali", nullable=false, length=64)
    private String ali;
    @Column(name="des", length=256)
    private String des;
    @Column(name="dir", length=128)
    private String dir;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", length=29)
    private Date fecMod;
    @Column(name="usu_mod")
    private Integer usuMod;
    @Column(name="est_reg", length=1)
    private Character estReg;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="organizacion")
    private List<Usuario> usuarios;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="organizacion")
    private List<OrganizacionRol> organizacionRoles;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="organizacionPadre")
    private List<Organizacion> organizaciones;

    public Organizacion() {
    }
    public Organizacion(int orgId) {
        this.orgId = orgId;
    }
	
    public Organizacion(int orgId, TipoOrganizacion tipoOrganizacion, String cod, String nom) {
        this.orgId = orgId;
        this.tipoOrganizacion = tipoOrganizacion;
        this.cod = cod;
        this.nom = nom;
    }
    public Organizacion(int orgId, TipoOrganizacion tipoOrganizacion,String cod, String nom, String ali, String des, String dir, Date fecMod, Integer usuMod, Character estReg) {
       this.orgId = orgId;
       this.tipoOrganizacion = tipoOrganizacion;
       this.cod = cod;
       this.nom = nom;
       this.ali = ali;
       this.des = des;
       this.dir = dir;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
    }
    public Organizacion(int orgId, TipoOrganizacion tipoOrganizacion,Organizacion organizacionPadre, String cod, String nom, String ali, String des, String dir, Date fecMod, Integer usuMod, Character estReg, List<Usuario> usuarios, List<OrganizacionRol> organizacionRoles) {
       this.orgId = orgId;
       this.tipoOrganizacion = tipoOrganizacion;
       this.organizacionPadre = organizacionPadre;
       this.cod = cod;
       this.nom = nom;
       this.ali = ali;
       this.des = des;
       this.dir = dir;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
       this.usuarios = usuarios;
       this.organizacionRoles = organizacionRoles;
    }
   
    public int getOrgId() {
        return this.orgId;
    }
    
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public TipoOrganizacion getTipoOrganizacion() {
        return this.tipoOrganizacion;
    }    
    public void setTipoOrganizacion(TipoOrganizacion tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public Organizacion getOrganizacionPadre() {
        return this.organizacionPadre;
    }    
    public void setOrganizacionPadre(Organizacion organizacionPadre) {
        this.organizacionPadre = organizacionPadre;
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
    
    public String getDes() {
        return this.des;
    }
    public void setDes(String des) {
        this.des = des;
    }
    
    public String getDir() {
        return this.dir;
    }
    public void setDir(String dir) {
        this.dir = dir;
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

    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public List<OrganizacionRol> getOrganizacionRoles() {
        return this.organizacionRoles;
    }
    public void setOrganizacionRoles(List<OrganizacionRol> organizacionRoles) {
        this.organizacionRoles = organizacionRoles;
    }
    
    public List<Organizacion> getOrganizaciones() {
        return this.organizaciones;
    }
    public void setOrganizaciones(List<Organizacion> organizaciones) {
        this.organizaciones = organizaciones;
    }




}


