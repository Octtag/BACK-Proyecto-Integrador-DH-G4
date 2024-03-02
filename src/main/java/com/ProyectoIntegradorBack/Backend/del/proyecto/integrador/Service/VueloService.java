package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs.VueloDTO;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.AppUser;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Ciudad;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Pais;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Vuelo;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.CiudadRepository;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.PaisRepository;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VueloService {

    private final VueloRepository vueloRepository;
    private final CiudadRepository ciudadRepository;
    private final PaisRepository paisRepository;
    private final AppUserService appUserService;

    @Autowired
    public VueloService(VueloRepository vueloRepository, CiudadRepository ciudadRepository, PaisRepository paisRepository, AppUserService appUserService) {
        this.vueloRepository = vueloRepository;
        this.ciudadRepository = ciudadRepository;
        this.paisRepository = paisRepository;
        this.appUserService = appUserService;
    }

    @Transactional
    public Vuelo guardarVuelo(Vuelo vuelo) {
        return vueloRepository.save(vuelo);
    }

    // Método para mapear VueloDTO a Vuelo
    public void mapVueloDTOToVuelo(VueloDTO vueloDTO, Vuelo vuelo) {
        vuelo.setAerolinea(vueloDTO.getAerolinea());
        vuelo.setOrigen(vueloDTO.getOrigen());
        vuelo.setFechaSalida(vueloDTO.getFechaSalida());
        vuelo.setFechaLlegada(vueloDTO.getFechaLlegada());

        // Asignar país de salida
        Pais paisSalida = paisRepository.findById(vueloDTO.getPaisSalidaId()).orElse(null);
        vuelo.setPaisSalida(paisSalida);

        // Asignar ciudad de salida
        Ciudad ciudadSalida = ciudadRepository.findById(vueloDTO.getCiudadSalidaId()).orElse(null);
        vuelo.setCiudadSalida(ciudadSalida);

        // Asignar país de destino
        Pais paisDestino = paisRepository.findById(vueloDTO.getPaisDestinoId()).orElse(null);
        vuelo.setPaisDestino(paisDestino);

        // Asignar ciudad de destino
        Ciudad ciudadDestino = ciudadRepository.findById(vueloDTO.getCiudadDestinoId()).orElse(null);
        vuelo.setCiudadDestino(ciudadDestino);

        // Asignar vendedor
        AppUser vendedor = appUserService.findById(vueloDTO.getVendedorId());
        vuelo.setVendedor(vendedor);
    }
}
