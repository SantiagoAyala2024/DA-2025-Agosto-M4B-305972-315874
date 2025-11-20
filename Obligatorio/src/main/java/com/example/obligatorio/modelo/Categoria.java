package com.example.obligatorio.modelo;

import java.util.ArrayList;

public class Categoria {
    
    private String nombre;
    private ArrayList<Tarifa> tarifas = new ArrayList<>();
    private ArrayList<Vehiculo> vehiculos;
    
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

}
