package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO.RolJPADAOImplementation;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rol")
public class RolRestController {

    @Autowired
    private RolJPADAOImplementation rolJPADAOImplementation;
    
    @GetMapping("/roles")
    public ResponseEntity GetAll(){
        Result result = new Result();
        
        try{
            result = rolJPADAOImplementation.GetAll();
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
