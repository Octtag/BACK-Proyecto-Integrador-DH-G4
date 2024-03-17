package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.ExcursionDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.CiudadService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.ExcursionService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.PaisService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Util.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/excursiones")
public class ExcursionController {


    private final ExcursionService excursionService;
    private final AwsService awsService;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    ImagenRepository imagenRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    public ExcursionController(ExcursionService excursionService, AwsService awsService) {
        this.excursionService = excursionService;
        this.awsService = awsService;
    }

    @PostMapping("/crearExcursion")
    public ResponseEntity<?> crearExcursion(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") double precio,
            @RequestParam("destino") String destino,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam("itinerario") String itinerario,
            @RequestParam("imagenes") MultipartFile[] imagenes) throws IOException {

        Excursion resultado;
       try {
           Excursion nuevaExcursion = new Excursion();
           Excursion lastExcursion = excursionService.getLastExcursion();
           Long id;
           if (lastExcursion != null) {
               id = lastExcursion.getId() + 1L;
           } else {
               id = 1L;
           }
           nuevaExcursion.setId(id);
           nuevaExcursion.setNombre(nombre);
           nuevaExcursion.setDescripcion(descripcion);
           nuevaExcursion.setPrecio(precio);
           if (destino != null) {
               nuevaExcursion.setDestino(destino);
           }
           nuevaExcursion.setFechaInicio(fechaInicio);
           nuevaExcursion.setFechaFin(fechaFin);
           nuevaExcursion.setItinerario(itinerario);

           List<Imagen> listaImagenes = new ArrayList<>();
           for (MultipartFile file : imagenes) {
               if (!file.isEmpty()) {
                   String imageUrl = awsService.uploadEventImage(file);
                   Imagen imagen = new Imagen();
                   imagen.setUrl(imageUrl);
                   listaImagenes.add(imagen);
                   imagen.setExcursion(nuevaExcursion);
                   imagenRepository.save(imagen);
               }
           }
           nuevaExcursion.setImagenes(listaImagenes);

           resultado = excursionService.guardarExcursion(nuevaExcursion);
       } catch (Exception e) {
           // Uso de ResponseEntity.badRequest() para devolver un mensaje de error y el stacktrace del error
           return ResponseEntity.badRequest().body("Error al crear la excursión: " + e.getMessage());
       }
        return ResponseEntity.ok().body(resultado);
    }


    @DeleteMapping("/eliminarExcursion")
    public ResponseEntity<String> eliminarExcursion(@RequestParam Long excursionId) throws IOException {
        Excursion excursion = excursionService.findById(excursionId);
        List<Imagen> imagenes = imagenRepository.findByExcursionId(excursionId);
        List<Favorito> favoritos = favoritoRepository.findByExcursionId(excursionId);
        for(Imagen i : imagenes){
            imagenRepository.delete(i);
        }
        for(Favorito f : favoritos){
            favoritoRepository.delete(f);
        }
        excursionService.eliminarExcursion(excursion);
        return new ResponseEntity<>("La excursión, imagenes y favoritos relacionados, se borraron con exito", HttpStatus.OK);
    }

    @PatchMapping("/actualizarCategoriaExcursion")
    public ResponseEntity<String> actualizarCategoriaExcursion(
            @RequestParam("id") Long id,
            @RequestParam("idCategoria") Long idCategoria) throws IOException {
        try {
            Excursion excursion = excursionService.findById(id);
            Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
            if(categoria.isPresent()) {
                excursion.setCategoria(categoria.orElse(new Categoria()));
            }
            excursionService.guardarExcursion(excursion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al editar la excursión: " + e.getMessage());
        }
        return ResponseEntity.ok().body("La categoría de la excursion solicitada, se modifico correctamente");
    }

}
