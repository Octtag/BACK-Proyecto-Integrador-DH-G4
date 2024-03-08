package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Jwt.JwtUtil;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Security.PasswordEncoder;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/usuarios")
public class AppUserController {

    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AppUserController(AppUserService appUserService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registrarUsuario")
    public ResponseEntity<?> registrarUsuario(@RequestBody AppUserDto appUserDto) throws Exception {

        System.out.println(appUserDto);

        if (appUserService.existsByEmail(appUserDto.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Este email ya está en uso, inicia sesión con tu cuenta");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        AppUser usuario = new AppUser();
        usuario.setUsername(appUserDto.getEmail());
        usuario.setEmail(appUserDto.getEmail());
        usuario.setNombre(appUserDto.getNombre());
        usuario.setApellido(appUserDto.getApellido());
        usuario.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        usuario.setRol(AppUserRol.USER);


        appUserService.guardarAppUser(usuario);
        return ResponseEntity.ok("to bien");
    }

    @PostMapping("/registrarAdmin")
    public ResponseEntity<?> registrarAdmin(@RequestBody AppUserDto appUserDto) throws Exception {

        System.out.println(appUserDto);

        if (appUserService.existsByEmail(appUserDto.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Este email ya está en uso, inicia sesión con tu cuenta");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        AppUser usuario = new AppUser();
        usuario.setUsername(appUserDto.getEmail());
        usuario.setEmail(appUserDto.getEmail());
        usuario.setNombre(appUserDto.getNombre());
        usuario.setApellido(appUserDto.getApellido());
        usuario.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        usuario.setRol(AppUserRol.ADMIN);


        appUserService.guardarAppUser(usuario);
        return ResponseEntity.ok("to bien");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loguinYCrearToken(@RequestBody AuthenticationRequest authenticationRequest) {
        String rol;
        String nombreCompleto;
        try {
            AppUser appUser = appUserService.findByEmail(authenticationRequest.getEmail()).get();
            rol = String.valueOf(appUser.getRol());
            nombreCompleto =  appUser.getNombre() + ' ' + appUser.getApellido();

            if (appUser == null){
                // En lugar de lanzar una excepción, devuelve una respuesta con un código de estado 401 y un mensaje claro.
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe un usuario con ese email");
            }
        } catch (BadCredentialsException e) {
            // En lugar de lanzar una excepción, devuelve una respuesta con un código de estado 401 y un mensaje claro.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña incorrecta");
        }

        final String jwt = jwtUtil.createToken(authenticationRequest.getEmail());
        System.out.println(jwt);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, rol, nombreCompleto));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<AppUserDto>> obtenerUsuarios() {
        List<AppUser> usuarios = appUserService.getAllUsers();
        List<AppUserDto> usuariosDTO = usuarios.stream()
                .map(usuario -> {
                    AppUserDto h = new AppUserDto();
                    h.setId(usuario.getId());
                    h.setNombre(usuario.getNombre());
                    h.setApellido(usuario.getApellido());
                    h.setEmail(usuario.getEmail());
                    h.setRol(String.valueOf(usuario.getRol()));
                    return h;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuariosDTO);
    }

    @PostMapping("/udpate")
    public ResponseEntity<AppUser> actualizarUsuario(@RequestBody AppUserDto user) throws IOException {
        AppUser usuario = appUserService.findById(user.getId());

        usuario.setRol(AppUserRol.valueOf(user.getRol()));
        AppUser u=appUserService.updateUser(usuario);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

}
