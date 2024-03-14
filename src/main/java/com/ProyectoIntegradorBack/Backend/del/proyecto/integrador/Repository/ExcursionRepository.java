package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.AppUser;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    List<Excursion> findByOrderByIdAsc();

    Excursion findTop1ByOrderByIdDesc();

    Optional<Excursion> findByNombre(String nombre);

}

