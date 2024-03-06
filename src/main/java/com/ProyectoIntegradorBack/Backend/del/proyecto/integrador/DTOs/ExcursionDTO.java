package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Imagen;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExcursionDTO {
    private Number id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String destino;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String itinerario;
    private List<ImagenDTO> imagenes;

}

