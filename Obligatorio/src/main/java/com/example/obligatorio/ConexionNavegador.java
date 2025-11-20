
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
        if (conexionSSE != null) { 
            cerrarConexion();
        }
        long timeOut = 30 * 60 * 1000;
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
            enviarMensaje(json);
   
        } catch (JsonProcessingException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public void enviarMensaje(String mensaje) {
       
	if(conexionSSE==null) {
            System.out.println("No hay conexión SSE activa");
            return;
        }
        try {	
	     	conexionSSE.send(mensaje);
            System.out.println("Mensaje enviado exitosamente");						
	} catch (Throwable e) {
            System.out.println("Error" + e.getMessage());
            cerrarConexion();
	}
    }
    
  

}
