package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.HotelDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Hotel;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Imagen;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.HotelService;
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
@RequestMapping("/api/hoteles")
public class HotelController {

    private final HotelService hotelService;
    private final AwsService awsService;

    @Autowired
    public HotelController(HotelService hotelService, AwsService awsService) {
        this.hotelService = hotelService;
        this.awsService = awsService;
    }

    @PostMapping
    public ResponseEntity<Hotel> crearHotel(@RequestBody HotelDTO hotelDTO) throws IOException {
        Hotel nuevoHotel = new Hotel();
        // Mapea las propiedades de HotelDTO a Hotel
        nuevoHotel.setNombreHotel(hotelDTO.getNombreHotel());
        nuevoHotel.setNit(hotelDTO.getNit());
        nuevoHotel.setDireccion(hotelDTO.getDireccion());
        nuevoHotel.setTelefono(hotelDTO.getTelefono());
        nuevoHotel.setCategoria(hotelDTO.getCategoria());
        nuevoHotel.setUrlWeb(hotelDTO.getUrlWeb());

        List<Imagen> imagenes = new ArrayList<>();
        /*for (MultipartFile file : hotelDTO.getImagenes()) {
            if (file != null && !file.isEmpty()) {
                String imageUrl = awsService.uploadEventImage(file);
                Imagen imagen = new Imagen();
                imagen.setUrl(imageUrl);
                // Assuming you set other necessary properties of Imagen here.
                imagenes.add(imagen);
            }
        }*/
        nuevoHotel.setImagenes(imagenes);

        // Suponiendo que la lógica para establecer los demás campos y relaciones se maneje en el servicio
        Hotel resultado = hotelService.guardarHotel(nuevoHotel);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }
}
