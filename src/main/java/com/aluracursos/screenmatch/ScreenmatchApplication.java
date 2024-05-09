package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.modulos.DatosSerie;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConversorDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoApi = new ConsumoAPI();
		var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=yellowjackets&Season=1&apikey=724175fd");
		System.out.println(json);
		ConversorDatos conversorDatos = new ConversorDatos();
		var datos = conversorDatos.obtenerDatos(json, DatosSerie.class);
		System.out.println("datos = " + datos);

	}
}
