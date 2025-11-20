package com.example.obligatorio.modelo;

import java.util.ArrayList;

public class Puesto {
    
    private String nombre;
    private String direccion;
    private ArrayList<Transito> transitos = new ArrayList<>();
    private ArrayList<Tarifa> tarifas = new ArrayList<>();
    private ArrayList<Asignacion> asignaciones = new ArrayList<>();

    public Puesto(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public ArrayList<Transito> getTransitos() {
        return transitos;
    }

    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }

    public ArrayList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public void crearTarifa(int monto, String nombre, Categoria categoria){
        Tarifa tarifa = new Tarifa(monto, nombre, categoria, this);
        if(tarifa != null){
            tarifas.add(tarifa);
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
