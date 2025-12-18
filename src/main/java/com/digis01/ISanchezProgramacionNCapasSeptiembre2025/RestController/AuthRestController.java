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
import org.springframework.web.bind.annotation.GetMapping;
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
                            usuarioJPA.getEmailUsuario(),
                            usuarioJPA.getPasswordUser()
                    )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(usuarioJPA.getEmailUsuario());

            String tkn = jwtService.getToken(userDetails);

            UsuarioJPA usuario = (UsuarioJPA) usuarioJPADAOImplementation.GetUsuarioByEmail(usuarioJPA.getEmailUsuario()).object;

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

    @GetMapping("/verificar")
    public ResponseEntity verificarCuenta(@RequestParam String token) {
        Result result = new Result();

        try {
            String email = jwtService.getEmailFromToken(token);
            String type = jwtService.getClaim(token, claims -> (String) claims.get("type"));

            if (!"verification".equals(type)) {
                result.status = 498;
                result.correct = false;
                result.redirectLink = "http://localhost:8081/usuario/verificadoError";
            }

            usuarioJPADAOImplementation.UpdateStatusVerificacion(email, 1);

            result.correct = true;
            result.status = 302;
            result.object = "Cuenta verificada de manera exitosa";
            result.redirectLink = "http://localhost:8081/usuario/verificadoExitoso";

        } catch (Exception ex) {
            result.status = 400;
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
            result.redirectLink = "http://localhost:8081/usuario/verificadoError";
        }

        return ResponseEntity.status(result.status).header("Location", result.redirectLink).body(result);
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
            String link = "http://localhost:8080/api/auth/verificar?token=" + tkn;

            emailService.sendEmail(usuario.getEmailUsuario(), link, "add");

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
