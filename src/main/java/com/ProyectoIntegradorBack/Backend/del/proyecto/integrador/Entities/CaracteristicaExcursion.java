package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "caracteristicas_excursiones")
@Data
//@JsonIgnoreProperties({"caracteristica"}) // Ignora la propiedad "caracteristica" en la serialización

public class CaracteristicaExcursion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caracteristica_id", nullable = false)
    private Caracteristica caracteristica;
    private Long excursion;

    public CaracteristicaExcursion() {
    }
}
