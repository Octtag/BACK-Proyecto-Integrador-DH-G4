package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.AppUser;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Categoria;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Excursion;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.ExcursionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ExcursionService {

    private final ExcursionRepository excursionRepository;

    @Autowired
    public ExcursionService(ExcursionRepository excursionRepository) {
        this.excursionRepository = excursionRepository;
    }

    @Transactional
    public Excursion guardarExcursion(Excursion excursion) {
        return excursionRepository.save(excursion);
    }

    @Transactional
    public Excursion getLastExcursion() {
        return excursionRepository.findTop1ByOrderByIdDesc();
    }

    public Excursion findById(Long id) {
        return excursionRepository.findById(id).get();
    }

    public Optional<Excursion> findByNombre(String nombre) {
        return excursionRepository.findByNombre(nombre);
    }


    @Transactional
    public void eliminarExcursion(Excursion excurcion) {
        excursionRepository.delete(excurcion);
    }
}
