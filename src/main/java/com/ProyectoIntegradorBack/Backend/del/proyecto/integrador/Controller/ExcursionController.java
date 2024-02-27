package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Excursion;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.ExcursionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/excursiones")
public class ExcursionController {

    @Autowired
    private ExcursionService excursionService;

    @PostMapping
    public ResponseEntity<Excursion> crearExcursion(@RequestBody Excursion excursion) {
        Excursion nuevaExcursion = excursionService.guardarExcursion(excursion);
        return new ResponseEntity<>(nuevaExcursion, HttpStatus.CREATED);
    }
}
