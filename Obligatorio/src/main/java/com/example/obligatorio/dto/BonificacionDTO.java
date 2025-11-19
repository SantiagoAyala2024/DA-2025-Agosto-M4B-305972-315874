package com.example.obligatorio.dto;

import java.time.LocalDate;
import com.example.obligatorio.modelo.Bonificacion;
import com.example.obligatorio.modelo.Asignacion;

public class BonificacionDTO {
    
    private String nombre;
    private String puesto;
    private LocalDate fechaAsignacion;

    public BonificacionDTO(Asignacion asignacion) {
        if (asignacion != null) {
            Bonificacion bonif = asignacion.getBonificacion();
            this.nombre = bonif != null ? bonif.getNombre() : "";
            this.puesto = asignacion.getPuesto() != null ? asignacion.getPuesto().getNombre() : "";
            this.fechaAsignacion = asignacion.getFecha();
        }
    }

    public String getNombre() {
        return nombre != null ? nombre : "";
    }

    public String getPuesto() {
        return puesto != null ? puesto : "";
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }
}