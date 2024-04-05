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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final AwsService awsService;
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
    public CategoriaController(CategoriaService categoriaService, AwsService awsService) {
        this.categoriaService = categoriaService;
        this.awsService = awsService;
    }

    @GetMapping("/obtenerCategorias")
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        List<Categoria> categorias = categoriaRepository.findByOrderByIdAsc();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/crearImagenes")
    public ResponseEntity<String> crearImagenes(@RequestParam("files") MultipartFile[] files) throws IOException {
        System.out.println("se reciben imagenesssssss"+files);
        List<Imagen> listaImagenes = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String imageUrl = awsService.uploadEventImage(file);
                Imagen imagen = new Imagen();
                System.out.println("url"+imageUrl);

                imagen.setUrl(imageUrl);
                listaImagenes.add(imagen);
            }
        }
        return new ResponseEntity<>("La categoría se creo exitosamente", HttpStatus.CREATED);
    }
    @PostMapping("/crearCategoria")
    public ResponseEntity<String> crearCategoria(@RequestParam("nombreCategoria") String nombreCategoria,
                                                 @RequestParam("descripcionCategoria") String descripcionCategoria,
                                                 @RequestParam("file") MultipartFile file) throws IOException {
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(nombreCategoria);
        categoria.setDescripcionCategoria(descripcionCategoria);
        if (!file.isEmpty()) {
            String imageUrl = awsService.uploadEventImage(file);
            categoria.setImageURL(imageUrl);
        }
        categoriaService.guardarCategoria(categoria);
        return new ResponseEntity<>("La categoría se creo exitosamente", HttpStatus.CREATED);
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
