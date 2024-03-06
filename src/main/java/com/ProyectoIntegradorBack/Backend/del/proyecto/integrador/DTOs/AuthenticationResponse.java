package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private final String jwt;
    private final String rol;
    private final String nombreCompleto;
    public AuthenticationResponse(String jwt, String rol, String nombreCompleto) {
        this.jwt = jwt;
        this.rol = rol;
        this.nombreCompleto =  nombreCompleto;
    }

    public String getJwt() {
        return jwt;
    }

}
