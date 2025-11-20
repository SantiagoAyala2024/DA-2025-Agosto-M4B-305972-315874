package com.example.obligatorio.modelo;

import java.util.ArrayList;

public class SistemaPeajes {
    
    private ArrayList<Categoria> categorias = new ArrayList<>();
    private ArrayList<Puesto> puestos = new ArrayList<>();
    private ArrayList<Bonificacion> bonificaciones = new ArrayList<>();
    private ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private ArrayList<Estado> estados = new ArrayList<>();

    public void agregarPuesto(String nombre, String ubicacion) {
        puestos.add(new Puesto(nombre, ubicacion));
    }

    public void agregarCategoria(String nombre) {
        categorias.add(new Categoria(nombre));
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public ArrayList<Puesto> getPuestos() {
        return puestos;
    }

    public Puesto buscarPuestoPorNombre(String nombre){
        for (Puesto p : puestos) {
            if(p.getNombre().equals(nombre)){
                return p;
            }
        }
        return null;
    }

    public Bonificacion asignarTipoBonificacion(String tipoBonificacion){
        
        Bonificacion bonificacion = null;

        if(tipoBonificacion.equals("Exonerados")){
            bonificacion = new Exonerados(tipoBonificacion);
        }else if(tipoBonificacion.equals("Frecuentes")){
            bonificacion = new Frecuentes(tipoBonificacion);
        }else if(tipoBonificacion.equals("Trabajadores")){
            bonificacion = new Trabajadores(tipoBonificacion);
        }
        
        if (bonificacion != null) {
            bonificaciones.add(bonificacion);
        }
        
        return bonificacion;
    }

    public ArrayList<Bonificacion> getBonificaciones() {
        return bonificaciones;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }
    
}
