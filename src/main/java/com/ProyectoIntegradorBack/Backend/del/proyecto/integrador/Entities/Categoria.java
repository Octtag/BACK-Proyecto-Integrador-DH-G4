package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCategoria;
    private String descripcionCategoria;

    private String imageURL;
    public Categoria() {
    }

    public Categoria(Long id, String nombre, String descripcion, String imagenURL) {
        this.id = id;
        this.nombreCategoria = nombre;
        this.descripcionCategoria = descripcion;
        this.imageURL = imagenURL;
    }

}
