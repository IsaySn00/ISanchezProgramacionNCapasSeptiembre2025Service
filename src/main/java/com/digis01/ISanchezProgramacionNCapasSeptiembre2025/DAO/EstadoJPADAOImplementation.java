package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.EstadoJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoJPADAOImplementation implements IEstadoJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetAll(int idPais) {
            Result result = new Result();
        result.objects = new ArrayList<>();
        try{
            TypedQuery<EstadoJPA> queryEstado = entityManager.createQuery("FROM EstadoJPA WHERE PaisJPA.IdPais = :id ORDER BY NombreEstado", EstadoJPA.class);
            queryEstado.setParameter("id", idPais);
            List<EstadoJPA> estadosJPA = queryEstado.getResultList();
            
            result.object = estadosJPA;
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

}
