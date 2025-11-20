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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import com.example.obligatorio.ConexionNavegador;
import com.example.obligatorio.dto.NombreDTO;
import com.example.obligatorio.dto.PropietarioDTO;
import com.example.obligatorio.modelo.Fachada;
import com.example.obligatorio.modelo.PeajeException;
import com.example.obligatorio.modelo.Propietario;

import observador.Observable;
import observador.Observador;

@RestController
@RequestMapping("/cambiarEstado")
@Scope("session")
public class ControladorEstado implements Observador {
    
    private Propietario propietario;
    private final ConexionNavegador conexionNavegador;

    public ControladorEstado(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE();
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista() {
        return Respuesta.lista(estadosDisponibles());
    }

    @PostMapping("/vistaCerrada")
    public void salir() {
        if (propietario != null) {
            propietario.quitarObservador(this);
        }
    }

    @PostMapping("/buscarPropietario")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) throws PeajeException {
        
        if (propietario != null) {
            propietario.quitarObservador(this);
        }
        
        propietario = (Propietario) Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
        if (propietario == null) {
            throw new PeajeException("No existe el propietario");
        }
        
        propietario.agregarObservador(this);
        return Respuesta.lista(propietarioInfo(),estadoActual(),estadosDisponibles());
    }

    @PostMapping("/cambiarEstado")
    public List<Respuesta> cambiarEstado(@RequestParam int posEstado) throws PeajeException {
        if (propietario == null) {
            throw new PeajeException("Debe buscar un propietario primero");
        }

        switch (posEstado) {
            case 0: propietario.habilitado(); break;
            case 1: propietario.deshabilitado(); break;
            case 2: propietario.suspendido(); break;
            case 3: propietario.penalizado(); break;
            default: throw new PeajeException("Estado no válido");
        }

        return Respuesta.lista(estadoActual(), new Respuesta("mensaje", "Se ha cambiado tu estado en el sistema. Tu estado actual o el anterior permiten registrar notificaciones"));
    }

    private Respuesta propietarioInfo() {
        return new Respuesta("propietario", new PropietarioDTO(propietario));
    }

    private Respuesta estadoActual() {
        return new Respuesta("estadoActual", propietario.getEstado().getNombre());
    }

    private Respuesta estadosDisponibles() {
        List<NombreDTO> dtos = new ArrayList<NombreDTO>();
        dtos.add(new NombreDTO("Activo"));
        dtos.add(new NombreDTO("Deshabilitado"));
        dtos.add(new NombreDTO("Suspendido"));
        dtos.add(new NombreDTO("Penalizado"));
        
        return new Respuesta("estados", dtos);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if(evento.equals(Propietario.Eventos.cambioEstado)){
            conexionNavegador.enviarJSON(Respuesta.lista(estadoActual()));
        }
    }
}