package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.modulos.DatosEpisodios;
import com.aluracursos.screenmatch.modulos.DatosSerie;
import com.aluracursos.screenmatch.modulos.DatosTemporadas;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConversorDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConversorDatos conversorDatos = new ConversorDatos();
    private Scanner scanner = new Scanner(System.in);
    private final String URL_API = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=724175fd";




    public void mostrarMenu(){
        System.out.println("Indique en nombre de la serie que desea buscar");
        String busquedaSerieUsuario = scanner.nextLine();
        var json = consumoAPI.obtenerDatos(URL_API+busquedaSerieUsuario.replace(" ","+")+API_KEY);
        System.out.println(json);
        var datosSerie = conversorDatos.obtenerDatos(json,DatosSerie.class);
        System.out.println("Dato Serie con formato = "+datosSerie);


        List<DatosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= datosSerie.totalDeTemporadas() ; i++) {

            json = consumoAPI.obtenerDatos(URL_API +busquedaSerieUsuario.replace(" ","+")+ "&Season=" + i + API_KEY);
            var datosTemporada = conversorDatos.obtenerDatos(json,DatosTemporadas.class);
            temporadas.add(datosTemporada);

        }
        temporadas.forEach(System.out::println);

        //imprime episodio por temporadas

        for (int i = 0; i < datosSerie.totalDeTemporadas(); i++) {
           /* System.out.println("entrando a loop for para mostrar nombres temp");*/
            System.out.println("\nTemporada " + (i+1) + "\n******************");

            List<DatosEpisodios> episodiosTemporada = temporadas.get(i).episodios();

            for (int j = 0; j < episodiosTemporada.size() ; j++) {
                System.out.println("Episodio "+(j+1)+" "+episodiosTemporada.get(j).titulo());
            }
        }

        System.out.println("desde expresiones lamda");
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


    }

}
