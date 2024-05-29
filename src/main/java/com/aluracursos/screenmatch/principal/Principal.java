package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.modelos.DatosEpisodios;
import com.aluracursos.screenmatch.modelos.DatosSerie;
import com.aluracursos.screenmatch.modelos.DatosTemporadas;
import com.aluracursos.screenmatch.modelos.Episodios;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConversorDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConversorDatos conversorDatos = new ConversorDatos();
    private Scanner scanner = new Scanner(System.in);
    private final String URL_API = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=724175fd";
    private List<DatosSerie> datosSeries = new ArrayList<>();


    public void mostrarMenu(){

        var opcion = -1;

        while(opcion != 0){

            var menu = """
                    1- Buscar series
                    2- Buscar episodios
                    3- Mostrar series buscadas
                    
                    0- Salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){

                case 1-> buscarSerieWeb();
                case 2-> buscarEpisodioPorSerie();
                case 3-> mostrarSeriesBuscadas();
                case 0-> {System.out.println("Cerrando la aplicación");
                break;}
                default -> System.out.println("Opción inválida");
            }

        }


    }

    private DatosSerie getDatoSerie(){
        System.out.println("Escribe el nombre de la serie que deseas buscar:");
        var nombreSerie = scanner.nextLine();
        var json = consumoAPI.obtenerDatos(URL_API + nombreSerie.replace(" ","+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversorDatos.obtenerDatos(json,DatosSerie.class);
        return datos;

    }

    private void buscarEpisodioPorSerie(){
        DatosSerie datos = getDatoSerie();
        List <DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {

            var json = consumoAPI.obtenerDatos(URL_API+datos.titulo().replace(" ","+")+API_KEY);
            DatosTemporadas datosTemporadas = conversorDatos.obtenerDatos(json,DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        temporadas.forEach(System.out::println);
    }

    private void buscarSerieWeb(){
        DatosSerie datos = getDatoSerie();
        datosSeries.add(datos);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas(){
        datosSeries.forEach(System.out::println);

    }







}
