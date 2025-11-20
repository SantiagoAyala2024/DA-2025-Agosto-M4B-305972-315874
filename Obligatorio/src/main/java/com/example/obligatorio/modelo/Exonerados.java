package com.example.obligatorio.modelo;

import java.time.LocalDate;

public class Exonerados extends Bonificacion {

    public Exonerados(String nombre) {
        super(nombre);
    }

    @Override
    public double calcularBonificacion(LocalDate fechaAsignacion, boolean frecuente) {
        return 0;
    }
    
}
