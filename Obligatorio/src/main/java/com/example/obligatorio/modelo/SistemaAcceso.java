package com.example.obligatorio.modelo;

import java.util.ArrayList;

import observador.Observable;
import observador.Observador;

public class SistemaAcceso implements Observador {

    private ArrayList<Administrador> administradores = new ArrayList();
    private ArrayList<Administrador> administradoresConectados = new ArrayList();
    private ArrayList<Propietario> propietarios = new ArrayList();

    public void agregarAdministrador(String cedula, String password, String nombreCompleto){
        administradores.add(new Administrador(cedula, password, nombreCompleto));
    }

    public void agregarPropietario(String cedula, String password, String nombreCompleto, int saldoActual, int saldoMinimo){
        propietarios.add(new Propietario(cedula, password, nombreCompleto, saldoActual, saldoMinimo));
    }

    public Administrador loginAdministrador(String cedula, String password) throws PeajeException{
        Administrador admin = (Administrador) login(cedula, password, administradores);
        if(admin == null) throw new PeajeException("Acceso Denegado");
        return admin;
    }

    public Propietario loginPropietario(String cedula, String password) throws PeajeException{
        Propietario prop = (Propietario) login(cedula, password, propietarios);
        if(prop == null) throw new PeajeException("Acceso Denegado");
        return prop;
    }

    private Usuario login(String cedula, String password, ArrayList lista){

        Usuario usuario;

        for (Object o : lista) {
            usuario = (Usuario) o;
            if(usuario.getCedula().equals(cedula) && usuario.getPassword().equals(password)){
                return usuario;
            }
        }
        return null;
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula){
        for (Propietario propietarios : propietarios) {
            Vehiculo vehiculo = propietarios.buscarVehiculoPorMatricula(matricula);
            if(vehiculo != null){
                return vehiculo;
            }
        }
        return null;
    }

    public ArrayList<Propietario> getPropietarios() {
        return propietarios;
    }

    public ArrayList<Administrador> getAdministradores() {
        return administradores;
    }

    public ArrayList<Administrador> getAdministradoresConectados() {
        return administradoresConectados;
    }

    public Propietario buscarPropietarioPorCedula(String cedula){
        for (Propietario propietario : propietarios) {
            if(propietario.getCedula().equals(cedula)){
                return propietario;
            }
        }
        return null;
    }   

    public boolean adminConectado(Administrador administrador){
        for (Administrador admin : administradoresConectados) {
            if(admin.equals(administrador)){
                return true;
            }
        }
        return false;
    }

    public void agregarAdministradorConectado(Administrador admin){
        administradoresConectados.add(admin);
    }

    public void quitarSesionAdmin(Administrador admin) {
        Administrador aux = null;
        for (Administrador a : administradoresConectados) {
            if (admin.equals(a)) {
                aux = admin;
            }
        }
        administradoresConectados.remove(aux);
    }
    
    @Override
    public void actualizar(Object evento, Observable origen) {
        for (Administrador admin : administradoresConectados) {
            admin.avisar(evento);
        }
    }
}

