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
@Table(name = "MUNICIPIO")
public class MunicipioJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmunicipio")
    private int IdMunicipio;
    
    @Column(name = "nombre")
    private String NombreMunicipio;
    
    @ManyToOne
    @JoinColumn(name = "idestado")
    public EstadoJPA EstadoJPA;
    
    public MunicipioJPA(){
        
    }
    
    public MunicipioJPA(int IdMunicipio, String NombreMunicipio){
        this.IdMunicipio = IdMunicipio;
        this.NombreMunicipio = NombreMunicipio;
    }
    
    public void setIdMunicipio(int IdMunicipio){
        this.IdMunicipio = IdMunicipio;
    }
    
    public int getIdMunicipio(){
        return IdMunicipio;
    }
    
    public void setNombreMunicipio(String NombreMunicipio){
        this.NombreMunicipio  = NombreMunicipio;
    }
    
    public String getNombreMunicipio(){
        return NombreMunicipio;
    }
    
}
