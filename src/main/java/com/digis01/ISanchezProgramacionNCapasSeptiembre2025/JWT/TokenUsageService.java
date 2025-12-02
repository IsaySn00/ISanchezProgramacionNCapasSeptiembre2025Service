package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JWT;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class TokenUsageService {
    
    private final Map<String, Integer> tokenUsageMap = new ConcurrentHashMap<>();
    
    private static final int MAX_REQUEST = 5;
    
    public boolean isAllowed(String token){
        int current = tokenUsageMap.getOrDefault(token, 0);
        
        if(current >= MAX_REQUEST){
            return false;
        }
        
        tokenUsageMap.put(token, current + 1);
        return true;
    }
    
    public void resetToken(String token){
        tokenUsageMap.remove(token);
    }

}
