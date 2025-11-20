package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public Result AddDireccion(DireccionJPA direccion, int id) {
        Result result = new Result();
        
        try{
            direccion.UsuarioJPA = new UsuarioJPA();
            direccion.UsuarioJPA.setIdUsuario(id);
            
            entityManager.persist(direccion);
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional()
    public Result UpdateDireccion(DireccionJPA direccion, int id) {
        Result result = new Result();
        try{
           
            direccion.UsuarioJPA = new UsuarioJPA();
            direccion.UsuarioJPA.setIdUsuario(id);
            
            entityManager.merge(direccion);
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional
    public Result DeleteDireccion(int id) {
        Result result = new Result();
        
        try{
            DireccionJPA direccionJPA = entityManager.getReference(DireccionJPA.class, id);
            entityManager.remove(direccionJPA);
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetAddressById(int id) {
        Result result = new Result();
        
        try{
            DireccionJPA direccion = entityManager.find(DireccionJPA.class, id);
            
            result.object = direccion;
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
