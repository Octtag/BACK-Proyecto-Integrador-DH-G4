package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Caracteristica;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaracteristicaDTO {
    private Long id;
    private String tipo;
    private String icono;
}

