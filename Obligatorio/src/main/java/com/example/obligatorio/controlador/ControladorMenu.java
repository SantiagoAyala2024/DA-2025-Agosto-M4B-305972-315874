package com.example.obligatorio.controlador;

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
import com.example.obligatorio.modelo.Administrador;
import com.example.obligatorio.modelo.Fachada;
import observador.Observable;
import observador.Observador;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/menu")
@Scope("session")
public class ControladorMenu implements Observador {
 
    private final ConexionNavegador conexionNavegador;
    
    public ControladorMenu(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }
    
    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        Fachada.getInstancia().agregarObservador(this);
        return conexionNavegador.getConexionSSE(); 
    }
    
    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuarioAdmin") Administrador admin){
        return Respuesta.lista(new Respuesta("admin", admin.getNombreCompleto()));
    }
    
    @PostMapping("/vistaCerrada")
    public void salir(HttpSession sesionHttp){
        Fachada.getInstancia().quitarObservador(this);
        sesionHttp.removeAttribute("usuarioAdmin");
    }
    
    @Override
    public void actualizar(Object evento, Observable origen) {
        
    }
}
