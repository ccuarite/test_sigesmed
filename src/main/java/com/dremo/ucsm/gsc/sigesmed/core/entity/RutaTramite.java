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
@Table(name="ruta_tramite" ,schema="administrativo")
public class RutaTramite  implements java.io.Serializable {

    @Id 
    @Column(name="rut_tra_id", unique=true, nullable=false)
    @SequenceGenerator(name = "secuencia_rutatramite", sequenceName="administrativo.ruta_tramite_rut_tra_id_seq" )
    @GeneratedValue(generator="secuencia_rutatramite")
    private short rutTraId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="tip_tra_id", nullable=false)
    private TipoTramite tipoTramite;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="are_ori_id", nullable=false)
    private Area areaOrigen;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="are_des_id", nullable=false)
    private Area areaDestino;
    @Column(name="des", length=256)
    private String des;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fec_mod", length=29)
    private Date fecMod;
    @Column(name="usu_mod")
    private Integer usuMod;
    @Column(name="est_reg", length=1)
    private Character estReg;

    public RutaTramite() {
    }

	
    public RutaTramite(short rutTraId, TipoTramite tipoTramite) {
        this.rutTraId = rutTraId;
        this.tipoTramite = tipoTramite;
    }
    public RutaTramite(short rutTraId, TipoTramite tipoTramite, String des, Area areaOrigen, Area areaDestino, Date fecMod, Integer usuMod, Character estReg) {
       this.rutTraId = rutTraId;
       this.tipoTramite = tipoTramite;
       this.des = des;
       this.areaOrigen = areaOrigen;
       this.areaDestino = areaDestino;
       this.fecMod = fecMod;
       this.usuMod = usuMod;
       this.estReg = estReg;
    }
   
     
    public short getRutTraId() {
        return this.rutTraId;
    }
    
    public void setRutTraId(short rutTraId) {
        this.rutTraId = rutTraId;
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
    
    public Area getAreaOrigen() {
        return this.areaOrigen;
    }
    public void setAreaOrigen(Area areaOrigen) {
        this.areaOrigen = areaOrigen;
    }
    public Area getAreaDestino() {
        return this.areaDestino;
    }
    public void setAreaDestino(Area areaDestino) {
        this.areaDestino = areaDestino;
    }




}


