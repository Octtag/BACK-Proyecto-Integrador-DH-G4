package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import lombok.Data;

@Data
public class AppUserDto {

    private String nombre;
    private String apellido;
    private String email;
    private String password;
}
