package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Caracteristica;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.CaracteristicaExcursion;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CaracteristicaExcursionRepository extends JpaRepository<CaracteristicaExcursion, Long> {

    @Modifying
    @Transactional
    @Query("SELECT ce FROM  CaracteristicaExcursion ce WHERE ce.caracteristica.id = :id")
    List<CaracteristicaExcursion> findAllByIdCaracteristica(Long id);
}

