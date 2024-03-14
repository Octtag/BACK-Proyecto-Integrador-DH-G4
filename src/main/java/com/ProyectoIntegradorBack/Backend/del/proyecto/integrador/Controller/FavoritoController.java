package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Controller;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.ExcursionDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.FavoritoDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.*;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.ExcursionService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.FavoritoService;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Util.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {


    private final FavoritoService favoritoService;
    private final AwsService awsService;

    @Autowired
    private ExcursionRepository excursionRepository;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    public FavoritoController(FavoritoService favoritoService, AwsService awsService) {
        this.favoritoService = favoritoService;
        this.awsService = awsService;
    }


    @PostMapping("/crearFavorito")
    public ResponseEntity<Favorito> crearFavorito(@RequestBody FavoritoDTO favoritoDTO) throws IOException {
        Optional<Excursion> excursion = excursionRepository.findById(favoritoDTO.getIdExcursion());
        Optional<AppUser> user = appUserRepository.findByUsername(favoritoDTO.getUserName());
        Favorito favorito = new Favorito();

        if(excursion.isPresent() && user.isPresent()){
            favorito.setExcursion(excursion.orElse(new Excursion()));
            favorito.setAppUser(user.orElse(new AppUser()));

            favoritoService.crearFavorito(favorito);
        }
        return new ResponseEntity<>(favorito, HttpStatus.CREATED);
    }

    @DeleteMapping("/borrarFavorito")
    public ResponseEntity<Favorito> borrarFavorito(@RequestBody FavoritoDTO favoritoDTO) throws IOException {
        Optional<Excursion> excursion = excursionRepository.findById(favoritoDTO.getIdExcursion());
        Optional<AppUser> user = appUserRepository.findByUsername(favoritoDTO.getUserName());
        Favorito favorito = new Favorito();

        if(excursion.isPresent() && user.isPresent()){
            favorito = favoritoService.findByIdUserAndIdExcursion(user.get().getId(), excursion.get().getId()).get(0);
            favoritoService.borrarFavorito(favorito);
        }
        return new ResponseEntity<>(favorito, HttpStatus.CREATED);
    }
}
