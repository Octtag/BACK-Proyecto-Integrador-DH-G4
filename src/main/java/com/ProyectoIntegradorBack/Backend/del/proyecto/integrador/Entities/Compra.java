package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprador_id")
    private AppUser comprador; // El usuario que compra el producto

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hotel> hotelesComprados;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vuelo> vuelosComprados;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Excursion> excursionesCompradas;

    private LocalDateTime fechaCompra;

    private Integer cantidad;

    public Compra() {
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", fechaCompra=" + fechaCompra +
                ", cantidad=" + cantidad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compra compra)) return false;

        return Objects.equals(id, compra.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

