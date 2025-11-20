package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO.DireccionJPADAOImplementation;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionRestController {

    @Autowired
    private DireccionJPADAOImplementation direccionJPADAOImplementation;
    
    @GetMapping("/direccion/{idDireccion}")
    public ResponseEntity GetDireccionById(@PathVariable("idDireccion") int idDireccion){
        Result result = new Result();
        
        try{
            result = direccionJPADAOImplementation.GetAddressById(idDireccion);
            result.correct = true;
            result.status = 200;
            
        }catch(Exception ex){
            result.correct = false;
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @PostMapping("/direccion")
    public ResponseEntity AddDireccion(@RequestBody DireccionJPA direccion, 
            @RequestParam("idUsuario") int id){
        Result result = new Result();
        
        try{
            direccionJPADAOImplementation.AddDireccion(direccion, id);
            result.status = 201;
            result.correct = true;
            result.object = "Se ha agregado una dirección al usuario";
            
        }catch(Exception ex){
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.correct = false;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @PutMapping("/direccion")
    public ResponseEntity UpdateDireccion(@RequestBody DireccionJPA direccion, 
            @RequestParam("idUsuario") int id){
        Result result = new Result();
        
        try{
            direccionJPADAOImplementation.UpdateDireccion(direccion, id);
            result.status = 202;
            result.correct = true;
            result.object = "Se ha actualizado la dirección al usuario";
            
        }catch(Exception ex){
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.correct = false;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @DeleteMapping("/direccion/{idDireccion}")
    public ResponseEntity DeleteUsuario(@PathVariable("idDireccion") int id){
        Result result = new Result();
        
        try{         
            direccionJPADAOImplementation.DeleteDireccion(id);
            result.correct = true;
            result.status = 200;
            result.object = "Dirección eliminada exitosamente";
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
}
