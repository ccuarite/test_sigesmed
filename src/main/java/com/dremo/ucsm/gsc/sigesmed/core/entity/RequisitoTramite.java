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

@Entity
@Table(name="requisito_tramite" ,schema="administrativo")
public class RequisitoTramite  implements java.io.Serializable {
    
    @Id
    @Column(name="req_tra_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_requisitotramite", sequenceName="administrativo.requisito_tramite_req_tra_id_seq" )
    @GeneratedValue(generator="secuencia_requisitotramite")
    private short reqTraId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="tip_tra_id", nullable=false)
    private TipoTramite tipoTramite;
    @Column(name="des", length=256)
    private String des;
    @Column(name="nom_arc_adj", length=128)
    private String nomArcAdj;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", length=29)
    private Date fecMod;
    @Column(name="usu_mod")
    private Integer usuMod;
    @Column(name="est_reg", length=1)
    private Character estReg;

    public RequisitoTramite() {
    }

	
    public RequisitoTramite(short reqTraId, TipoTramite tipoTramite) {
        this.reqTraId = reqTraId;
        this.tipoTramite = tipoTramite;
    }
    public RequisitoTramite(short reqTraId, TipoTramite tipoTramite, String des,String nomArcAdj, Date fecMod, Integer usuMod, Character estReg) {
       this.reqTraId = reqTraId;
       this.tipoTramite = tipoTramite;
       this.nomArcAdj = nomArcAdj;
       this.des = des;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
    }
   
     
    public short getReqTraId() {
        return this.reqTraId;
    }
    
    public void setReqTraId(short reqTraId) {
        this.reqTraId = reqTraId;
    }

    public TipoTramite getTipoTramite() {
        return this.tipoTramite;
    }
    
    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    
    public String getDes() {
        return this.des;
    }
    public void setDes(String des) {
        this.des = des;
    }
    
    public String getNomArcAdj() {
        return this.nomArcAdj;
    }
    public void setNomArcAdj(String nomArcAdj) {
        this.nomArcAdj = nomArcAdj;
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




}


