package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.MunicipioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioJPADAOImplementation implements IMunicipioJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetAllMunicipioByIdEstado(int id) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try{
            
            TypedQuery<MunicipioJPA> queryMunicipios = entityManager.createQuery("FROM MunicipioJPA WHERE EstadoJPA.IdEstado = :id ORDER BY NombreMunicipio", MunicipioJPA.class);
            queryMunicipios.setParameter("id", id);
            List<MunicipioJPA> municipios = queryMunicipios.getResultList();
            
            result.object = municipios;
            
            result.correct = true;
            
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

}
