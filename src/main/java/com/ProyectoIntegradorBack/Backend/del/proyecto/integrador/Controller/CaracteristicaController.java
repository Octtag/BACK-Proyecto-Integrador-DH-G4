package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.CaracteristicaDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.CaracteristicaService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/caracteristicas")
public class CaracteristicaController {

    @Autowired
    private CaracteristicaExcursionRepository caracteristicaExcursionRepository;
    @Autowired
    private IconRepository iconRepository;
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
    public ResponseEntity<List<CaracteristicaDTO>> obtenerCaracteristicas() {
        List<Caracteristica> caracteristicas = caracteristicaService.findByOrderByIdAsc();
        List<CaracteristicaDTO> caracteristicasDTO = new ArrayList<>();
        for(Caracteristica c : caracteristicas){
            CaracteristicaDTO caracteristicaDTO = new CaracteristicaDTO();
            caracteristicaDTO.setId(c.getId());
            caracteristicaDTO.setTipo(c.getTipo());
            caracteristicaDTO.setIcono(c.getIcon().getIcono());

            caracteristicasDTO.add(caracteristicaDTO);
        }
        System.out.println("DTO"+caracteristicasDTO);
        return ResponseEntity.ok(caracteristicasDTO);
    }
    @GetMapping("/obtenerIconos")
    public ResponseEntity<List<Icon>> obtenerIconos() {
        List<Icon> iconos = iconRepository.findByOrderByIdAsc();
        return ResponseEntity.ok(iconos);
    }

    @PostMapping("/crearCaracteristica")
    public ResponseEntity<String> crearCaracteristica(@RequestParam Long idIcon,@RequestParam String nombre) throws IOException {
        Caracteristica caracteristica1 = new Caracteristica();
        caracteristica1.setTipo(nombre);
        Icon icon = iconRepository.getById(idIcon);
        caracteristica1.setIcon(icon);
        caracteristicaService.guardarCaracteristica(caracteristica1);

        return new ResponseEntity<>("Se creo la característica correctamente", HttpStatus.CREATED);
    }

    @PatchMapping("/actualizarCaracteristica")
    public ResponseEntity<String> actualirzaCaracteristica(@RequestBody CaracteristicaDTO caracteristica) throws IOException {
        Caracteristica caracteristica1 = caracteristicaService.findById(caracteristica.getId());
        caracteristica1.setTipo(caracteristica.getTipo());
        Icon icon = iconRepository.getById(Long.valueOf(caracteristica.getIcono()));
        caracteristica1.setIcon(icon);
        caracteristicaService.guardarCaracteristica(caracteristica1);

        return new ResponseEntity<>("Se actualizo la característica correctamente", HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminarCaracteristica")
    public ResponseEntity<String> eliminarCaracteristica(@RequestParam Long id) throws IOException {
        Caracteristica categoriaEncontrada = caracteristicaService.findById(id);
        List<CaracteristicaExcursion> caracteristicaExcursions = caracteristicaExcursionRepository.findAllByIdCaracteristica(id);
        for(CaracteristicaExcursion ce : caracteristicaExcursions){
            caracteristicaExcursionRepository.delete(ce);
        }

        caracteristicaService.borrarCategoria(categoriaEncontrada);
        return new ResponseEntity<>("La característica se elimino correctamente", HttpStatus.OK);
    }

}
