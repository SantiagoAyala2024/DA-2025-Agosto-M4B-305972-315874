package com.example.obligatorio.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Asignacion {
    
    private LocalDate fecha;
    private Bonificacion bonificacion;
    private Puesto puesto;
    private Propietario propietario;
    private Administrador administrador;
    private ArrayList<Transito> transitos = new ArrayList<>();
    
    public Asignacion(Bonificacion bonificacion, Puesto puesto, Propietario propietario, Administrador administrador, LocalDate fecha) {
        this.fecha = fecha != null ? fecha : LocalDate.now();
        this.bonificacion = bonificacion;
        this.puesto = puesto;
        this.propietario = propietario;
        this.administrador = administrador;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Bonificacion getBonificacion() {
        return bonificacion;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public ArrayList<Transito> getTransitos() {
        return transitos;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
    
}
