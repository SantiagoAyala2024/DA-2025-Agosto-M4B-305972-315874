package com.example.obligatorio.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.obligatorio.modelo.Transito;
import com.example.obligatorio.dto.VehiculoDTO;
import com.example.obligatorio.dto.NombreDTO;

public class TransitoDTO {
    
    private LocalDateTime fecha;
    private double monto;
    private double descuento;
    private double total;
    private VehiculoDTO vehiculo;
    private NombreDTO puesto;
    private String nombreBonificacion;

    public TransitoDTO(Transito transito){
        fecha = transito.getFecha();
        monto = transito.getMonto();
        descuento = transito.getDescuento();
        total = transito.getTotal();
        
        if(transito.getVehiculo() != null){
            vehiculo = new VehiculoDTO(transito.getVehiculo());
        }
        if(transito.getPuesto() != null){
            puesto = new NombreDTO(transito.getPuesto().getNombre());
        }
        if(transito.getAsignacion() != null && transito.getAsignacion().getBonificacion() != null){
            nombreBonificacion = transito.getAsignacion().getBonificacion().getNombre();
        }
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getMonto() {
        return monto;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    public VehiculoDTO getVehiculo() {
        return vehiculo;
    }

    public NombreDTO getPuesto() {
        return puesto;
    }

    public String getNombreBonificacion() {
        return nombreBonificacion;
    }

    public static List<TransitoDTO> listaDtos(List<Transito> lista){
        List<TransitoDTO> dtos = new ArrayList<>();
        for(Transito t: lista){
           dtos.add(new TransitoDTO(t));
        }
        return dtos;
    }
}
