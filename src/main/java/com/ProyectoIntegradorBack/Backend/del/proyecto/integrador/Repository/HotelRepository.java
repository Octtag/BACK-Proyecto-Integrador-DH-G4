package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
