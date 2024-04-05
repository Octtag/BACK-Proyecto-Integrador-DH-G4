package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long puntuacion;
    @Column(nullable = false)
    private String detalle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excursion_id")
    private Excursion excursion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    public Comentario() {
    }
    @Override
    public String toString() {
        return "Imagen{" +
                "id=" + id +
                ", puntuacion='" + puntuacion + '\'' +
                ", detalle='" + detalle + '\'' +
                '}';
    }
}
