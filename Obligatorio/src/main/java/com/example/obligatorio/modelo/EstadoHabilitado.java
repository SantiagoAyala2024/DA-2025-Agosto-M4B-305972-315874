package com.example.obligatorio.modelo;

public class EstadoHabilitado extends Estado {

    public EstadoHabilitado(Propietario propietario) {
        super(propietario, "Activo");
    }

    @Override
    public void habilitado() throws PeajeException {
        throw new PeajeException("El propietario ya está en estado Activo");
    }

    @Override
    public void deshabilitado() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoDeshabilitado(getPropietario()));
    }

    @Override
    public void suspendido() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoSuspendido(getPropietario()));
    }

    @Override
    public void penalizado() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoPenalizado(getPropietario()));
    }

    @Override
    public boolean puedeLoguearse() {
        return true;
    }

    @Override
    public void registrarTransito() throws PeajeException {
        // El propietario habilitado puede registrar tránsitos normalmente
        // No hay restricciones - operación permitida
    }

    @Override
    public void asignarBonificacion() throws PeajeException {
        // El propietario habilitado puede recibir bonificaciones sin restricciones
        // No hay restricciones - operación permitida
    }

    @Override
    public void aplicarDescuento() throws PeajeException {
        // El propietario habilitado puede aplicar todos los descuentos disponibles
        // No hay restricciones - operación permitida
    }

    @Override
    public void registrarNotificacion() throws PeajeException {
        // El propietario habilitado recibe todas las notificaciones
        // No hay restricciones - operación permitida
    }

}
