package com.example.obligatorio.modelo;

public class EstadoDeshabilitado extends Estado {

    public EstadoDeshabilitado(Propietario propietario) {
        super(propietario, "Deshabilitado");
    }

    @Override
    public void habilitado() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoHabilitado(getPropietario()));
    }

    @Override
    public void deshabilitado() throws PeajeException {
        throw new PeajeException("El propietario ya está en estado Deshabilitado");
    }

    @Override
    public void suspendido() throws PeajeException {
        throw new PeajeException("No se puede suspender un propietario deshabilitado. Debe habilitarlo primero");
    }

    @Override
    public void penalizado() throws PeajeException {
        throw new PeajeException("No se puede penalizar un propietario deshabilitado. Debe habilitarlo primero");
    }

    @Override
    public boolean puedeLoguearse() {
        return false;
    }

    @Override
    public void registrarTransito() throws PeajeException {
        throw new PeajeException("Usuario deshabilitado: no puede realizar tránsitos");
    }

    @Override
    public void asignarBonificacion() throws PeajeException {
        throw new PeajeException("Usuario deshabilitado: no se pueden asignar bonificaciones");
    }

    @Override
    public void aplicarDescuento() throws PeajeException {
        throw new PeajeException("Usuario deshabilitado: no puede aplicar descuentos");
    }

    @Override
    public void registrarNotificacion() throws PeajeException {
        throw new PeajeException("Usuario deshabilitado: no puede recibir notificaciones");
    }
    
}
