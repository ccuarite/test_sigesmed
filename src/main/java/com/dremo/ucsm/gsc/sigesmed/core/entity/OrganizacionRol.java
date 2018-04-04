package com.dremo.ucsm.gsc.sigesmed.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="organizacion_rol" ,schema="public")
public class OrganizacionRol  implements java.io.Serializable {

    @Id
    @Column(name="org_rol_id", unique=true, nullable=false)
    private int orgRolId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="org_id", nullable=false)
    private Organizacion organizacion;
    @Column(name="rol_id", nullable=false)
    private int rolId;

    public OrganizacionRol() {
    }

    public OrganizacionRol(int orgRolId, Organizacion organizacion, int rolId) {
       this.orgRolId = orgRolId;
       this.organizacion = organizacion;
       this.rolId = rolId;
    }
   
    
    public int getOrgRolId() {
        return this.orgRolId;
    }
    
    public void setOrgRolId(int orgRolId) {
        this.orgRolId = orgRolId;
    }

    public Organizacion getOrganizacion() {
        return this.organizacion;
    }
    
    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    
    public int getRolId() {
        return this.rolId;
    }
    
    public void setRolId(int rolId) {
        this.rolId = rolId;
    }




}


