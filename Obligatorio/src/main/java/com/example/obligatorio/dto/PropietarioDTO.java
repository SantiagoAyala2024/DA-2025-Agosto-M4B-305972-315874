package com.example.obligatorio.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.obligatorio.modelo.Propietario;

public class PropietarioDTO {
    
    private String cedula;
    private String nombreCompleto;
    private int saldoActual;
    private String estado;

    public PropietarioDTO(Propietario propietario){
        cedula = propietario.getCedula();
        nombreCompleto = propietario.getNombreCompleto();
        saldoActual = propietario.getSaldoActual();
        estado = propietario.getEstado().getNombre();
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getSaldoActual() {
        return saldoActual;
    }

    public String getEstado() {
        return estado;
    }

    public static List<PropietarioDTO> listaDtos(List<Propietario> lista){
        List<PropietarioDTO> dtos = new ArrayList<>();
        for(Propietario p: lista){
           dtos.add(new PropietarioDTO(p));
        }
        return dtos;
    }
}
