
package com.example.obligatorio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Component
@Scope("session")
public class ConexionNavegador  {

    private SseEmitter conexionSSE;
    private static final ObjectMapper objectMapper;
    
    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void conectarSSE() {
        if (conexionSSE != null) { //si hay hay una conexion la cierro
            cerrarConexion();
        }
        long timeOut = 30 * 60 * 1000; //30 minutos de timeOut (igual al valor por defecto de la sesion)
        conexionSSE = new SseEmitter(timeOut);
        
    }
    public void cerrarConexion(){
        try{
            if(conexionSSE!=null){
                conexionSSE.complete();
                conexionSSE = null;
            }
        }catch(Exception e){}
    }

    public SseEmitter getConexionSSE() {
        return conexionSSE;
    }
     
    public void enviarJSON(Object informacion) {
        try {
            String json = objectMapper.writeValueAsString(informacion);
            System.out.println("[SSE] Enviando JSON: " + json.substring(0, Math.min(100, json.length())));
            enviarMensaje(json);
   
        } catch (JsonProcessingException e) {
            System.out.println("Error al convertir a JSON:" + e.getMessage());
           
        }
   
    }
    public void enviarMensaje(String mensaje) {
       
	if(conexionSSE==null) {
            System.out.println("[SSE] No hay conexión SSE activa");
            return;
        }
        try {	
	     	conexionSSE.send(mensaje);
            System.out.println("[SSE] Mensaje enviado exitosamente");						
	} catch (Throwable e) {
            System.out.println("Error al enviar mensaje:" + e.getMessage());
            cerrarConexion();
	}
    }
    
  

}
