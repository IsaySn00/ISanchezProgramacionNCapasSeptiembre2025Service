package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.PaisJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisJPADAOImplementation implements IPaisJPA{

    @Autowired 
    private EntityManager entityManager;
    
    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try{
            TypedQuery<PaisJPA> queryPais = entityManager.createQuery("FROM PaisJPA ORDER BY NombrePais", PaisJPA.class);
            List<PaisJPA> paisesJPA = queryPais.getResultList();
            
            result.object = paisesJPA;
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
