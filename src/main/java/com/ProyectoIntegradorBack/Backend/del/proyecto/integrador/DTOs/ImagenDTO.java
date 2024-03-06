package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Imagen;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ImagenDTO {
    private Long id;
    private String url;
}

