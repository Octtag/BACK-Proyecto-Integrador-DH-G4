package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import javax.persistence.*;

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

