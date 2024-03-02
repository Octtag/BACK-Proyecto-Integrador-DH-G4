package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Paises")
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_pais", nullable = false, length = 30)
    private String nombrePais;

    @OneToMany(mappedBy = "pais", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ciudad> ciudades;

    public Pais() {
    }

    @Override
    public String toString() {
        return "Pais{" +
                "id=" + id +
                ", nombrePais='" + nombrePais + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pais pais)) return false;

        return Objects.equals(id, pais.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
