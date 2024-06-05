package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.modelos.*;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConversorDatos;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConversorDatos conversorDatos = new ConversorDatos();
    private Scanner scanner = new Scanner(System.in);
    private final String URL_API = "https://www.omdbapi.com/?t=";

    @Value("${omdb.api.key}")
    private String API_KEY;
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie>series;

    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }


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
        System.out.println("LISTA DE SERIE GUARDADAS");
        mostrarSeriesBuscadas();
        System.out.println("Para ver la liste de episodios de su serie, por favor indique el nombre de la serie:");
        var serieBuscada = scanner.nextLine();

        Optional<Serie>serie =series.stream()
                .filter(s->s.getTitulo().toLowerCase().equals(serieBuscada.toLowerCase()))
                .findFirst();

        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();
            for (int i = 1; i < serieEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumoAPI.obtenerDatos(URL_API + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + API_KEY);
                DatosTemporadas datosTemporadas = conversorDatos.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporadas);
            }
            List <Episodio> episodios = temporadas.stream()
                    .flatMap(t ->  t.episodios().stream()
                            .map(d -> new Episodio(t.numeroTemporada(),d)))
                    .collect(Collectors.toList());

            temporadas.forEach(System.out::println);

        }else{
            System.out.println("Serie no encontrada. Ingrese un nombre de serie disponible");
        }





    }

    private void buscarSerieWeb(){
        DatosSerie datos = getDatoSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas(){
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }







}
