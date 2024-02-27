package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import jakarta.persistence.*;

import java.util.List;

@MappedSuperclass
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    public Producto() {
    }
}

