package com.example.obligatorio.modelo;

import observador.Observable;

public abstract class Usuario extends Observable {
    
    private String cedula;
    private String password;
    private String nombreCompleto;

    public enum Eventos {
        cambioListaVehiculos,
        cambioListaTransitos,
        cambiosListaBonificaciones,
        cambioListaNotificaciones
    }

    public Usuario(String cedula, String password, String nombreCompleto) {
        this.cedula = cedula;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
    }

    public String getCedula() {
        return cedula;
    }

    public String getPassword() {
        return password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
}
