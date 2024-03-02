package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.DTOs;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Data
public class VueloDTO {
    private String aerolinea;
    private String origen;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private Long paisSalidaId;
    private Long ciudadSalidaId;
    private Long paisDestinoId;
    private Long ciudadDestinoId;
    private Long vendedorId;
    private MultipartFile[] imagenes;
}
