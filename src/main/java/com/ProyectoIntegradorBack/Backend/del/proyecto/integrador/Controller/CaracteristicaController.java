package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.CaracteristicaService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/caracteristicas")
public class CaracteristicaController {

    @Autowired
    private CaracteristicaExcursionRepository caracteristicaExcursionRepository;
    private final CaracteristicaService caracteristicaService;
    @Autowired
    private ExcursionRepository excursionRepository;

    @Autowired
    private ImagenRepository imagenRepository;
    @Autowired
    private FavoritoRepository favoritoRepository;


    @Autowired
    public CaracteristicaController(CaracteristicaService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    @GetMapping("/obtenerCaracteristicas")
    public ResponseEntity<List<Caracteristica>> obtenerCaracteristicas() {
        List<Caracteristica> caracteristicas = caracteristicaService.findByOrderByIdAsc();
        return ResponseEntity.ok(caracteristicas);
    }

    @PostMapping("/crearCaracteristica")
    public ResponseEntity<Caracteristica> crearCaracteristica(@RequestBody Caracteristica caracteristica) throws IOException {
        Caracteristica resultado = caracteristicaService.guardarCaracteristica(caracteristica);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminarCaracteristica")
    public ResponseEntity<Caracteristica> eliminarCaracteristica(@RequestParam Long id) throws IOException {
        Caracteristica categoriaEncontrada = caracteristicaService.findById(id);
        List<CaracteristicaExcursion> caracteristicaExcursions = caracteristicaExcursionRepository.findAllByIdCaracteristica(id);
        for(CaracteristicaExcursion ce : caracteristicaExcursions){
            caracteristicaExcursionRepository.delete(ce);
        }

        caracteristicaService.borrarCategoria(categoriaEncontrada);
        return new ResponseEntity<>(categoriaEncontrada, HttpStatus.OK);
    }

}
