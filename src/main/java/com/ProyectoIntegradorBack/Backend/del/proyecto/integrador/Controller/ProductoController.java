package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.ExcursionDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.HotelDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.ImagenDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.VueloDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Excursion;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Hotel;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Imagen;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Vuelo;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.ExcursionRepository;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.HotelRepository;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private VueloRepository vueloRepository;

    @Autowired
    private ExcursionRepository excursionRepository;

    @GetMapping("/aleatorios")
    public ResponseEntity<Map<String, List<?>>> obtenerProductosAleatorios() {
        List<Excursion> excursionesAleatorias = excursionRepository.findByOrderByIdAsc();
        List<ExcursionDTO> excursionesDTO = excursionesAleatorias.stream()
                .map(excursion -> {
                    ExcursionDTO h = new ExcursionDTO();
                    h.setId(excursion.getId());
                    h.setNombre(excursion.getNombre());
                    h.setDescripcion(excursion.getDescripcion());
                    h.setDestino(excursion.getDestino());
                    h.setPrecio(excursion.getPrecio());
                    h.setFechaInicio(excursion.getFechaInicio());
                    h.setFechaFin(excursion.getFechaFin());
                    h.setItinerario(excursion.getItinerario());
                    List<ImagenDTO> imagenes= getImages(excursion.getImagenes());
                    h.setImagenes(imagenes);

                    return h;
                }) // Crear DTOs a partir de las entidades
                .collect(Collectors.toList());

        Map<String, List<?>> respuesta = new HashMap<>();
        respuesta.put("excursiones", excursionesDTO);

        return ResponseEntity.ok(respuesta);
    }

    private List<ImagenDTO> getImages(List<Imagen> imagenes){
        List<ImagenDTO> imagenes2 = new ArrayList<>();
        for (Imagen file : imagenes) {
            if (file != null) {
                ImagenDTO imagen = new ImagenDTO();
                imagen.setId(file.getId());
                imagen.setUrl(file.getUrl());
                imagenes2.add(imagen);
            }
        }
        return imagenes2;
    }
}
