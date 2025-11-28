package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JWT.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/login")
    public ResponseEntity Login(@RequestBody UsuarioJPA usuarioJPA){
        Result result = new Result();
        
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuarioJPA.getUserName(),
                            usuarioJPA.getPasswordUser()
                    )
            );
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(usuarioJPA.getUserName());
            
            String tkn = jwtService.getToken(userDetails);
            
            result.correct = true;
            result.status = 200;
            result.object = tkn;
            
        }catch(Exception ex){
            result.correct = false;
            result.status = 401;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return ResponseEntity.status(result.status).body(result);
    }
}
