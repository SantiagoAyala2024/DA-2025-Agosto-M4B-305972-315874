package com.example.obligatorio.modelo;

import java.util.ArrayList;

public class Fachada {

    private SistemaAcceso sistemaAcceso = new SistemaAcceso();
    private SistemaPeajes sistemaPeajes = new SistemaPeajes();

    //SINGLETON

    private static Fachada instancia = new Fachada();

    public static Fachada getInstancia(){
        return instancia;
    }

    private Fachada(){

    }

    //DELEGACIONES

    public void agregarAdministrador(String cedula, String password, String nombreCompleto) {
        sistemaAcceso.agregarAdministrador(cedula, password, nombreCompleto);
    }

    public void agregarPropietario(String cedula, String password, String nombreCompleto, int saldoActual, int saldoMinimo) {
        sistemaAcceso.agregarPropietario(cedula, password, nombreCompleto, saldoActual, saldoMinimo);
    }

    public Administrador loginAdministrador(String cedula, String password) throws PeajeException {
        return sistemaAcceso.loginAdministrador(cedula, password);
    }

    public Propietario loginPropietario(String cedula, String password) throws PeajeException {
        return sistemaAcceso.loginPropietario(cedula, password);
    }

    public void agregarPuesto(String nombre, String ubicacion) {
        sistemaPeajes.agregarPuesto(nombre, ubicacion);
    }

    public void agregarCategoria(String nombre) {
        sistemaPeajes.agregarCategoria(nombre);
    }

    public ArrayList<Categoria> getCategorias() {
        return sistemaPeajes.getCategorias();
    }

    public ArrayList<Puesto> getPuestos() {
        return sistemaPeajes.getPuestos();
    }

    public Puesto buscarPuestoPorNombre(String nombre) {
        return sistemaPeajes.buscarPuestoPorNombre(nombre);
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) {
        return sistemaAcceso.buscarVehiculoPorMatricula(matricula);
    }

    public ArrayList<Propietario> getPropietarios() {
        return sistemaAcceso.getPropietarios();
    }

    public Bonificacion asignarTipoBonificacion(String tipoBonificacion) {
        return sistemaPeajes.asignarTipoBonificacion(tipoBonificacion);
    }

    public Propietario buscarPropietarioPorCedula(String cedula) {
        return sistemaAcceso.buscarPropietarioPorCedula(cedula);
    }

    public ArrayList<Bonificacion> getBonificaciones() {
        return sistemaPeajes.getBonificaciones();
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return sistemaPeajes.getVehiculos();
    }

    public ArrayList<Administrador> getAdministradores() {
        return sistemaAcceso.getAdministradores();
    }

    public ArrayList<Administrador> getAdministradoresConectados() {
        return sistemaAcceso.getAdministradoresConectados();
    }

    public boolean adminConectado(Administrador administrador) {
        return sistemaAcceso.adminConectado(administrador);
    }

    public void agregarAdministradorConectado(Administrador admin) {
        sistemaAcceso.agregarAdministradorConectado(admin);
    }

    public void quitarSesionAdmin(Administrador admin) {
        sistemaAcceso.quitarSesionAdmin(admin);
    }

    public ArrayList<Estado> getEstados() {
        return sistemaPeajes.getEstados();
    }

}
