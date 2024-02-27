package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vuelos")
public class Vuelo extends Producto {

    private String aerolinea;
    private String origen;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @OneToMany(mappedBy = "vuelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenesUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_salida_id")
    private Pais paisSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_salida_id")
    private Ciudad ciudadSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_destino_id")
    private Pais paisDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_destino_id")
    private Ciudad ciudadDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    private AppUser vendedor;

    public Vuelo() {
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "aerolinea='" + aerolinea + '\'' +
                ", origen='" + origen + '\'' +
                ", fechaSalida=" + fechaSalida +
                ", fechaLlegada=" + fechaLlegada +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vuelo vuelo)) return false;

        return Objects.equals(aerolinea, vuelo.aerolinea);
    }

    @Override
    public int hashCode() {
        return aerolinea != null ? aerolinea.hashCode() : 0;
    }
}

