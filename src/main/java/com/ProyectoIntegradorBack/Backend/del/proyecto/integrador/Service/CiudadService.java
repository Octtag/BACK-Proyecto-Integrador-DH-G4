package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Ciudad;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CiudadService {

    @Autowired
    CiudadRepository ciudadRepository;

    public Optional<Ciudad> buscarCiudadPorNombre(String nombre) {
        return ciudadRepository.findByNombreCiudadIgnoreCase(nombre);
    }

}
