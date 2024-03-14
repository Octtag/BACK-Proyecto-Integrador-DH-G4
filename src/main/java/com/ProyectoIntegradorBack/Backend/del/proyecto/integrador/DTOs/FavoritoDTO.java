package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class FavoritoDTO {
    private Long id;
    private String userName;
    private Long idExcursion;
}
