/*package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller.HotelController;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.HotelDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Hotel;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Jwt.JwtUtil;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HotelService hotelService;
    @MockBean
    JwtUtil jwtUtil;

    @Test
    public void crearHotelTest() throws Exception {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setNombre("Hotel Test");
        hotelDTO.setDescripcion("Descripción del Hotel Test");
        hotelDTO.setPrecio(150.00);
        hotelDTO.setNombreHotel("Nombre del Hotel");
        hotelDTO.setNit("NIT123");
        hotelDTO.setDireccion("Dirección Test");
        hotelDTO.setTelefono("123456789");
        hotelDTO.setCategoria("5 estrellas");
        // Suponiendo que tienes un DTO y un mapeo a la entidad Hotel

        Hotel hotel = new Hotel(); // Simula la respuesta esperada tras guardar el hotel

        Mockito.when(hotelService.guardarHotel(any(Hotel.class))).thenReturn(hotel);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/hotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(hotel)));

        Mockito.verify(hotelService).guardarHotel(any(Hotel.class));
    }

}

 */

