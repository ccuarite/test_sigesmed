package com.dremo.ucsm.gsc.sigesmed.core.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="rol_funcion",schema="public")
@AssociationOverrides({
    @AssociationOverride(name = "claveRolFuncion.rol", 
            joinColumns = @JoinColumn(name = "rol_id")),
    @AssociationOverride(name = "claveRolFuncion.funcionSistema", 
            joinColumns = @JoinColumn(name = "fun_sis_id")) })
public class RolFuncion  implements java.io.Serializable {
    
    private RolFuncionId claveRolFuncion = new RolFuncionId();
    
    private Short num;

    public RolFuncion() {
    }
    
    public RolFuncion( FuncionSistema funcionSistema, Rol rol, Short num) {
       claveRolFuncion.setFuncionSistema( funcionSistema );
       claveRolFuncion.setRol(rol);
       this.num = num;
    }
    @EmbeddedId
    public RolFuncionId getClaveRolFuncion() {
        return this.claveRolFuncion;
    }
    public void setClaveRolFuncion(RolFuncionId claveRolFuncion) {
        this.claveRolFuncion = claveRolFuncion;
    }
    
    
    @Transient
    public FuncionSistema getFuncionSistema() {
        return claveRolFuncion.getFuncionSistema();
    }
    public void setFuncionSistema(FuncionSistema funcionSistema) {
        claveRolFuncion.setFuncionSistema( funcionSistema );
    }
    
    @Transient
    public Rol getRol() {
        return claveRolFuncion.getRol();
    }
    public void setRol(Rol rol) {
        claveRolFuncion.setRol( rol );
    }

    @Column(name="num")
    public Short getNum() {
        return this.num;
    }    
    public void setNum(Short num) {
        this.num = num;
    }




}


