package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Vuelo;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vuelos")
public class VuelosController {

    @Autowired
    private VueloService vueloService;

    @PostMapping
    public ResponseEntity<Vuelo> crearVuelo(@RequestBody Vuelo vuelo) {
        Vuelo nuevoVuelo = vueloService.guardarVuelo(vuelo);
        return new ResponseEntity<>(nuevoVuelo, HttpStatus.CREATED);
    }

}
