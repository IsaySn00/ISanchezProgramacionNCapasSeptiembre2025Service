package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Configuration;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JWT.JwtAuthenticationFilter;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service.UserDetailsJPAService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
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
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(configurer -> configurer
                .requestMatchers("api/auth/login", "/login").permitAll()
                .requestMatchers("api/usuario/**").hasAnyRole("admin", "usuario", "invitado")
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
