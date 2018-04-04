package com.dremo.ucsm.gsc.sigesmed.core.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class RolFuncionId  implements java.io.Serializable {
    
    
    private FuncionSistema funcionSistema;
    
    private Rol rol;
    
/*
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RolFuncionId) ) return false;
		 RolFuncionId castOther = ( RolFuncionId ) other; 
         
		 return (this.getRol()==castOther.getRol()) && (this.getFuncionSistema()==castOther.getFuncionSistema());
   }*//*
   public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolFuncionId that = (RolFuncionId) o;

        if (rol != null ? !rol.equals(that.rol) : that.rol != null) return false;
        if (funcionSistema != null ? !funcionSistema.equals(that.funcionSistema) : that.funcionSistema != null)return false;

        return true;
    }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getRol().hashCode();
         result = 37 * result + this.getFuncionSistema().hashCode();
         return result;
   }
   */
   @ManyToOne
   public FuncionSistema getFuncionSistema() {
        return this.funcionSistema;
    }
    
    public void setFuncionSistema(FuncionSistema funcionSistema) {
        this.funcionSistema = funcionSistema;
    }
    @ManyToOne
    public Rol getRol() {
        return this.rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }


}


