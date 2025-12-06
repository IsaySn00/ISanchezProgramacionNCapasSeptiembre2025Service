package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JWT.JwtService;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JWT.TokenUsageService;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service.EmailService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @Autowired
    private TokenUsageService tokenUsageService;
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity Login(@RequestBody UsuarioJPA usuarioJPA) {
        Result result = new Result();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuarioJPA.getUserName(),
                            usuarioJPA.getPasswordUser()
                    )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(usuarioJPA.getUserName());

            String tkn = jwtService.getToken(userDetails);

            UsuarioJPA usuario = (UsuarioJPA) usuarioJPADAOImplementation.GetUsuarioByUserName(usuarioJPA.getUserName()).object;

            if (usuario.getIsVerified() == 0) {
                result.correct = false;
                result.status = 403;
                result.object = "Cuenta no verificada";
                return ResponseEntity.status(result.status).body(result);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("token", tkn);
            response.put("rol", usuario.RolJPA.getNombreRol());
            response.put("idUsuario", usuario.getIdUsuario());

            result.correct = true;
            result.status = 200;
            result.object = response;

        } catch (Exception ex) {
            result.correct = false;
            result.status = 401;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping("/logout")
    public ResponseEntity Logout(@RequestHeader("Authorization") String authHeader) {

        Result result = new Result();

        try {
            String token = authHeader.replace("Bearer ", "").trim();
            tokenUsageService.resetToken(token);
            tokenUsageService.blackList(token);

            result.correct = true;
            result.status = 200;
            result.object = "La sesión se ha cerrado";

        } catch (Exception ex) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping("/reenviar")
    public ResponseEntity reenviar(@RequestParam String email) {
        Result result = new Result();

        try {

            UsuarioJPA usuario = (UsuarioJPA) usuarioJPADAOImplementation.GetUsuarioByEmail(email).object;

            if (usuario.getIsVerified() == 1) {
                result.correct = false;
                result.status = 409;
                result.object = "La cuenta ya esta verificada";
                return ResponseEntity.status(result.status).body(result);
            }
            
            String tkn = jwtService.generateVerificationToken(email);
            String link = "http://localhost:8080/api/usuario/verificar?token=" + tkn;
            
            emailService.sendEmail(
                    usuario.getEmailUsuario(), 
                    "Verifica tu cuenta", 
                    "Haz clic para verificar tu cuenta " + link
            );
            
            result.correct = true;
            result.status = 200;
            result.object = "Correo enviado correctamente";

        } catch (Exception ex) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return ResponseEntity.status(result.status).body(result);
    }
}
