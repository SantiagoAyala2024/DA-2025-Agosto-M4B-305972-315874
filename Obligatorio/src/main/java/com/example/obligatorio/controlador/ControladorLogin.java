package com.example.obligatorio.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.obligatorio.modelo.Administrador;
import com.example.obligatorio.modelo.Fachada;
import com.example.obligatorio.modelo.PeajeException;
import com.example.obligatorio.modelo.Propietario;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/acceso")
public class ControladorLogin {

    @PostMapping("/loginAdmin")
    public List<Respuesta> loginAdministrador(HttpSession sessionHttp, @RequestParam String cedula, @RequestParam String password) throws PeajeException{
        
        Administrador admin = Fachada.getInstancia().loginAdministrador(cedula, password);

        if (Fachada.getInstancia().adminConectado(admin)) {
            throw new PeajeException("Ud. Ya está logueado");
        }

        Fachada.getInstancia().agregarAdministradorConectado(admin);
        
        sessionHttp.setAttribute("usuarioAdmin", admin);
        return Respuesta.lista(new Respuesta("loginExitoso", "menu-admin.html" ));
    }

    @PostMapping("/loginProp")
    public List<Respuesta> loginPropietario(HttpSession sessionHttp, @RequestParam String cedula, @RequestParam String password) throws PeajeException{

        Propietario prop = Fachada.getInstancia().loginPropietario(cedula, password);
        
        if (!prop.puedeLoguearse()) {
            throw new PeajeException("No puede ingresar al sistema. Su cuenta está en estado: " + prop.getEstado().getNombre());
        }
        
        sessionHttp.setAttribute("usuarioProp", prop);
        return Respuesta.lista(new Respuesta("loginExitoso", "tablero-propietario.html"));
    }

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sessionHttp) {
        sessionHttp.invalidate();
        return Respuesta.lista(new Respuesta("logoutExitoso", "login-propietario.html"));
    }

    @PostMapping("/logoutAdmin")
    public List<Respuesta> logoutAdmin(HttpSession sessionHttp, @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin) {

        if (admin != null) {
            Fachada.getInstancia().quitarSesionAdmin(admin);
        }
        
        sessionHttp.invalidate();
        return Respuesta.lista(new Respuesta("logoutExitoso", "login-admin.html"));
    }
}
