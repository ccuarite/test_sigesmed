package com.dremo.ucsm.gsc.sigesmed.core.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
@Table(name="tipo_tramite" ,schema="administrativo")
public class TipoTramite  implements java.io.Serializable {


    @Id
    @Column(name="tip_tra_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_tipotramite", sequenceName="administrativo.tipo_tramite_tip_tra_id_seq" )
    @GeneratedValue(generator="secuencia_tipotramite")
    private int tipTraId;
    @Column(name="cod", nullable=false, length=16)
    private String cod;
    @Column(name="nom", length=64)
    private String nom;
    @Column(name="des", length=256)
    private String des;
    @Column(name="dur")
    private short dur;
    @Column(name="cos", precision=9)
    private BigDecimal cos;
    @Column(name="tip")
    private Boolean tip;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", length=29)
    private Date fecMod;
    @Column(name="usu_mod")
    private Integer usuMod;
    @Column(name="est_reg", length=1)
    private Character estReg;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="tip_org_id", nullable=false)
    private TipoOrganizacion tipoOrganizacion;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="tipoTramite",cascade=CascadeType.PERSIST)
    private List<RequisitoTramite> requisitoTramites ;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="tipoTramite",cascade=CascadeType.PERSIST)
    private List<RutaTramite> rutaTramites ;

    public TipoTramite() {
    }

	
    public TipoTramite(int tipTraId, String cod) {
        this.tipTraId = tipTraId;
        this.cod = cod;
    }
    public TipoTramite(int tipTraId, String cod, String nom, String des, short dur, BigDecimal cos, Boolean tip, Date fecMod, Integer usuMod, Character estReg, TipoOrganizacion tipoOrganizacion) {
       this.tipTraId = tipTraId;
       this.cod = cod;
       this.nom = nom;
       this.des = des;
       this.dur = dur;
       this.cos = cos;
       this.tip = tip;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
       this.tipoOrganizacion = tipoOrganizacion;
    }
   
    public int getTipTraId() {
        return this.tipTraId;
    }
    public void setTipTraId(int tipTraId) {
        this.tipTraId = tipTraId;
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

    public short getDur() {
        return this.dur;
    }
    public void setDur(short dur) {
        this.dur = dur;
    }
    
    public BigDecimal getCos() {
        return this.cos;
    }
    public void setCos(BigDecimal cos) {
        this.cos = cos;
    }

    
    public Boolean getTip() {
        return this.tip;
    }
    public void setTip(Boolean tip) {
        this.tip = tip;
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
    
    public TipoOrganizacion getTipoOrganizacion() {
        return this.tipoOrganizacion;
    }    
    public void setTipoOrganizacion(TipoOrganizacion tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }


    public List<RequisitoTramite> getRequisitoTramites() {
        return this.requisitoTramites;
    }    
    public void setRequisitoTramites(List<RequisitoTramite> requisitoTramites) {
        this.requisitoTramites = requisitoTramites;
    }

    public List<RutaTramite> getRutaTramites() {
        return this.rutaTramites;
    }
    public void setRutaTramites(List<RutaTramite> rutaTramites) {
        this.rutaTramites = rutaTramites;
    }
}


