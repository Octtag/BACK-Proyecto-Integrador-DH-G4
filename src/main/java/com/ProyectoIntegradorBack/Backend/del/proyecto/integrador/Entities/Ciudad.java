package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "Ciudades")
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCiudad;
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id")
    private Pais pais;

    public Ciudad() {
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "id=" + id +
                ", nombreCiudad='" + nombreCiudad + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ciudad ciudad)) return false;

        return Objects.equals(id, ciudad.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
