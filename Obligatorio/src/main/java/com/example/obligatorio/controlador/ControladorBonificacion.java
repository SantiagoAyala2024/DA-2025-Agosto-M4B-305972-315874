package com.example.obligatorio.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.obligatorio.ConexionNavegador;
import com.example.obligatorio.dto.BonificacionDTO;
import com.example.obligatorio.dto.NombreDTO;
import com.example.obligatorio.modelo.Fachada;
import com.example.obligatorio.modelo.Propietario;
import com.example.obligatorio.modelo.Administrador;
import com.example.obligatorio.modelo.Asignacion;
import com.example.obligatorio.modelo.PeajeException;
import com.example.obligatorio.modelo.Puesto;
import com.example.obligatorio.modelo.Bonificacion;

import observador.Observable;
import observador.Observador;

@RestController
@RequestMapping("/bonificacion")
@Scope("session")
public class ControladorBonificacion implements Observador {
    
    private Propietario propietarioActual;
    private Administrador administrador;
    private final ConexionNavegador conexionNavegador;

    public ControladorBonificacion(@Autowired ConexionNavegador conexionNavegador){
        this.conexionNavegador = conexionNavegador;
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE();
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> vistaConectada(@SessionAttribute(name = "usuarioAdmin") Administrador admin) {
        administrador = admin;
        
        return Respuesta.lista(bonificaciones(),puestos(), new Respuesta("habilitarIngreso", true));
    }

    private Respuesta puestos() {
        ArrayList<Puesto> puestos = Fachada.getInstancia().getPuestos();
        ArrayList<NombreDTO> lista = new ArrayList<>();
        for(Puesto p : puestos) {
            lista.add(new NombreDTO(p.getNombre()));
        }
        return new Respuesta("puestos", lista);
    }
    
    @PostMapping("/buscarPropietario")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) throws PeajeException {
        Propietario propietario = (Propietario) Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
        if (propietario == null) {
            throw new PeajeException("No existe el propietario");
        }

        if (propietarioActual != null) {
            propietarioActual.quitarObservador(this);
        }

        propietarioActual = propietario;
        propietarioActual.agregarObservador(this);
    
        return Respuesta.lista(
            new Respuesta("nombrePropietario", propietario.getNombreCompleto()),
            new Respuesta("estadoPropietario", propietario.getEstado().getNombre()),
            bonificacionesAsignadas()
        );
    }

    private Respuesta bonificaciones(){
        return new Respuesta("bonificaciones", Fachada.getInstancia().getBonificaciones());
    }
    
    private Respuesta bonificacionesAsignadas() {
        ArrayList<BonificacionDTO> lista = new ArrayList<>();
        if (propietarioActual != null) {
            for (Asignacion asignacion : propietarioActual.getAsignaciones()) {
                lista.add(new BonificacionDTO(asignacion));
            }
        }
        return new Respuesta("bonificacionesAsignadas", lista);
    }

    @PostMapping("/asignarBonificacion")
    public List<Respuesta> asignarBonificacion(@RequestParam int bonificacionId, @RequestParam int puestoId) throws PeajeException {
        
        if (propietarioActual == null) {
            throw new PeajeException("Debe buscar un propietario primero");
        }

        propietarioActual.asignarBonificacion();

        ArrayList<Bonificacion> bonificaciones = Fachada.getInstancia().getBonificaciones();
        ArrayList<Puesto> puestos = Fachada.getInstancia().getPuestos();

        if (bonificacionId < 0 || bonificacionId >= bonificaciones.size()) {
            throw new PeajeException("Debe especificar una bonificación");
        }

        if (puestoId < 0 || puestoId >= puestos.size()) {
            throw new PeajeException("Debe especificar un puesto");
        }

        Bonificacion bonificacion = bonificaciones.get(bonificacionId);
        Puesto puesto = puestos.get(puestoId);

        propietarioActual.tieneAsignacionPuesto(puesto);

        Asignacion asignacion = new Asignacion(bonificacion, puesto, propietarioActual, this.administrador, java.time.LocalDate.now());
        propietarioActual.agregarAsignacion(asignacion);

        return Respuesta.lista(bonificacionesAsignadas());
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if(evento.equals(Propietario.Eventos.cambiosListaBonificaciones)){
            conexionNavegador.enviarJSON(Respuesta.lista(bonificacionesAsignadas()));
        }
    }
}
