package com.example.obligatorio.dto;

import java.time.LocalDateTime;

import com.example.obligatorio.modelo.Notificacion;

public class NotificacionDTO {
    
    private String mensaje;
    private LocalDateTime fecha;

    public NotificacionDTO(Notificacion notificacion) {
        if (notificacion != null) {
            this.mensaje = notificacion.getMensaje();
            this.fecha = notificacion.getFechaHora();
        }
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}