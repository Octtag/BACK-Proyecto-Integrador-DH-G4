package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    List<Excursion> findByOrderByIdAsc();

    Excursion findTop1ByOrderByIdDesc();
}

