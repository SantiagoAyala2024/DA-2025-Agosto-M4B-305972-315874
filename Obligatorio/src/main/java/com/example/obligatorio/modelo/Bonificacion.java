package com.example.obligatorio.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Bonificacion {
 
    private String nombre;
    private Asignacion asignacion;
    private ArrayList<Puesto> puestos = new ArrayList<>();

    public Bonificacion(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Asignacion getAsignacion() {
        return asignacion;
    }

    public ArrayList<Puesto> getPuestos() {
        return puestos;
    }

    public abstract double calcularBonificacion(LocalDate fechaAsignacion, boolean frecuente);

    
}
