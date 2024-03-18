package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Caracteristica;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Categoria;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.CaracteristicaRepository;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CaracteristicaService {

    private final CaracteristicaRepository caracteristicaRepository;

    @Autowired
    public CaracteristicaService(CaracteristicaRepository caracteristicaRepository) {
        this.caracteristicaRepository = caracteristicaRepository;
    }

    @Transactional
    public Caracteristica guardarCaracteristica(Caracteristica caracteristica) {
        return caracteristicaRepository.save(caracteristica);
    }

    @Transactional
    public void borrarCategoria(Caracteristica caracteristica) {
        caracteristicaRepository.delete(caracteristica);
    }

    public Caracteristica findById(Long id) {
        return caracteristicaRepository.findById(id).get();
    }

    public List<Caracteristica> findByOrderByIdAsc() {
        return caracteristicaRepository.findByOrderByIdAsc();
    }



}
