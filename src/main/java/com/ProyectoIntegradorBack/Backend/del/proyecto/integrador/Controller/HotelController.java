package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Hotel;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hoteles")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> crearHotel(@RequestBody Hotel hotel) {
        Hotel nuevoHotel = hotelService.guardarHotel(hotel);
        return new ResponseEntity<>(nuevoHotel, HttpStatus.CREATED);
    }

}
