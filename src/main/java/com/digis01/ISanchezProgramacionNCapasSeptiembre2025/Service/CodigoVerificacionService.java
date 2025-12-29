package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class CodigoVerificacionService {

    private final Map<String , CodigoData> codigos = new ConcurrentHashMap<>();
    
    public void save(String email, String code){
        codigos.put(email, new CodigoData(code, false, System.currentTimeMillis() + 300_000));
    }
    
    public boolean isValid(String email, String code){
        CodigoData data = codigos.get(email);
        
        return data != null && data.codigo.equals(code) && data.expiresAt > System.currentTimeMillis();
    }
    
    public void markIsVerified(String email){
        codigos.get(email).verified = true;
    }
    
    public boolean canChangePassword(String email){
        CodigoData data = codigos.get(email);
        
        return data != null && data.verified;
    }
    
    public void invalidate(String email){
        codigos.remove(email);
    }
    
    static class CodigoData{
        String codigo;
        boolean verified;
        long expiresAt;
        
        CodigoData(String codigo, boolean verified, long expiresAt){
            this.codigo = codigo;
            this.verified = verified;
            this.expiresAt = expiresAt;
        }
    }
}
