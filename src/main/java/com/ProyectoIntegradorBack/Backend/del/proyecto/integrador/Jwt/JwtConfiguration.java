package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256("${JWT_SECRET_KEY}");
    }
}
