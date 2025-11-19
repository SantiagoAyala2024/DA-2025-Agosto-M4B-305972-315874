package com.example.obligatorio.modelo;

public class EstadoSuspendido extends Estado {

    public EstadoSuspendido(Propietario propietario) {
        super(propietario, "Suspendido");
    }

    @Override
    public void habilitado() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoHabilitado(getPropietario()));
    }

    @Override
    public void deshabilitado() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoDeshabilitado(getPropietario()));
    }

    @Override
    public void suspendido() throws PeajeException {
        throw new PeajeException("El propietario ya está en estado Suspendido");
    }

    @Override
    public void penalizado() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoPenalizado(getPropietario()));
    }

    @Override
    public boolean puedeLoguearse() {
        // Suspendido: El usuario puede ingresar al sistema
        return true;
    }

    @Override
    public void registrarTransito() throws PeajeException {
        // Suspendido: no puede realizar tránsitos
        throw new PeajeException("Usuario suspendido: no puede realizar tránsitos");
    }

    @Override
    public void asignarBonificacion() throws PeajeException {
        // Suspendido: puede recibir asignaciones de bonificaciones
        // No hay restricciones para asignar bonificaciones
    }

    @Override
    public void aplicarDescuento() throws PeajeException {
        // Suspendido: puede aplicar descuentos (no especificado que no pueda)
        // No hay restricciones para aplicar descuentos
    }

    @Override
    public void registrarNotificacion() throws PeajeException {
        // Suspendido: puede recibir notificaciones normalmente
        // No hay restricciones para notificaciones
    }
    
}
