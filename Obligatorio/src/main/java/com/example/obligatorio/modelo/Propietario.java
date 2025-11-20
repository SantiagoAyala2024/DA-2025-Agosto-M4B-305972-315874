package com.example.obligatorio.modelo;

import java.util.ArrayList;

public class Propietario extends Usuario {

    public enum Eventos{cambioListaVehiculos, cambioListaTransitos, cambiosListaBonificaciones, cambioListaNotificaciones, cambioEstado};

    private int saldoActual;
    private int saldoMinimo;
    private ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private ArrayList<Notificacion> notificaciones = new ArrayList<>();
    private ArrayList<Asignacion> asignaciones = new ArrayList<>();
    private Estado estado;

    public Propietario(String cedula, String password, String nombreCompleto, int saldoActual, int saldoMinimo) {
        super(cedula, password, nombreCompleto);
        this.saldoActual = saldoActual;
        this.saldoMinimo = saldoMinimo;
        this.estado = new EstadoHabilitado(this);
    }

    public int getSaldoActual() {
        return saldoActual;
    }

    public int getSaldoMinimo() {
        return saldoMinimo;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public ArrayList<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public ArrayList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setSaldoActual(int saldoActual) {
        this.saldoActual = saldoActual;
    }

    public void setSaldoMinimo(int saldoMinimo) {
        this.saldoMinimo = saldoMinimo;
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula){
        for (Vehiculo vehiculo : vehiculos) {
            if(vehiculo.getMatricula().equals(matricula)){
                return vehiculo;
            }
        }
        return null;
    }

    public void crearVehiculo(String matricula, String modelo, String color, Categoria categoria){
        Vehiculo vehiculo = new Vehiculo(matricula, modelo, color, this, categoria);
        if(vehiculo != null){
            vehiculos.add(vehiculo);
            avisar(Eventos.cambioListaVehiculos);
        }
    }

    public Asignacion obtenerAsignacionPorPuesto(Puesto puesto){
        Asignacion asignacion = null;
        for (Asignacion a : asignaciones) {
            if(a.getPuesto().equals(puesto)){
                asignacion = a;
            }
        }
        return asignacion;
    }

    public ArrayList<Transito> obtenerTransitos(){
        ArrayList<Transito> transitos = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            transitos.addAll(vehiculo.getTransitos());
        }
        return transitos;
    }

    public void agregarAsignacion(Asignacion asignacion){
        asignaciones.add(0, asignacion);
        avisar(Eventos.cambiosListaBonificaciones);
    }

    public void tieneAsignacionPuesto(Puesto puesto) throws PeajeException{
        Asignacion asignacion = obtenerAsignacionPorPuesto(puesto);
        if(asignacion != null){
            throw new PeajeException("Ya tiene una bonificación asignada para ese puesto");
        }
    }

    public void agregarNotificacion(Notificacion notificacion){
        notificaciones.add(0, notificacion);
        avisar(Eventos.cambioListaNotificaciones);
    }

    public void eliminarNotificaciones(){
        this.notificaciones = new ArrayList<>();
        avisar(Eventos.cambioListaNotificaciones);
    }
     
    public void actualizarTransitoSaldo(Transito transito){
        this.saldoActual = (int)(saldoActual - transito.getTotal());
        avisar(Eventos.cambioListaTransitos);
    }

    public void habilitado() throws PeajeException {
        estado.habilitado();
    }

    public void deshabilitado() throws PeajeException {
        estado.deshabilitado();
    }

    public void suspendido() throws PeajeException {
        estado.suspendido();
    }

    public void penalizado() throws PeajeException {
        estado.penalizado();
    }

    protected void cambiarEstado(Estado nuevoEstado) {
        this.estado = nuevoEstado;
        avisar(Eventos.cambioEstado);
    }

    public boolean puedeLoguearse() {
        return estado.puedeLoguearse();
    }

    public void registrarTransito() throws PeajeException {
        estado.registrarTransito();
    }

    public void asignarBonificacion() throws PeajeException {
        estado.asignarBonificacion();
    }

    public void aplicarDescuento() throws PeajeException {
        estado.aplicarDescuento();
    }

    public void registrarNotificacion() throws PeajeException {
        estado.registrarNotificacion();
    }
}
