package com.example.obligatorio.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

import observador.Observable;

public class Vehiculo extends Observable {
    
    private String matricula;
    private String modelo;
    private String color;
    private Propietario propietario;
    private Categoria categoria;
    private ArrayList<Transito> transitos = new ArrayList<>();

    public Vehiculo(String matricula, String modelo, String color, Propietario propietario, Categoria categoria) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
        this.propietario = propietario;
        this.categoria = categoria;
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

    public Propietario getPropietario() {
        return propietario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public ArrayList<Transito> getTransitos() {
        return transitos;
    }

    public double montoTarifa(Puesto puesto){
        
        double monto = 0;

        ArrayList<Tarifa> tarifas = puesto.getTarifas();

        for (Tarifa tarifa : tarifas) {
            if(tarifa.getPuesto().equals(puesto) && this.categoria.getNombre().equals(tarifa.getCategoria().getNombre())){
                monto = tarifa.getMonto();
            }
        }
        return monto;
    }

    public double montoTotal(){

        double montoTotal = 0;

        for (Transito transito : transitos) {
            montoTotal += transito.getTotal();
        }
        return montoTotal;
    }

    public void agregarTransito(Transito t) throws PeajeException{
        int saldoActualPropietario = propietario.getSaldoActual();

        if(t.getTotal() <= saldoActualPropietario){
            transitos.add(0,t);
            
            try {
                propietario.registrarNotificacion();
                Notificacion notificacion = new Notificacion("Pasaste por el "+ t.getPuesto()+" con el vehiculo "+ matricula, propietario, java.time.LocalDateTime.now());
                propietario.agregarNotificacion(notificacion);
            } catch (PeajeException e) {

            }
            
            if(t.getAsignacion() != null && t.getDescuento() < 0) {
                try {
                    propietario.registrarNotificacion();
                    String nombreBonificacion = t.getAsignacion().getBonificacion().getNombre();
                    Notificacion notifBonificacion = new Notificacion("Bonificación '" + nombreBonificacion + "' aplicada en " + t.getPuesto() + ". Descuento: $" + Math.abs(t.getDescuento()), propietario, java.time.LocalDateTime.now());
                    propietario.agregarNotificacion(notifBonificacion);
                } catch (PeajeException e) {

                }
            }
            
            propietario.setSaldoActual((int)(saldoActualPropietario - t.getTotal()));
            
            if(propietario.getSaldoActual() < propietario.getSaldoMinimo()){
                try {
                    propietario.registrarNotificacion();
                    Notificacion notificacionSaldo = new Notificacion("Tu saldo actual es de $"+ propietario.getSaldoActual()+". Te recomendamos hacer una recarga", propietario, java.time.LocalDateTime.now());
                    propietario.agregarNotificacion(notificacionSaldo);
                } catch (PeajeException e) {

                }
            }
            
            propietario.avisar(Propietario.Eventos.cambioListaTransitos);
            propietario.avisar(Propietario.Eventos.cambioListaVehiculos);
        }else{
            throw new PeajeException("Saldo insuficiente: "+ propietario.getSaldoActual());
        }
      
    }

    public boolean vehiculoFrecuencia(Puesto puesto, LocalDate fecha){
        int frecuencia = 0;
        for (Transito t : transitos) {
            if(t.getPuesto().equals(puesto) && t.getFecha().equals(fecha)){
                frecuencia++;
            }
        }
        return frecuencia >= 1;
    }

    public Asignacion obtenerAsignacion(Puesto puesto){
        return getPropietario().obtenerAsignacionPorPuesto(puesto);
    }
}
