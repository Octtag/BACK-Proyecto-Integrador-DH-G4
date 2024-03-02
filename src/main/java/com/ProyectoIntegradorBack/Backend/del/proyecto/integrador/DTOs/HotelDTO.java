package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class HotelDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private String nombreHotel;
    private String nit;
    private String direccion;
    private String telefono;
    private String categoria;
    private String urlWeb;
    private MultipartFile[] imagenes;
}
