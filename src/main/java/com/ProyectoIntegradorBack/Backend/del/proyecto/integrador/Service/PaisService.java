package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Pais;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaisService {

    @Autowired
    PaisRepository paisRepository;

    public Optional<Pais> buscarPaisPorNombre(String nombre) {
        return paisRepository.findByNombrePaisIgnoreCase(nombre);
    }
}
