package com.example.obligatorio.modelo;

import java.time.LocalDate;

public class Trabajadores extends Bonificacion{

    public Trabajadores(String nombre) {
        super(nombre);
    }

    @Override
    public double calcularBonificacion(LocalDate fechaAsignacion, boolean frecuente) {

        int dayOfWeek = fechaAsignacion.getDayOfWeek().getValue();
        if(dayOfWeek >= 1 && dayOfWeek <= 5){
            return 0.8; 
        }else{
            return 1;
        }
    }
    
}
