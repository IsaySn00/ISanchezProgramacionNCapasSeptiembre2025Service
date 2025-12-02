package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JWT.JwtService;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JWT.TokenUsageService;
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
}
