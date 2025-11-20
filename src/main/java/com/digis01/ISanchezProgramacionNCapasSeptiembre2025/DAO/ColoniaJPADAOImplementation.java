package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.ColoniaJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAllColoniaByIdMunicipio(int idMunicipio) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {

            TypedQuery<ColoniaJPA> queryColonias = entityManager.createQuery("FROM ColoniaJPA WHERE MunicipioJPA.IdMunicipio =  :id ORDER BY NombreColonia", ColoniaJPA.class);
            queryColonias.setParameter("id", idMunicipio);
            List<ColoniaJPA> colonias = queryColonias.getResultList();

            result.object = colonias;

            result.correct = true;

        } catch (Exception ex) {
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetCodigoPostalByNameColoniaIdMnpio(String nombreColonia, int idMunicipio) {
        Result result = new Result();
        try {

            TypedQuery<ColoniaJPA> queryColonias = entityManager.createQuery("FROM ColoniaJPA WHERE MunicipioJPA.IdMunicipio =  :id AND NombreColonia = :nombre", ColoniaJPA.class);
            queryColonias.setParameter("id", idMunicipio);
            queryColonias.setParameter("nombre", nombreColonia);
            ColoniaJPA colonias = queryColonias.getSingleResult();

            String codigoPostal = colonias.getCodigoPostal();

            result.object = codigoPostal;

            result.correct = true;

        } catch (Exception ex) {
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetByIdMnpioCodigoPostal(int idMunicipio, String codigoPostal) {
        Result result = new Result();
        
        try{
            
            TypedQuery<ColoniaJPA> queryColonia = entityManager.createQuery("FROM ColoniaJPA WHERE MunicipioJPA.IdMunicipio = :id AND CodigoPostal = :codigoPostal", ColoniaJPA.class);
            queryColonia.setParameter("id", idMunicipio);
            queryColonia.setParameter("codigoPostal", codigoPostal);
            List<ColoniaJPA> colonias = queryColonia.getResultList();
            
            result.object = colonias;
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

}
