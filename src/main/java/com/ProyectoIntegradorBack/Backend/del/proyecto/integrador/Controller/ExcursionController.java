package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.CaracteristicaDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.CiudadDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.ExcursionDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.ImagenDTO;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/excursiones")
public class ExcursionController {
    private final ExcursionService excursionService;
    private final AwsService awsService;
    @Autowired
    private CaracteristicaRepository caracteristicaRepository;
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
    public ResponseEntity<String> crearExcursion(
            @RequestParam("nombre") String nombre,
            @RequestParam("categoria") Long categoria,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") double precio,
            @RequestParam("caracteristicas") List<String> caracteristicas,
            @RequestParam("destino") String destino,
            @RequestParam("fechaInicio") String fechaI,
            @RequestParam("fechaFin") String fechaF,
            @RequestParam("itinerario") String itinerario,
            @RequestParam("imagenes") MultipartFile[] imagenes) throws IOException {

        LocalDateTime fechaInicio = LocalDateTime.parse(fechaI);
        LocalDateTime fechaFin = LocalDateTime.parse(fechaF);

        Excursion resultado;
       try {
           Excursion nuevaExcursion = new Excursion();
           Excursion lastExcursion = excursionService.getLastExcursion();
           Categoria categoria1 = categoriaRepository.getById(categoria);

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
           nuevaExcursion.setCategoria(categoria1);
           nuevaExcursion.setDestino(destino);
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
               }
           }
           nuevaExcursion.setImagenes(listaImagenes);

           List<CaracteristicaExcursion> caracteristicaList = new ArrayList<>();
           for(String idC : caracteristicas){
               CaracteristicaExcursion caracteristicaExcursion = new CaracteristicaExcursion();
               caracteristicaExcursion.setExcursion(nuevaExcursion.getId());
               caracteristicaExcursion.setCaracteristica(caracteristicaRepository.getByTipo(idC));
               caracteristicaList.add(caracteristicaExcursion);
           }
           nuevaExcursion.setCaracteristicaExcursions(caracteristicaList);

           resultado = excursionService.guardarExcursion(nuevaExcursion);
       } catch (Exception e) {
           // Uso de ResponseEntity.badRequest() para devolver un mensaje de error y el stacktrace del error
           return ResponseEntity.badRequest().body("Error al crear la excursión: " + e.getMessage());
       }
        return ResponseEntity.ok("Excursión creada con exito");
    }

    @PutMapping("/actualizarExcursion")
    public ResponseEntity<String> actualizarExcursion(
            @RequestParam("id") Long idExcursion,
            @RequestParam("nombre") String nombre,
            @RequestParam("categoria") Long categoria,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") double precio,
            @RequestParam("caracteristicas") List<String> caracteristicas,
            @RequestParam("fechaInicio") String fechaI,
            @RequestParam("fechaFin") String fechaF,
            @RequestParam("itinerario") String itinerario,
            @RequestParam("imagenes") MultipartFile[] imagenes) throws IOException {

        LocalDateTime fechaInicio = LocalDateTime.parse(fechaI);
        LocalDateTime fechaFin = LocalDateTime.parse(fechaF);

        Excursion nuevaExcursion = excursionService.findById(idExcursion);
        try {
            Categoria categoria1 = categoriaRepository.getById(categoria);
            nuevaExcursion.setNombre(nombre);
            nuevaExcursion.setDescripcion(descripcion);
            nuevaExcursion.setPrecio(precio);
            nuevaExcursion.setCategoria(categoria1);
            nuevaExcursion.setFechaInicio(fechaInicio);
            nuevaExcursion.setFechaFin(fechaFin);
            nuevaExcursion.setItinerario(itinerario);

            List<Imagen> listaImagenes = nuevaExcursion.getImagenes();
            listaImagenes.clear();

            for (MultipartFile file : imagenes) {
                if (!file.isEmpty()) {
                    String imageUrl = awsService.uploadEventImage(file);
                    Imagen imagen = new Imagen();
                    imagen.setUrl(imageUrl);
                    listaImagenes.add(imagen);
                    imagen.setExcursion(nuevaExcursion);
                }
            }
            nuevaExcursion.setImagenes(listaImagenes);

            List<CaracteristicaExcursion> caracteristicasExistentes = nuevaExcursion.getCaracteristicaExcursions();
            caracteristicasExistentes.clear(); // Eliminar todas las caracteristicas existentes

            for(String idC : caracteristicas){
                CaracteristicaExcursion caracteristicaExcursion = new CaracteristicaExcursion();
                caracteristicaExcursion.setExcursion(nuevaExcursion.getId());
                caracteristicaExcursion.setCaracteristica(caracteristicaRepository.getByTipo(idC));
                caracteristicasExistentes.add(caracteristicaExcursion);
            }
            nuevaExcursion.setCaracteristicaExcursions(caracteristicasExistentes);

           excursionService.guardarExcursion(nuevaExcursion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al modificar la excursión: " + e.getMessage());
        }
        return ResponseEntity.ok("Excursión modifica con exito");
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

    @GetMapping("/obtenerExcursion")
    public ResponseEntity<ExcursionDTO> obtenerExcursion(@RequestParam(required = false) String userName, @RequestParam(required = false) Long id) {
        Excursion excursion = excursionService.findById(id);
        ExcursionDTO excursionDTO = new ExcursionDTO();
        List<CaracteristicaDTO> caracteristicas = new ArrayList<>();
        for (CaracteristicaExcursion ce : excursion.getCaracteristicaExcursions()) {
            CaracteristicaDTO c = new CaracteristicaDTO();
            c.setId(ce.getId());
            c.setTipo(ce.getCaracteristica().getTipo());
            c.setIcono(ce.getCaracteristica().getIcon().getIcono());
            caracteristicas.add(c);
        }
        excursionDTO.setCaracteristicas(caracteristicas);

        System.out.println("CARACTERISTICAS: "+excursionDTO);
        excursionDTO.setId(excursion.getId());
        excursionDTO.setNombre(excursion.getNombre());
        excursionDTO.setDescripcion(excursion.getDescripcion());
        excursionDTO.setDestino(excursion.getDestino());
        excursionDTO.setPrecio(excursion.getPrecio());
        excursionDTO.setFechaInicio(excursion.getFechaInicio());
        excursionDTO.setFechaFin(excursion.getFechaFin());
        excursionDTO.setItinerario(excursion.getItinerario());
        excursionDTO.setIdCategoria(excursion.getCategoria().getId());

        List<ImagenDTO> imagenes = new ArrayList<>();
        for (Imagen file : excursion.getImagenes()) {
            if (file != null) {
                ImagenDTO imagen = new ImagenDTO();
                imagen.setId(file.getId());
                imagen.setUrl(file.getUrl());
                imagenes.add(imagen);
            }
        }
        excursionDTO.setImagenes(imagenes);

        return ResponseEntity.ok(excursionDTO);
    }

    @GetMapping("/obtenerCiudades")
    public ResponseEntity<List<CiudadDTO>> obtenerCiudades() {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        List<CiudadDTO> ciudadesDTO = new ArrayList<>();
        for(Ciudad c : ciudades){
            CiudadDTO ciudadDTO = new CiudadDTO();
            ciudadDTO.setId(c.getId());
            ciudadDTO.setNombreCiudad(c.getNombreCiudad());
            ciudadDTO.setZipCode(c.getZipCode());
            ciudadDTO.setPais(c.getPais().getNombrePais());
            ciudadesDTO.add(ciudadDTO);
        }
        return ResponseEntity.ok(ciudadesDTO);
    }

}
