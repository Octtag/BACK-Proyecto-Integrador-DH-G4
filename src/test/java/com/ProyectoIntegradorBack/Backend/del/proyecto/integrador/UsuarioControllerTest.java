/*package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.AppUserDto;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.AuthenticationRequest;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.AppUser;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Jwt.JwtUtil;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppUserService appUserService;
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    public void registrarUsuarioTest() throws Exception {
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setEmail("diego@digital.com");
        appUserDto.setPassword("password");
        appUserDto.setNombre("emerejildo");
        appUserDto.setApellido("zapata");

        Mockito.when(appUserService.existsByEmail(anyString())).thenReturn(false);

        mockMvc.perform(post("/api/usuarios/registrarUsuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(appUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("to bien"));

        Mockito.verify(appUserService, Mockito.times(1)).guardarAppUser(any(AppUser.class));
    }

    @Test
    public void registrarAdminTest() throws Exception {
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setEmail("admin@example.com");
        appUserDto.setPassword("password");
        appUserDto.setNombre("AdminNombre");
        appUserDto.setApellido("AdminApellido");

        Mockito.when(appUserService.existsByEmail(anyString())).thenReturn(false);

        mockMvc.perform(post("/api/usuarios/registrarAdmin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(appUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("to bien"));

        Mockito.verify(appUserService, Mockito.times(1)).guardarAppUser(any(AppUser.class));
    }

    @Test
    public void loginFailureTest() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("user@example.com", "wrongpassword");

        Mockito.when(appUserService.findByEmail(anyString())).thenReturn(Optional.of(new AppUser()));
        Mockito.doThrow(new BadCredentialsException("Email o contraseña incorrecta")).when(authenticationManager).authenticate(any());

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Email o contraseña incorrecta"));
    }

}

 */

