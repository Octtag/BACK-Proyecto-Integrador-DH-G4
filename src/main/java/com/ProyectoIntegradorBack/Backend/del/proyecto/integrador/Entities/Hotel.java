package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "Hoteles")
public class Hotel extends Producto{

    private String nombreHotel;
    private String nit;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hotel_id") // Esta es la columna en la tabla 'CaracteristicaHotel'.
    private List<CaracteristicaHotel> caracteristicasHotel = new ArrayList<>();

    private String direccion;
    private String telefono;
    private String categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    private AppUser vendedor;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes;

    private String urlWeb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais", nullable = false)
    private Pais pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ciudad", nullable = false)
    private Ciudad ciudad; // Relación con la entidad Ciudad.

    public Hotel() {
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "nombreHotel='" + nombreHotel + '\'' +
                ", nit='" + nit + '\'' +
                ", caracteristicasHotel=" + caracteristicasHotel +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", categoria='" + categoria + '\'' +
                ", urlWeb='" + urlWeb + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel hotel)) return false;

        return Objects.equals(nombreHotel, hotel.nombreHotel);
    }

    @Override
    public int hashCode() {
        return nombreHotel != null ? nombreHotel.hashCode() : 0;
    }
}
