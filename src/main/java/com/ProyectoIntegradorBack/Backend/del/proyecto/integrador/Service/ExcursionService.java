package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Excursion;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.ExcursionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExcursionService {

    private final ExcursionRepository excursionRepository;

    public ExcursionService(ExcursionRepository excursionRepository) {
        this.excursionRepository = excursionRepository;
    }

    @Transactional
    public Excursion guardarExcursion(Excursion excursion) {
        return excursionRepository.save(excursion);
    }
}
