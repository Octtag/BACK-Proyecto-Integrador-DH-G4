package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;
    private String password;

    public AuthenticationRequest(String s, String wrongpassword) {
        this.email = s;
        this.password = wrongpassword;
    }
}
