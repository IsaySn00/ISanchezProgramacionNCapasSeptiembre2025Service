package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;


public interface IDireccionJPA {

    Result AddDireccion(DireccionJPA direccion, int id);
    Result UpdateDireccion(DireccionJPA direccion, int id);
    Result DeleteDireccion(int id);
    Result GetAddressById(int id);
}
