package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PAIS")
public class PaisJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpais")
    private int IdPais;
    
    @Column(name = "nombre")
    private String NombrePais;
    
    public PaisJPA(){
        
    }
    
    public PaisJPA(int IdPais, String NombrePais){
        this.IdPais = IdPais;
        this.NombrePais =  NombrePais;
    }
    
    public void setIdPais(int IdPais){
        this.IdPais = IdPais;
    }
    
    public int getIdPais(){
        return  IdPais;
    }
    
    public void setNombrePais(String NombrePais){
        this.NombrePais = NombrePais;
    }
    
    public String getNombrePais(){
        return NombrePais;
    }
}
