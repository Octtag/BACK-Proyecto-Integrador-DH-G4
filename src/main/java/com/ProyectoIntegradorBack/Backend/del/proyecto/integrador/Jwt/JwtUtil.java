package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Jwt;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.AppUser;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.AppUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private final AppUserService simpleUserService;

    private String SECRET_KEY = "atilio";
    private Algorithm ALGORITHM;

    @Autowired
    public JwtUtil(AppUserService simpleUserService) {
        this.simpleUserService = simpleUserService;
    }

    @PostConstruct
    public void init() {
        ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
    }

    public String createToken(String email){
        System.out.println("Creando el token");
        return JWT.create()
                .withSubject(email)
                .withIssuer("simple-secret")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2)))
                .sign(ALGORITHM);
    }

    public String createTokenAccessTokenProductor(String texto){
        System.out.println("Creando el token");
        return JWT.create()
                .withSubject(texto)
                .withIssuer("simple-secret")
                .sign(ALGORITHM);
    }

    public String getAccessToken(String jwt){
        return JWT.require(ALGORITHM)
                .build()
                .verify(jwt)
                .getSubject();
    }


    public String crearTokenValido10min(String email){
        System.out.println("Creando el token");
        return JWT.create()
                .withSubject(email)
                .withIssuer("simple-secret")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
                .sign(ALGORITHM);
    }

    public Optional<AppUser> obtenerUsuarioDesdeToken(String authorizationHeader){
        String jwt = authorizationHeader.substring(7);
        String username = getEmail(jwt);

        Optional<AppUser> simpleUser;
        simpleUser = simpleUserService.findByUsername(username);
        if (simpleUser == null){
            System.out.println("no se encontro ningun usuario con ese email");
        }
        return simpleUser;
    }

    public boolean esValido(String jwt){
        try{
            JWT.require(ALGORITHM)
                    .build()
                    .verify(jwt);
            return true;
        } catch (JWTVerificationException j){
            return false;
        }
    }

    public String getEmail(String jwt) {
        try {
            if (jwt == null || jwt.trim().isEmpty()) {
                // Loguear o manejar la ausencia de token si es necesario
                return null;
            }

            return JWT.require(ALGORITHM)
                    .build()
                    .verify(jwt)
                    .getSubject();
        } catch (JWTVerificationException e) {
            // Manejar la excepción aquí (loguearla si es necesario, pero no lanzarla)
            return null;
        }
    }

    public String createTokenCarrito(String carrito_id){
        System.out.println("Creando el token");
        return JWT.create()
                .withSubject(carrito_id)
                .withIssuer("simple-secret")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2)))
                .sign(ALGORITHM);
    }

    public String getCarrito(String jwt){
        return JWT.require(ALGORITHM)
                .build()
                .verify(jwt)
                .getSubject();
    }
}