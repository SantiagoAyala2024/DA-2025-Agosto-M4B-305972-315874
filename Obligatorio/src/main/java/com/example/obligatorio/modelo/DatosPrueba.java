package com.example.obligatorio.modelo;

public class DatosPrueba {
    
    public static void cargar(){

        Fachada fachada = Fachada.getInstancia();

        fachada.agregarPropietario("23456789", "prop.123", "Usuario Propietario", 2000, 500);

        fachada.agregarPropietario("23456788", "a", "Marcos", 2500, 400); 
        
        fachada.agregarAdministrador("12345678", "admin.123", "Usuario Administrador");

        fachada.agregarAdministrador("12345689", "admin.124", "Administrador");

        fachada.asignarTipoBonificacion("Exonerados");
        fachada.asignarTipoBonificacion("Frecuentes");
        fachada.asignarTipoBonificacion("Trabajadores");

        fachada.agregarCategoria("Auto");
        fachada.agregarCategoria("Moto"); 
        fachada.agregarCategoria("Camion"); 

        Propietario propietario1 = fachada.getPropietarios().get(0);
        Propietario propietario2 = fachada.getPropietarios().get(1);
        
        propietario1.crearVehiculo("ABC123", "Fiat", "Rojo", fachada.getCategorias().get(0));
        propietario2.crearVehiculo("ABD114", "Fiat", "Blanco", fachada.getCategorias().get(1));
        
        fachada.agregarPuesto("Puesto 1", "Ubicacion 1");
        fachada.agregarPuesto("Puesto 2", "Ubicacion 2");
        fachada.agregarPuesto("Puesto 3", "Ubicacion 3");
        fachada.agregarPuesto("Puesto 4", "Ubicacion 4");

        Puesto puesto1 = fachada.getPuestos().get(0);
        Puesto puesto2 = fachada.getPuestos().get(1);
        Puesto puesto3 = fachada.getPuestos().get(2);
        Puesto puesto4 = fachada.getPuestos().get(3);
        
        puesto1.crearTarifa(150, "Autos", fachada.getCategorias().get(0));
        puesto1.crearTarifa(100, "Motos", fachada.getCategorias().get(1));
        puesto1.crearTarifa(200, "Camiones", fachada.getCategorias().get(2));
        puesto2.crearTarifa(250, "Camiones", fachada.getCategorias().get(2));
        puesto3.crearTarifa(100, "Motos", fachada.getCategorias().get(1));
        puesto4.crearTarifa(200, "Autos", fachada.getCategorias().get(0));

        Asignacion asignacion1 = new Asignacion(fachada.getBonificaciones().get(0), puesto1, propietario1, null, java.time.LocalDate.now().minusDays(10));
        propietario1.getAsignaciones().add(asignacion1);
        
        Asignacion asignacion2 = new Asignacion(fachada.getBonificaciones().get(1), puesto2, propietario1, null, java.time.LocalDate.now().minusDays(5));
        propietario1.getAsignaciones().add(asignacion2);
        
        Asignacion asignacion3 = new Asignacion(fachada.getBonificaciones().get(2), puesto3, propietario2, null, java.time.LocalDate.now().minusDays(3));
        propietario2.getAsignaciones().add(asignacion3);

        
        try {
            Vehiculo vehiculo1 = propietario1.getVehiculos().get(0);
            Vehiculo vehiculo2 = propietario2.getVehiculos().get(0);
            
            Transito transito1 = new Transito(vehiculo1, puesto1, java.time.LocalDateTime.now().minusDays(2));
            vehiculo1.agregarTransito(transito1);
            
            Transito transito2 = new Transito(vehiculo1, puesto2, java.time.LocalDateTime.now().minusDays(1));
            vehiculo1.agregarTransito(transito2);
            
            Transito transito3 = new Transito(vehiculo2, puesto3, java.time.LocalDateTime.now());
            vehiculo2.agregarTransito(transito3);
            
        } catch (PeajeException e) {
            System.out.println("Error al crear tránsitos de prueba: " + e.getMessage());
        }

        Notificacion notif1 = new Notificacion("Nuevo tránsito registrado ABC123 - Puesto 1", propietario1, java.time.LocalDateTime.now().minusDays(2));
        propietario1.agregarNotificacion(notif1);
        
        Notificacion notif2 = new Notificacion("Bonificación Exonerados aplicada en Puesto 2", propietario1, java.time.LocalDateTime.now().minusDays(1));
        propietario1.agregarNotificacion(notif2);

    }
}
