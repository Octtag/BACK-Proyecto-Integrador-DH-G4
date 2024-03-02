package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Excursion;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Hotel;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Vuelo;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.ExcursionRepository;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.HotelRepository;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Hotel> hotelesAleatorios = hotelRepository.findTop4ByOrderByIdDesc();
        List<Vuelo> vuelosAleatorios = vueloRepository.findTop3ByOrderByIdDesc();
        List<Excursion> excursionesAleatorias = excursionRepository.findTop3ByOrderByIdDesc();

        Map<String, List<?>> respuesta = new HashMap<>();
        respuesta.put("hoteles", hotelesAleatorios);
        respuesta.put("vuelos", vuelosAleatorios);
        respuesta.put("excursiones", excursionesAleatorias);

        return ResponseEntity.ok(respuesta);
    }
}
