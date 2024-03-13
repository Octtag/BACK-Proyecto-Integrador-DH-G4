package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    List<Excursion> findByOrderByIdAsc();

    Excursion findTop1ByOrderByIdDesc();

    @Modifying
    @Transactional
    @Query("UPDATE Excursion u SET u.esFavorito = :esFavorito WHERE u.id = :id")
    void update(Long id, Boolean esFavorito);

}

