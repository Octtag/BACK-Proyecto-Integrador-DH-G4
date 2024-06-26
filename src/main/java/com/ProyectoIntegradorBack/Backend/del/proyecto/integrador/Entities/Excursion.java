package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "excursiones")
@Data
public class Excursion extends Producto {

    private String destino;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String itinerario;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaracteristicaExcursion> caracteristicaExcursions;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ciudad")
    private Ciudad ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;


    public Excursion() {
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "destino='" + destino + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", itinerario='" + itinerario + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Excursion excursion)) return false;

        return Objects.equals(destino, excursion.destino);
    }

    @Override
    public int hashCode() {
        return destino != null ? destino.hashCode() : 0;
    }
}
