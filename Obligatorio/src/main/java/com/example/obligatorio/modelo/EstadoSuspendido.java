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
        return true;
    }

    @Override
    public void registrarTransito() throws PeajeException {
        throw new PeajeException("Usuario suspendido: no puede realizar tránsitos");
    }

    @Override
    public void asignarBonificacion() throws PeajeException {
        
    }

    @Override
    public void aplicarDescuento() throws PeajeException {
        
    }

    @Override
    public void registrarNotificacion() throws PeajeException {
       
    }
    
}
