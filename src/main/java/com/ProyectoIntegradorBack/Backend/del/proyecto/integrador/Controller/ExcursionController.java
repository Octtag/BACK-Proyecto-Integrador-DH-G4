package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.ExcursionDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.ExcursionService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Util.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private CompraRepository compraRepository;
    @Autowired
    public ExcursionController(ExcursionService excursionService, AwsService awsService) {
        this.excursionService = excursionService;
        this.awsService = awsService;
    }

    @PostMapping("/crearExcursion")
    public ResponseEntity<Excursion> crearExcursion(@RequestBody ExcursionDTO excursionDTO) throws IOException {
        Excursion nuevaExcursion = new Excursion();
        // Mapear ExcursionDTO a Excursion
        Excursion lastExcursion = excursionService.getLastExcursion();

        nuevaExcursion.setId(lastExcursion.getId() + 1);
        nuevaExcursion.setDescripcion(excursionDTO.getDescripcion());
        nuevaExcursion.setNombre(excursionDTO.getNombre());
        nuevaExcursion.setPrecio(excursionDTO.getPrecio());
        nuevaExcursion.setDestino(excursionDTO.getDestino());
        nuevaExcursion.setFechaFin(excursionDTO.getFechaFin());
        nuevaExcursion.setFechaInicio(excursionDTO.getFechaInicio());
        nuevaExcursion.setItinerario(excursionDTO.getItinerario());

        Ciudad ciudad = ciudadRepository.findTop1ByOrderByIdDesc();
        Pais pais = paisRepository.findTop1ByOrderByIdDesc();
        AppUser appUser = appUserRepository.findTop1ByOrderByIdDesc();
        Compra compra = compraRepository.findTop1ByOrderByIdDesc();

        nuevaExcursion.setCiudad(ciudad);
        nuevaExcursion.setCompra(compra);
        nuevaExcursion.setPais(pais);
        nuevaExcursion.setVendedor(appUser);

        List<Imagen> imagenes = new ArrayList<>();
      /*  for (Imagen file : excursionDTO.getImagenes()) {
            if (file != null ) {
                String imageUrl = awsService.uploadEventImage(file);
                Imagen imagen = new Imagen();
                imagen.setUrl(imageUrl);
                // Assuming you set other necessary properties of Imagen here.
                imagenes.add(imagen);
            }
        }*/
        nuevaExcursion.setImagenes(imagenes);

        // Suponiendo que la lógica para establecer los demás campos y relaciones se maneje en el servicio
        Excursion resultado = excursionService.guardarExcursion(nuevaExcursion);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }
}
