/*package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller.ExcursionController;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.ExcursionDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Excursion;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Jwt.JwtUtil;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.AppUserService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.ExcursionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ExcursionController.class)
public class ExcursionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExcursionService excursionService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private AppUserService appUserService;


    @Test
    public void crearExcursionTest() throws Exception {
        // Propiedades para ExcursionDTO
        ExcursionDTO excursionDTO = new ExcursionDTO();
        excursionDTO.setNombre("Excursión al Everest");
        excursionDTO.setDescripcion("Una aventura única al Everest");
        excursionDTO.setPrecio(999.99);
        excursionDTO.setDestino("Everest");
        excursionDTO.setFechaInicio(LocalDateTime.of(2023, 10, 15, 8, 0));
        excursionDTO.setFechaFin(LocalDateTime.of(2023, 10, 25, 20, 0));
        excursionDTO.setItinerario("Día 1: Llegada - Día 10: Salida");

        Excursion excursion = new Excursion();

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()); // Registra módulo para LocalDateTime

        Mockito.when(excursionService.guardarExcursion(any(Excursion.class))).thenReturn(excursion);

        mockMvc.perform(post("/api/excursiones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(excursionDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(excursion)));

        Mockito.verify(excursionService).guardarExcursion(any(Excursion.class));
    }
}

 */

