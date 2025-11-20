package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "COLONIA")
public class ColoniaJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcolonia")
    private int IdColonia;
    
    @Column(name = "nombre")
    private String NombreColonia;
    
    @Column(name = "codigopostal")
    private String CodigoPostal;
    
    @ManyToOne
    @JoinColumn(name = "idmunicipio")
    public MunicipioJPA MunicipioJPA;
    
    public ColoniaJPA(){
        
    }
    
    public ColoniaJPA(int IdColonia, String NombreColonia, String CodigoPostal){
        this.IdColonia = IdColonia;
        this.NombreColonia = NombreColonia;
        this.CodigoPostal = CodigoPostal;
    }
    
    public void setIdColonia(int IdColonia){
        this.IdColonia = IdColonia;
    }
    
    public int getIdColonia(){
        return IdColonia;
    }
    
    public void setNombreColonia(String NombreColonia){
        this.NombreColonia = NombreColonia;
    }
    
    public String getNombreColonia(){
        return NombreColonia;
    }
    
    public void setCodigoPostal(String CodigoPostal){
        this.CodigoPostal = CodigoPostal;
    }
    
    public String getCodigoPostal(){
        return CodigoPostal;
    }
    
}
