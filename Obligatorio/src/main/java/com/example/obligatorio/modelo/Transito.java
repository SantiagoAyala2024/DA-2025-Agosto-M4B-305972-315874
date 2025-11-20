package com.example.obligatorio.modelo;

import java.time.LocalDateTime;

public class Transito {
    
    private LocalDateTime fecha;
    private double monto;
    private double descuento;
    private double total;
    private Vehiculo vehiculo;
    private Puesto puesto;
    private Asignacion asignacion;

    public Transito(Vehiculo vehiculo, Puesto puesto) {
        this.vehiculo = vehiculo;
        this.puesto = puesto;
        this.fecha = LocalDateTime.now();
        this.monto = vehiculo.montoTarifa(puesto);
        this.asignacion = obtenerAsignacionValida(vehiculo, puesto);
        this.total = calcularMonto(puesto, this.fecha, this.monto);
        this.descuento = this.total - this.monto;
    }

    public Transito(Vehiculo vehiculo, Puesto puesto, LocalDateTime fecha) {
        this.vehiculo = vehiculo;
        this.puesto = puesto;
        this.fecha = fecha;
        this.monto = vehiculo.montoTarifa(puesto);
        this.asignacion = obtenerAsignacionValida(vehiculo, puesto);
        this.total = calcularMonto(puesto, this.fecha, this.monto);
        this.descuento = this.total - this.monto;
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

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public Asignacion getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }

    private Asignacion obtenerAsignacionValida(Vehiculo vehiculo, Puesto puesto) {
        Asignacion asignacion = vehiculo.obtenerAsignacion(puesto);
        if (asignacion != null) {
            try {
                vehiculo.getPropietario().aplicarDescuento();
                return asignacion;
            } catch (PeajeException e) {
                return null;
            }
        }
        return null;
    }

    private double calcularMonto(Puesto puesto, LocalDateTime fecha, double monto){
        double montoTotal = monto;
        double descuento = 1;
        if(asignacion != null){
            descuento = asignacion.getBonificacion().calcularBonificacion(fecha.toLocalDate(), this.vehiculo.vehiculoFrecuencia(puesto, fecha.toLocalDate()));
            montoTotal = monto * descuento;
        }
        return montoTotal;
    }
}
