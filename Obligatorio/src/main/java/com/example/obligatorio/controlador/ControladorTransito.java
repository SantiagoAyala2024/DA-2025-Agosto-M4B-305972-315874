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
import com.example.obligatorio.dto.NombreDTO;
import com.example.obligatorio.dto.TransitoDTO;
import com.example.obligatorio.dto.TarifaDTO;
import com.example.obligatorio.modelo.Administrador;
import com.example.obligatorio.modelo.Fachada;
import com.example.obligatorio.modelo.PeajeException;
import com.example.obligatorio.modelo.Puesto;
import com.example.obligatorio.modelo.Transito;
import com.example.obligatorio.modelo.Vehiculo;

import observador.Observable;
import observador.Observador;

import com.example.obligatorio.modelo.Tarifa;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/transito")
@Scope("session")
public class ControladorTransito implements Observador {
    
    private Transito transito;
    private ArrayList<Puesto> puestos;
    private Administrador administrador;
    private final ConexionNavegador conexionNavegador;

    public ControladorTransito(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> vistaConectada(@SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin) throws PeajeException {
        
        if (admin == null) {
            throw new PeajeException("Debe iniciar sesión como administrador");
        }

        if (administrador != null) {
            administrador.quitarObservador(this);
        }
        
        administrador = admin;
        administrador.agregarObservador(this);
       
        return Respuesta.lista(puestos(), new Respuesta("habilitarIngreso", true));
    }

    @PostMapping("/emularTransito")
    public List<Respuesta> emularTransito(@RequestParam int posPuesto, @RequestParam String matricula, @RequestParam String fechaHora) throws PeajeException{
        if (posPuesto < 0 || posPuesto >= Fachada.getInstancia().getPuestos().size()) {
            throw new PeajeException("El puesto seleccionado no existe");
        }
        Puesto puesto = Fachada.getInstancia().getPuestos().get(posPuesto);
        Vehiculo vehiculo = Fachada.getInstancia().buscarVehiculoPorMatricula(matricula);
        if (vehiculo == null) {
            throw new PeajeException("No existe un vehículo con la matrícula " + matricula);
        }
        
        vehiculo.getPropietario().registrarTransito();
        
        LocalDateTime fechaTransito = LocalDateTime.parse(fechaHora, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        
        this.transito = new Transito(vehiculo, puesto, fechaTransito);
        vehiculo.agregarTransito(this.transito);
        return Respuesta.lista(transitos(), new Respuesta("limpiarEntradas", true));
    }

    @PostMapping("/tarifasPuesto")
    public List<Respuesta> tarifasPuesto(@RequestParam int posPuesto){
        Puesto puesto = Fachada.getInstancia().getPuestos().get(posPuesto);
        ArrayList<TarifaDTO> lista = new ArrayList<>();
        if(puesto != null){
            for(Tarifa t : puesto.getTarifas()){
                lista.add(new TarifaDTO(t));
            }
        }
        return Respuesta.lista(new Respuesta("tarifas", lista));
    }

    private Respuesta transitos(){
        return new Respuesta("transito", new TransitoDTO(transito));
    }

    private Respuesta puestos() {
        ArrayList<NombreDTO> lista = new ArrayList<>();
        puestos = new ArrayList<>(Fachada.getInstancia().getPuestos());
        for(Puesto p: puestos){
            lista.add(new NombreDTO(p.getNombre()));
        }
        return new Respuesta("puestos", lista);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        conexionNavegador.enviarJSON(puestos());
    }

}
