package com.example.obligatorio.modelo;

import java.time.LocalDate;

public class Frecuentes extends Bonificacion {

    public Frecuentes(String nombre) {
        super(nombre);
    }

    @Override
    public double calcularBonificacion(LocalDate fechaAsignacion, boolean frecuente) {

        if(frecuente){
            return 0.5;
        }else{
            return 1;
        }

    }
    
}
