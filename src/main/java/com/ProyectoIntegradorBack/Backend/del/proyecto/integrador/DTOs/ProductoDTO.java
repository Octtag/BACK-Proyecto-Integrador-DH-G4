package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductoDTO {
    private String tipoProducto;
    private String nombre;
    private String destino;
    private LocalDateTime fecha;
    private String descripcion;
    private Double precio;

    public ProductoDTO() {
    }
}
