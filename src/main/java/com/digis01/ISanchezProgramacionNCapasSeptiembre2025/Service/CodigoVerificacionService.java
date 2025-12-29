package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class CodigoVerificacionService {

    private final Map<String , String> codigos = new ConcurrentHashMap<>();
    
    public void save(String email, String code){
        codigos.put(email, code);
    }
    
    public boolean isValid(String email, String code){
        return code.equals(codigos.get(email));
    }
    
    public void invalidate(String email){
        codigos.remove(email);
    }
}
