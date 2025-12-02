package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO.EstadoJPADAOImplementation;
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
@RequestMapping("/api/estado")
public class EstadoRestController {

    @Autowired
    private EstadoJPADAOImplementation estadoJPADAOImplementation;
    
    @GetMapping("estados/{idPais}")
    public ResponseEntity GetByIdPais(@PathVariable("idPais") int idPais){
        Result result = new Result();
        
        try{
            result = estadoJPADAOImplementation.GetAll(idPais);
            result.status = 200;
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
}
