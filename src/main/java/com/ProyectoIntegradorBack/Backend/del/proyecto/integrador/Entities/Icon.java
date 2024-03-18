package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "iconos")
@Data
public class Icon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Icono;
    private String Nombre;
    public Icon() {
    }
}
