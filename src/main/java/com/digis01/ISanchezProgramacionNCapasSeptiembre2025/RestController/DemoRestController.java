package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoRestController {

    @GetMapping("saludo")
    public String Saludo() {
        return "Hola Mundo";
    }

    @GetMapping("division")
    public ResponseEntity Division(@RequestParam("NumeroUno") String numeroUno, @RequestParam("NumeroDos") String numeroDos) {
        Result result = new Result();
        try {
            int numeroUnoInt = Integer.parseInt(numeroUno);
            int numeroDosInt = Integer.parseInt(numeroDos);

            if (numeroDosInt == 0) {
                result.correct = false;
                result.errorMessage = "Syntax Error";
                result.status = 400;
            } else {
                int division = numeroUnoInt / numeroDosInt;
                result.object = division;
                result.correct = true;
                result.status = 200;
            }

        } catch (NumberFormatException ex) {
            result.correct = false;
            result.errorMessage = "Syntax Error";
            result.status = 400;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("multiplicacion")
    public ResponseEntity Multiplicacion(@RequestParam("numeroUno") String numeroUno, @RequestParam("numeroDos") String numeroDos) {
        Result result = new Result();
        try {
            int numeroUnoInt = Integer.parseInt(numeroUno);
            int numeroDosInt = Integer.parseInt(numeroDos);
            
            int multiplicacion = numeroUnoInt * numeroDosInt;
            
            result.correct = true;
            result.object = multiplicacion;
            result.status = 200;

        } catch (NumberFormatException ex) {
            result.correct = false;
            result.errorMessage = "Syntax Error";
            result.status = 400;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }
    
    @PostMapping("suma")
    public ResponseEntity Suma(@RequestBody int[] arregloNumeros){
        Result result = new Result();
        try{
            int sum = 0;
            
            for(int n : arregloNumeros){
                sum += n;
            }
            
            result.correct = true;
            result.object = sum;
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
