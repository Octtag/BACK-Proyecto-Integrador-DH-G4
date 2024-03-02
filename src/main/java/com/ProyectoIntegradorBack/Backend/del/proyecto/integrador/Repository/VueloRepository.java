package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    List<Vuelo> findTop3ByOrderByIdDesc();
}
