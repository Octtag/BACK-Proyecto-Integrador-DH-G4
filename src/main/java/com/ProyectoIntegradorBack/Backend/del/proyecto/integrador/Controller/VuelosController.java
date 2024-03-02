package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.VueloDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Imagen;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Vuelo;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.VueloService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Util.AwsService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Util.SaverToS3;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vuelos")
public class VuelosController {

    private final VueloService vueloService;
    private final SaverToS3 saverToS3;
    private final AwsService awsService;

    public VuelosController(VueloService vueloService, SaverToS3 saverToS3, AwsService awsService) {
        this.vueloService = vueloService;
        this.saverToS3 = saverToS3;
        this.awsService = awsService;
    }

    @PostMapping("/crearVuelo")
    public ResponseEntity<Vuelo> crearVuelo(@ModelAttribute VueloDTO vueloDTO) throws IOException {
        Vuelo nuevoVuelo = new Vuelo();
        // Assuming you have a method to map VueloDTO to Vuelo entity.
        vueloService.mapVueloDTOToVuelo(vueloDTO, nuevoVuelo);

        List<Imagen> imagenes = new ArrayList<>();
        for (MultipartFile file : vueloDTO.getImagenes()) {
            if (file != null && !file.isEmpty()) {
                String imageUrl = awsService.uploadEventImage(file);
                Imagen imagen = new Imagen();
                imagen.setUrl(imageUrl);
                // Assuming you set other necessary properties of Imagen here.
                imagenes.add(imagen);
            }
        }
        nuevoVuelo.setImagenesUrl(imagenes);
        Vuelo vueloGuardado = vueloService.guardarVuelo(nuevoVuelo);
        return new ResponseEntity<>(vueloGuardado, HttpStatus.CREATED);
    }

}
