package com.example.obligatorio.dto;

import com.example.obligatorio.modelo.Tarifa;

public class TarifaDTO {

    private int monto;
    private String categoria;

    public TarifaDTO(Tarifa t) {
        this.monto = t.getMonto();
        this.categoria = t.getCategoria() != null ? t.getCategoria().getNombre() : null;
    }

    public int getMonto() {
        return monto;
    }

    public String getCategoria() {
        return categoria;
    }

}
