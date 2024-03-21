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
    ExcursionRepository excursionRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

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
            @RequestParam("idCategoria") Long idCategoria,
            @RequestParam("imagenes") MultipartFile[] imagenes) throws IOException {

        Excursion resultado;
        try {
            System.out.println("Creando nueva excursión...");

            Excursion nuevaExcursion = new Excursion();
            Excursion lastExcursion = excursionService.getLastExcursion();
            Long id;
            if (lastExcursion != null) {
                id = lastExcursion.getId() + 1L;
            } else {
                id = 1L;
            }
            System.out.println("ID de la nueva excursión: " + id);

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

            if (idCategoria != null){
                Categoria categoria = categoriaRepository.findById(idCategoria).get();
                nuevaExcursion.setCategoria(categoria);
            }

            // Lo guardamos antes para que se le pueda asignar a las imágenes
            System.out.println("Guardando nueva excursión en la base de datos...");
            excursionRepository.save(nuevaExcursion);

            List<Imagen> listaImagenes = new ArrayList<>();
            for (MultipartFile file : imagenes) {
                if (!file.isEmpty()) {
                    System.out.println("Subiendo imagen al servicio AWS...");
                    String imageUrl = awsService.uploadEventImage(file);
                    System.out.println("URL de la imagen subida: " + imageUrl);

                    Imagen imagen = new Imagen();
                    imagen.setUrl(imageUrl);
                    listaImagenes.add(imagen);
                    imagen.setExcursion(nuevaExcursion);

                    System.out.println("Guardando imagen en la base de datos...");
                    imagenRepository.save(imagen);
                }
            }
            nuevaExcursion.setImagenes(listaImagenes);

            System.out.println("Guardando la excursión completa en la base de datos...");
            resultado = excursionService.guardarExcursion(nuevaExcursion);

            System.out.println("Excursión creada exitosamente.");
        } catch (Exception e) {
            // Uso de ResponseEntity.badRequest() para devolver un mensaje de error y el stacktrace del error
            System.out.println("Error al crear la excursión: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error al crear la excursión: " + e.getMessage());
        }
        return ResponseEntity.ok().body(resultado);
    }


    /*
    @PutMapping("/agregarAFavoritos")
    public ResponseEntity<Excursion> agregarAFavoritos(@RequestBody ExcursionDTO excursionDTO) throws IOException {
        Excursion excursion = excursionService.findById( Long.valueOf(excursionDTO.getId().intValue()));
       // excursion.setEsFavorito(excursionDTO.getEsFavorito());

        excursionService.actualizarExcursion(nuevaExcursion);
        return new ResponseEntity<>(excursion, HttpStatus.CREATED);
    }
    */
}
