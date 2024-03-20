package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.Pais;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CiudadDTO {
    private Long id;
    private String nombreCiudad;
    private String zipCode;
    private String pais;
}

