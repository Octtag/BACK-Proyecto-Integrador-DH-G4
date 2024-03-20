package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
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

    @Autowired
    private FavoritoRepository favoritoRepository;


    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/aleatorios")
    public ResponseEntity<Map<String, List<?>>> obtenerProductosAleatorios(@RequestParam(required = false) String userName, @RequestParam(required = false) Boolean isFavorite) {

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
                    h.setIdCategoria(excursion.getCategoria().getId());
                    List<CaracteristicaDTO> caracteristicaDTOS = new ArrayList<>();
                    for(CaracteristicaExcursion ce : excursion.getCaracteristicaExcursions()){
                        CaracteristicaDTO caracteristicaDTO = new CaracteristicaDTO();
                        caracteristicaDTO.setIcono(ce.getCaracteristica().getIcon().getIcono());
                        caracteristicaDTO.setTipo(ce.getCaracteristica().getTipo());
                        caracteristicaDTO.setId(ce.getCaracteristica().getId());
                        caracteristicaDTOS.add(caracteristicaDTO);
                    }
                    h.setCaracteristicas(caracteristicaDTOS);

                    List<ImagenDTO> imagenes= getImages(excursion.getImagenes());
                    h.setImagenes(imagenes);

                    return h;
                }) // Crear DTOs a partir de las entidades
                .collect(Collectors.toList());

        if(userName != null){
            Optional<AppUser> user = appUserRepository.findByUsername(userName);
            List<Favorito> favoritos = favoritoRepository.findAllByIdUser(user.get().getId());

            for(ExcursionDTO excursionDTO: excursionesDTO){
                Optional<Favorito> favorito = favoritos.stream().filter(i -> i.getExcursion().getId().equals(excursionDTO.getId())).findFirst();
                if(favorito.isPresent()){
                    excursionDTO.setEsFavorito(true);
                }else{
                    excursionDTO.setEsFavorito(false);
                }
            }

            if(isFavorite != null){
                excursionesDTO = excursionesDTO.stream().filter(i -> i.getEsFavorito()).toList();
            }
        }

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
