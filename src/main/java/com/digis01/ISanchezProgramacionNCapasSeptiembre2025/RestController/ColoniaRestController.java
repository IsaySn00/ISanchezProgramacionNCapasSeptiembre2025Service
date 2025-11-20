package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO.ColoniaJPADAOImplementation;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/colonia")
public class ColoniaRestController {

    @Autowired
    private ColoniaJPADAOImplementation coloniaJPADAOImplementation;

    @GetMapping("/colonias/{idMunicipio}")
    public ResponseEntity GetColonias(@PathVariable("idMunicipio") int idMunicipio) {
        Result result = new Result();

        try {
            result = coloniaJPADAOImplementation.GetAllColoniaByIdMunicipio(idMunicipio);
            result.status = 201;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }
    
    @GetMapping("/colonias/{idMunicipio}/{codigoPostal}")
    public ResponseEntity GetByMnpioCodigoPostal(@PathVariable("idMunicipio") int idMunicipio,
            @PathVariable("codigoPostal") String codigoPostal) {

        Result result = new Result();

        try {
            result = coloniaJPADAOImplementation.GetByIdMnpioCodigoPostal(idMunicipio, codigoPostal);
            result.status = 201;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }
}
