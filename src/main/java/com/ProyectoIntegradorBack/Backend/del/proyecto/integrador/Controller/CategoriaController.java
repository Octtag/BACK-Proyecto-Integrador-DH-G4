package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.ExcursionDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.HotelDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.ImagenDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.VueloDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.CategoriaService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.ExcursionService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.HotelService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Util.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;
    private final CategoriaService categoriaService;
    @Autowired
    private ExcursionRepository excursionRepository;

    @Autowired
    private ImagenRepository imagenRepository;
    @Autowired
    private FavoritoRepository favoritoRepository;


    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/obtenerCategorias")
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        List<Categoria> categorias = categoriaRepository.findByOrderByIdAsc();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/crearCategoria")
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) throws IOException {
        Categoria resultado = categoriaService.guardarCategoria(categoria);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminarCategoria")
    public ResponseEntity<Categoria> eliminarCategoria(@RequestBody Categoria categoria) throws IOException {
        Categoria categoriaEncontrada = categoriaService.findById(categoria.getId());
        List<Excursion> excursiones = excursionRepository.findByCategoriaId(categoria.getId());
        for(Excursion e : excursiones){
            List<Imagen> imagenes = imagenRepository.findByExcursionId(e.getId());
            List<Favorito> favoritos = favoritoRepository.findByExcursionId(e.getId());
            for(Imagen i : imagenes){
                imagenRepository.delete(i);
            }
            for(Favorito f : favoritos){
                favoritoRepository.delete(f);
            }
            excursionRepository.delete(e);
        }

        categoriaService.borrarCategoria(categoriaEncontrada);
        return new ResponseEntity<>(categoriaEncontrada, HttpStatus.OK);
    }

}
