package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ComentarioDTO {
    private Long id;
    private Long puntuacion;
    private String detalle;
    private Long idUser;
    private String nombre;
    private String apellido;
    private Long idExcursion;
}

