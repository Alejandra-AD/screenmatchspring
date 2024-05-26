package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.modelos.DatosEpisodios;
import com.aluracursos.screenmatch.modelos.DatosSerie;
import com.aluracursos.screenmatch.modelos.DatosTemporadas;
import com.aluracursos.screenmatch.modelos.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConversorDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
     /*   System.out.println(json);*/
        var datosSerie = conversorDatos.obtenerDatos(json,DatosSerie.class);
       /* System.out.println("Dato Serie con formato = "+datosSerie);*/


        List<DatosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= datosSerie.totalDeTemporadas() ; i++) {

            json = consumoAPI.obtenerDatos(URL_API +busquedaSerieUsuario.replace(" ","+")+ "&Season=" + i + API_KEY);
            var datosTemporada = conversorDatos.obtenerDatos(json,DatosTemporadas.class);
            temporadas.add(datosTemporada);

        }
        /*temporadas.forEach(System.out::println);*/

        //imprime episodio por temporadas

       /* for (int i = 0; i < datosSerie.totalDeTemporadas(); i++) {
           *//* System.out.println("entrando a loop for para mostrar nombres temp");*//*
            System.out.println("\nTemporada " + (i+1) + "\n******************");

            List<DatosEpisodios> episodiosTemporada = temporadas.get(i).episodios();

            for (int j = 0; j < episodiosTemporada.size() ; j++) {
                System.out.println("Episodio "+(j+1)+" "+episodiosTemporada.get(j).titulo());
            }
        }

        System.out.println("desde expresiones lamda");
        int i = 1;
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));*/


        //convertir info en una lista datos episodio

        List<DatosEpisodios> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        //top 5 episodios

        /*System.out.println();
        datosEpisodios.stream()
                .sorted(Comparator.comparing(DatosEpisodios::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);*/


        //Convierte los datos a una lista del tipo de datos Episodio
        List <Episodio> episodios = temporadas.stream()
                .flatMap(t ->  t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(),d)))
                .collect(Collectors.toList());

       /* episodios.forEach(System.out::println);*/

        //Búsqueda de episodios a partir de x año

      /*  System.out.println("Por favor indique el año de estreno de la temporada para ver los episodios:");
        var fecha = scanner.nextInt();
        scanner.nextLine();
        LocalDate fechaBusqueda = LocalDate.of(fecha,1,1);
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e-> e.getFechaLanzamiento() != null && e.getFechaLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada()+
                                "\nEpisodio: " + e.getTitulo() +
                                "\nFecha de lanzamiento: " + e.getFechaLanzamiento().format(dft)
                ));*/

        //búsqueda de episodios por parte de su nombre
        /*System.out.println("Escriba el nombre del episodio");
        var nombreParteEpisodio = scanner.nextLine();

        Optional<Episodio> episodioParteNombre = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(nombreParteEpisodio.toUpperCase()))
                .findFirst();

        if (episodioParteNombre.isPresent()){
            System.out.println("Titulo encontrado: "+ episodioParteNombre.get().getTitulo()
                    + " ,Temporada: " + episodioParteNombre.get().getTemporada()
                    + " ,Número de episodio:  " + episodioParteNombre.get().getNumeroEpisodio());
        } else {
            System.out.println("Episodio no encontrado");

        }
        */

        //
        Map< Integer,Double > evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println("\nSerie : "+ datosSerie.titulo() + "\nEvaluación por temporada" + evaluacionesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("\n"+ est);


    }

}
