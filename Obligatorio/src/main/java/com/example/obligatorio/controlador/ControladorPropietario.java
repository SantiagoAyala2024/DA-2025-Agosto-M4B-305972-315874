package com.example.obligatorio.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.obligatorio.ConexionNavegador;
import com.example.obligatorio.dto.BonificacionDTO;
import com.example.obligatorio.dto.NotificacionDTO;
import com.example.obligatorio.dto.PropietarioDTO;
import com.example.obligatorio.dto.TransitoDTO;
import com.example.obligatorio.dto.VehiculoDTO;
import com.example.obligatorio.modelo.Asignacion;
import com.example.obligatorio.modelo.Notificacion;
import com.example.obligatorio.modelo.PeajeException;
import com.example.obligatorio.modelo.Propietario;
import com.example.obligatorio.modelo.Transito;
import com.example.obligatorio.modelo.Vehiculo;

import observador.Observable;
import observador.Observador;

@RestController
@RequestMapping("/propietario")
@Scope("session")
public class ControladorPropietario implements Observador {
    
    private Propietario propietario;
    private final ConexionNavegador conexionNavegador;

    public ControladorPropietario(@Autowired ConexionNavegador conexionNavegador){
        this.conexionNavegador = conexionNavegador;
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE();
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> vistaConectada(@SessionAttribute(name = "usuarioProp", required = false) Propietario prop) throws PeajeException {
        
        if (prop == null) {
            throw new PeajeException("Debe iniciar sesión como propietario");
        }

        if (propietario == null || !propietario.equals(prop)) {
            if (propietario != null) {
                propietario.quitarObservador(this);
            }
            this.propietario = prop;
        }
        
        propietario.agregarObservador(this);
        
        return Respuesta.lista(propietario(), bonificaciones(), vehiculos(), transitos(), notificaciones(), new Respuesta("habilitarIngreso", true));
    }

    private Respuesta propietario(){
        return new Respuesta("propietario", new PropietarioDTO(propietario));
    }

    private Respuesta bonificaciones() {
        ArrayList<BonificacionDTO> lista = new ArrayList<>();
        if (propietario != null) {
            for (Asignacion asignacion : propietario.getAsignaciones()) {
                lista.add(new BonificacionDTO(asignacion));
            }
        }
        return new Respuesta("bonificaciones", lista);
    }

    private Respuesta vehiculos() {
        ArrayList<VehiculoDTO> lista = new ArrayList<>();
        if (propietario != null) {
            for (Vehiculo vehiculo : propietario.getVehiculos()) {
                VehiculoDTO vehiculoDTO = new VehiculoDTO(vehiculo);
                vehiculoDTO.setCantidadTransitos(vehiculo.getTransitos().size());
                vehiculoDTO.setMontoTotal(vehiculo.montoTotal());
                lista.add(vehiculoDTO);
            }
        }
        return new Respuesta("vehiculos", lista);
    }

    private Respuesta transitos() {
        ArrayList<TransitoDTO> lista = new ArrayList<>();
        if (propietario != null) {
            for (Transito transito : propietario.obtenerTransitos()) {
                lista.add(new TransitoDTO(transito));
            }
        }
        return new Respuesta("transitos", lista);
    }

    private Respuesta notificaciones() {
        ArrayList<NotificacionDTO> lista = new ArrayList<>();
        if (propietario != null) {
            for (Notificacion notificacion : propietario.getNotificaciones()) {
                lista.add(new NotificacionDTO(notificacion));
            }
        }
        return new Respuesta("notificaciones", lista);
    }

    @PostMapping("/borrarNotificaciones")
    public List<Respuesta> borrarNotificaciones(){
        if (propietario != null) {
            propietario.eliminarNotificaciones();
        }
        return Respuesta.lista(new Respuesta("notificacionesBorradas", true), notificaciones());
    }
    
    @Override
    public void actualizar(Object evento, Observable origen) {
        if(evento.equals(Propietario.Eventos.cambioListaTransitos)){
            conexionNavegador.enviarJSON(Respuesta.lista(propietario(), transitos(), vehiculos()));
        }else if(evento.equals(Propietario.Eventos.cambioListaVehiculos)){
            conexionNavegador.enviarJSON(Respuesta.lista(vehiculos()));
        }else if(evento.equals(Propietario.Eventos.cambiosListaBonificaciones)){
            conexionNavegador.enviarJSON(Respuesta.lista(bonificaciones()));
        }else if(evento.equals(Propietario.Eventos.cambioListaNotificaciones)){
            conexionNavegador.enviarJSON(Respuesta.lista(notificaciones()));
        }else if(evento.equals(Propietario.Eventos.cambioEstado)){
            conexionNavegador.enviarJSON(Respuesta.lista(propietario()));
            
            if (!propietario.puedeLoguearse()) {
                conexionNavegador.enviarJSON(Respuesta.lista(new Respuesta("cerrarSesion", "Su cuenta ha sido " + propietario.getEstado().getNombre().toLowerCase() + ". Debe cerrar sesi\u00f3n.")));
            }
        }
    }
}
