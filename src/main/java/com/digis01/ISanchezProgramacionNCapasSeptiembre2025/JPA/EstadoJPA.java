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
@Table(name = "ESTADO")
public class EstadoJPA {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestado")
    private int IdEstado;
    
    @Column(name = "nombre")
    private String NombreEstado;
    
    @ManyToOne
    @JoinColumn(name = "idpais")
    public PaisJPA PaisJPA;
    
    public EstadoJPA(){
        
    }
    
    public EstadoJPA(int IdEstado, String NombreEstado){
        this.IdEstado = IdEstado;
        this.NombreEstado = NombreEstado;
    }
    
    public void setIdEstado(int IdEstado){
        this.IdEstado = IdEstado;
    }
    
    public int getIdEstado(){
        return IdEstado;
    }
    
    public void setNombreEstado(String NombreEstado){
        this.NombreEstado = NombreEstado;
    }
    
    public String getNombreEstado(){
        return NombreEstado;
    }
}
