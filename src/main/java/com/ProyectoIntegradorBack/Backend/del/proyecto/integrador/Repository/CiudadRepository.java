package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CiudadRepository extends JpaRepository<Ciudad,Long>{
    Ciudad findTop1ByOrderByIdDesc();

    Optional<Ciudad> findByNombreCiudadIgnoreCase(String nombreCiudad);
}
