package com.example.obligatorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.obligatorio.modelo.DatosPrueba;

@SpringBootApplication
public class ObligatorioApplication {

	public static void main(String[] args) {
		DatosPrueba.cargar();
		SpringApplication.run(ObligatorioApplication.class, args);
	}

}
