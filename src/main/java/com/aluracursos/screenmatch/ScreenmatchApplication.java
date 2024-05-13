package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.modulos.DatosEpisodios;
import com.aluracursos.screenmatch.modulos.DatosSerie;
import com.aluracursos.screenmatch.modulos.DatosTemporadas;
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
		ConsumoAPI consumoApi = new ConsumoAPI();
		var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=fallout&apikey=724175fd");
		System.out.println(json);
		ConversorDatos conversorDatos = new ConversorDatos();
		var datos = conversorDatos.obtenerDatos(json, DatosSerie.class);
		System.out.println("datos = " + datos);

		json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=fallout&Season=1&Episode=1&apikey=724175fd");
		var datosCapitulo = conversorDatos.obtenerDatos(json, DatosEpisodios.class);
		System.out.println("datosCapitulo = " + datosCapitulo);

		List<DatosTemporadas>temporadas = new ArrayList<>();

		for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {

			json =consumoApi.obtenerDatos("https://www.omdbapi.com/?t=fallout&Season=" + i + "&apikey=724175fd");
			var datosTemporada = conversorDatos.obtenerDatos(json,DatosTemporadas.class);
			temporadas.add(datosTemporada);

		}
		temporadas.forEach(System.out::println);
		


	}
}
