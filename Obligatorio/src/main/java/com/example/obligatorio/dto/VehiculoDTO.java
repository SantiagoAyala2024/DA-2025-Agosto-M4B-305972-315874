package com.example.obligatorio.dto;

import com.example.obligatorio.modelo.Vehiculo;

public class VehiculoDTO {

    private String matricula;
    private String modelo;
    private String color;
    private String categoria;
    private PropietarioDTO propietario;
    private int cantidadTransitos;
    private double montoTotal;

    public VehiculoDTO(Vehiculo v){
        this.matricula = v.getMatricula();
        this.modelo = v.getModelo();
        this.color = v.getColor();
        this.categoria = v.getCategoria().getNombre();
        if(v.getPropietario() != null){
            this.propietario = new PropietarioDTO(v.getPropietario());
        }
    }

    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public PropietarioDTO getPropietario() {
        return propietario;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getCantidadTransitos() {
        return cantidadTransitos;
    }

    public void setCantidadTransitos(int cantidadTransitos) {
        this.cantidadTransitos = cantidadTransitos;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }
}
