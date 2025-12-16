package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO.IUsuarioRepositoryDAO;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsJPAService implements UserDetailsService{
    
    private final IUsuarioRepositoryDAO iUsuarioRepositoryDAO;

    public UserDetailsJPAService(IUsuarioRepositoryDAO iUsuarioRepositoryDAO) {
        this.iUsuarioRepositoryDAO = iUsuarioRepositoryDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioJPA usuario = iUsuarioRepositoryDAO.findByEmailUsuario(email);

        boolean statusUsuario = usuario.getStatusUsuario() == 1 ? false : true;
        
            return User.withUsername(usuario.getEmailUsuario())
                .password(usuario.getPasswordUser())
                .roles(usuario.RolJPA.getNombreRol())
                .disabled(false)
                .build();
    }
}
