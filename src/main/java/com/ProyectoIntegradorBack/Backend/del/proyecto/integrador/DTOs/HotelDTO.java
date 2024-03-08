package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class HotelDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String nombreHotel;
    private String nit;
    private String direccion;
    private String telefono;
    private String categoria;
    private String urlWeb;
    private List<ImagenDTO> imagenes;
}
