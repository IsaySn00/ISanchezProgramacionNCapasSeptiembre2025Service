package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;


public interface IColoniaJPA {

    Result GetAllColoniaByIdMunicipio(int idMunicipio);
    
    Result GetCodigoPostalByNameColoniaIdMnpio(String nombreColonia, int idMunicipio);
    
    Result GetByIdMnpioCodigoPostal(int idMunicipio, String codigoPostal);
}
