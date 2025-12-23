package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Configuration;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JWT.JwtAuthenticationFilter;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service.UserDetailsJPAService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsJPAService userDetailsJPAService;

    public SpringSecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, UserDetailsJPAService userDetailsJPAService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsJPAService = userDetailsJPAService;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOriginPatterns(List.of("http://localhost:8081"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);
            return config;
        }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/auth/verificar").permitAll()
                .requestMatchers("/api/auth/reenviar").permitAll()
                .requestMatchers("/api/auth/estadoVerificacion").permitAll()
                .requestMatchers("/api/usuarios/enviarCorreoPassword").permitAll()
                .requestMatchers("/api/usuarios/recuperarPassword").permitAll()
                .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/v3/api-docs.yaml",
                        "/v3/api-docs.json"
                ).permitAll()
                //                .requestMatchers(
                //                        "/api/rol/roles",
                //                        "/api/pais/paises/**",
                //                        "/api/municipio/municipio/**",
                //                        "/api/estado/estados/**",
                //                        "/api/colonia/colonias/**",
                //                        "/api/auth/logout"
                //                ).hasAnyRole("admin", "usuario", "invitado")
                //                .requestMatchers(HttpMethod.POST, "/api/usuarios/usuario").hasAnyRole("admin", "usuario", "invitado")
                //                .requestMatchers(HttpMethod.GET, "/api/usuarios/usuario").hasRole("admin")
                //                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/usuario/**").hasRole("admin")
                //                .requestMatchers("/api/usuario/**").hasAnyRole("admin", "usuario", "invitado")
                .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsJPAService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
