package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Favorito;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;

    @Autowired
    public FavoritoService(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    @Transactional
    public List<Favorito> findAllByIdUser(Long id) {
        return favoritoRepository.findAllByIdUser(id);
    }

    @Transactional
    public void crearFavorito(Favorito favorito) {
        favoritoRepository.save(favorito);
    }
    @Transactional
    public void borrarFavorito(Favorito favorito) {
        favoritoRepository.delete(favorito);
    }

    @Transactional
    public List<Favorito> findByIdUserAndIdExcursion(Long idUser, Long idExcursion) {
        return favoritoRepository.findTop1ByIdUserAndIdExcursion(idUser, idExcursion);
    }

}
