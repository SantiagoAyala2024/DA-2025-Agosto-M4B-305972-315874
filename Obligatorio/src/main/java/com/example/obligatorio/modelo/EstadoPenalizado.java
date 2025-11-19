package com.example.obligatorio.modelo;

public class EstadoPenalizado extends Estado {

    public EstadoPenalizado(Propietario propietario) {
        super(propietario, "Penalizado");
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
        getPropietario().cambiarEstado(new EstadoSuspendido(getPropietario()));
    }

    @Override
    public void penalizado() throws PeajeException {
        throw new PeajeException("El propietario ya está en estado Penalizado");
    }

    @Override
    public boolean puedeLoguearse() {
        // Penalizado: El usuario puede ingresar al sistema
        return true;
    }

    @Override
    public void registrarTransito() throws PeajeException {
        // Penalizado: Puede realizar tránsitos sin restricciones
        // No hay impedimentos para registrar tránsitos
    }

    @Override
    public void asignarBonificacion() throws PeajeException {
        // Penalizado: Puede recibir asignación de bonificaciones
        // No hay restricciones para asignar bonificaciones
    }

    @Override
    public void aplicarDescuento() throws PeajeException {
        // Penalizado: no aplican las bonificaciones que tenga asignadas
        throw new PeajeException("Estado penalizado: las bonificaciones asignadas no se aplican");
    }

    @Override
    public void registrarNotificacion() throws PeajeException {
        // Penalizado: NO se le registran notificaciones
        throw new PeajeException("Estado penalizado: no se registran notificaciones");
    }
    
}
