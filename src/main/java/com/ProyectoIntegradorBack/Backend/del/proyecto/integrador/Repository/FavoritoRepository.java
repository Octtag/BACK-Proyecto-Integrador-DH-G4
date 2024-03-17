package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Excursion;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Favorito;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    @Modifying
    @Transactional
    @Query("SELECT f FROM Favorito f WHERE f.appUser.id = :id")
    List<Favorito> findAllByIdUser(Long id);


    @Modifying
    @Transactional
    @Query("SELECT f FROM Favorito f WHERE f.appUser.id = :idUser AND f.excursion.id = :idExcursion")
    List<Favorito> findTop1ByIdUserAndIdExcursion(Long idUser, Long idExcursion);


    List<Favorito> findByExcursionId(Long excursionId);

}

