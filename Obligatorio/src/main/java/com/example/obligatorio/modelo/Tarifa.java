package com.example.obligatorio.modelo;

public class Tarifa {
    
    private int monto;
    private String nombre;
    private Categoria categoria;
    private Puesto puesto;

    public Tarifa(int monto, String nombre, Categoria categoria, Puesto puesto) {
        this.monto = monto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.puesto = puesto;
    }

    public int getMonto() {
        return monto;
    }

    public String getNombre() {
        return nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Puesto getPuesto() {
        return puesto;
    }
}
