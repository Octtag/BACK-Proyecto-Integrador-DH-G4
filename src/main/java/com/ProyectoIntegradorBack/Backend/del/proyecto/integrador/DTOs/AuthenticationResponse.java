package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

public class AuthenticationResponse {

    private final String jwt;
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}
