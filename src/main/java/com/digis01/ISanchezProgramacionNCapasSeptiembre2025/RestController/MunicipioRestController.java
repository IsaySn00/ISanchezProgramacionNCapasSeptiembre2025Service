package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO.MunicipioJPADAOImplementation;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/municipio")
public class MunicipioRestController {

    @Autowired
    private MunicipioJPADAOImplementation municipioJPADAOImplementation;
    
    @GetMapping("/municipios/{idEstado}")
    public ResponseEntity GetByIdEstado(@PathVariable("idEstado") int idEstado){
        Result result = new Result();
        
        try{
            result = municipioJPADAOImplementation.GetAllMunicipioByIdEstado(idEstado);
            result.correct = true;
            result.status = 200;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
}
