package com.example.obligatorio.modelo;

import java.time.LocalDateTime;

public class Notificacion {
    
    private String mensaje;
    private Propietario propietario;
    private LocalDateTime fechaHora;

    public Notificacion(String mensaje, Propietario propietario, LocalDateTime fechaHora) {
        this.mensaje = mensaje;
        this.propietario = propietario;
        this.fechaHora = fechaHora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

}
