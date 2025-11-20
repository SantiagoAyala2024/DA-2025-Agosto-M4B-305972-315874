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
        return true;
    }

    @Override
    public void registrarTransito() throws PeajeException {

    }

    @Override
    public void asignarBonificacion() throws PeajeException {
       
    }

    @Override
    public void aplicarDescuento() throws PeajeException {
        throw new PeajeException("Estado penalizado: las bonificaciones asignadas no se aplican");
    }

    @Override
    public void registrarNotificacion() throws PeajeException {
        throw new PeajeException("Estado penalizado: no se registran notificaciones");
    }
    
}
