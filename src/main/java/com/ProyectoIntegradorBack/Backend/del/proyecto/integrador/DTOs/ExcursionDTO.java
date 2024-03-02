package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class ExcursionDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private String destino;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String itinerario;
    private MultipartFile[] imagenes;

}

