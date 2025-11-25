package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DTO.UsuarioUpdateDTO;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import java.util.List;


public interface IUsuarioJPA {
    
    Result GetAll();
    Result AddUsuario(UsuarioJPA usuario);
    Result GetById(int id);
    Result UpdateUsuario(UsuarioUpdateDTO usuario);
    Result UpdateImgUsuario(UsuarioJPA usuario);
    Result DeleteUsuario(int id);
    Result AddUsuariosByFile(List<UsuarioJPA> usuarios);
    Result GetAllDinamico(UsuarioJPA usuario);
    Result BorradoLogicoUsuario(int idUsuario, int status);
}
