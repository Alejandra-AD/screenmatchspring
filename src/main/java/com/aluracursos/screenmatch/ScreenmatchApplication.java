package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.modulos.DatosEpisodios;
import com.aluracursos.screenmatch.modulos.DatosSerie;
import com.aluracursos.screenmatch.modulos.DatosTemporadas;
import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConversorDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("desde menu");
		Principal appPrincipal = new Principal();
		appPrincipal.mostrarMenu();

	}
}
