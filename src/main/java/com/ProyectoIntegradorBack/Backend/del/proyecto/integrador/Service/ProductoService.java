package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.ExcursionRepository;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.HotelRepository;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ExcursionRepository excursionRepository;

    @Autowired
    private VueloRepository vueloRepository;

}
