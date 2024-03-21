package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCategoria;
    private String descripcionCategoria;

    @OneToMany(mappedBy = "categoria")
    private List<Excursion> excursiones = new ArrayList<>();
    private String imageURL;

    public Categoria() {
    }

    public Categoria(Long id, String nombre, String descripcion, String imagenURL) {
        this.id = id;
        this.nombreCategoria = nombre;
        this.descripcionCategoria = descripcion;
        this.imageURL = imagenURL;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria categoria)) return false;

        return id.equals(categoria.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
