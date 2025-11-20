package com.example.obligatorio.modelo;

public abstract class Estado {
    
    private Propietario propietario;
    private String nombre;

    public Estado(Propietario propietario, String nombre){
        this.propietario = propietario;
        this.nombre = nombre;
    }

    public Propietario getPropietario(){
        return propietario;
    }

    public String getNombre(){
        return nombre;
    }

    public abstract void habilitado() throws PeajeException;
    public abstract void deshabilitado() throws PeajeException;    
    public abstract void suspendido() throws PeajeException;
    public abstract void penalizado() throws PeajeException;

    public abstract boolean puedeLoguearse();
    public abstract void registrarTransito() throws PeajeException;
    public abstract void asignarBonificacion() throws PeajeException;
    public abstract void aplicarDescuento() throws PeajeException;
    public abstract void registrarNotificacion() throws PeajeException;
}
