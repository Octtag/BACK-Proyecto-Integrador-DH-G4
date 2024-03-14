package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "favoritos")
@Data
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excursion_id", nullable = false)
    private Excursion excursion;

    public Favorito() {
    }
}
