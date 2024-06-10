package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.modelos.*;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConversorDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConversorDatos conversorDatos = new ConversorDatos();
    private Scanner scanner = new Scanner(System.in);
    private final String URL_API = "https://www.omdbapi.com/?t=";

    private final String API_KEY ="&apikey=724175fd";

    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie>series;
    private Optional <Serie> serieBuscadaDB;

    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }


    public void mostrarMenu(){

        var opcion = -1;

        while(opcion != 0){

            var menu = """
                    
                    *******************SCREENMATCH*******************
                                  Selecciones una opción
                    
                    1- Buscar series
                    2- Buscar episodios
                    3- Mostrar series buscadas
                    4- Buscar series por titulo
                    5- Top 5 mejores series
                    6- Buscar Series por categoría
                    7- filtrar series por temporadas y evaluación
                    8- Buscar episodios por titulo
                    9- Top 5 episodios por Serie
                    
                    0- Salir
                   
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){

                case 1-> buscarSerieWeb();
                case 2-> buscarEpisodioPorSerie();
                case 3-> mostrarSeriesBuscadas();
                case 4-> buscarSeriesPorTitulo();
                case 5-> buscarTop5Series();
                case 6-> buscarSeriesPorCategoria();
                case 7-> filtrarSeriesPorTemporadaYEvaluacion();
                case 8-> buscarEpisodiosPorTitulo();
                case 9-> buscarTop5Episodios();
                case 0-> {System.out.println("Cerrando la aplicación");}
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
        System.out.println("\nLISTA DE SERIE GUARDADAS");
        mostrarSeriesBuscadas();
        System.out.println("\nPara ver la liste de episodios de su serie, por favor indique el nombre de la serie:");
        var serieBuscada = scanner.nextLine();

        Optional<Serie>serie =series.stream()
                .filter(s->s.getTitulo().toLowerCase().equals(serieBuscada.toLowerCase()))
                .findFirst();

        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumoAPI.obtenerDatos(URL_API + serieEncontrada.getTitulo().replace(" ", "+") + "&season="+i+ API_KEY);
                DatosTemporadas datosTemporadas = conversorDatos.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporadas);
            }
            List <Episodio> episodios = temporadas.stream()
                    .flatMap(d ->  d.episodios().stream()
                            .map(e -> new Episodio(d.numeroTemporada(),e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
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
        System.out.println("\n****** Lista de series buscadas ******");
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriesPorTitulo(){
        System.out.println("\n****** Filtro de serie por título ******");
        System.out.println("\nEscribe el nombre de la serie que deseas buscar:");
        var nombreSerie = scanner.nextLine();
        serieBuscadaDB = repositorio.findByTituloContainsIgnoreCase(nombreSerie);
        if(serieBuscadaDB.isPresent()){
            System.out.println("La serie buscada es: " + serieBuscadaDB);
        }else {
            System.out.println("Serie no encontrada");
        }
    }

    private void buscarTop5Series(){
        System.out.println("\n****** Top 5 series ******");
        List<Serie> top5SeriesDB= repositorio.findTop5ByOrderByEvaluacionDesc();
        top5SeriesDB.forEach(s-> System.out.println(s.getTitulo()));
    }
    private void buscarSeriesPorCategoria(){
        System.out.println("\n****** Búsqueda de serie por categoría/género ******");
        System.out.println("Indique el género de la serie que quiere buscar: ");
        var genero = scanner.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las series de la categoría " + genero);
        seriesPorCategoria.forEach(System.out::println);

    }

    private void filtrarSeriesPorTemporadaYEvaluacion(){
        System.out.println("\n****** Filtro de serie por nro de temporadas y evaluación ******");
        System.out.println("Indique el total de temporadas");
        var totalTemporadas = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Indique desde que evaluación");
        var evaluacion = scanner.nextDouble();
        scanner.nextLine();

        var serieTemporadasEvaluacionBuscada = repositorio.seriesPorTemporadaYEvaluacion(totalTemporadas,evaluacion);

        serieTemporadasEvaluacionBuscada.forEach(d-> System.out.println("\nSerie: " + d.getTitulo().toUpperCase()
        + "\nTotal de temporadas: " + d.getTotalDeTemporadas()
        + "\nEvaluación: "+ d.getEvaluacion()));
    }
    private void buscarEpisodiosPorTitulo(){
        System.out.println("\n****** Búsqueda de episodio por su título ******");
        System.out.println("Escriba el titulo del episodio que desea buscar:");
        var nombreEpisodio = scanner.nextLine();
        var busquedaEpisodio = repositorio.episodiosPorNombre(nombreEpisodio);
            busquedaEpisodio.forEach(e-> System.out.println("\nEpisodio encontrado: " + e.getTitulo()
                +"\nNúmero de episodio: " + e.getNumeroEpisodio()
                +"\nTemporada: " + e.getTemporada()
                + "\nSerie: " + e.getSeries().getTitulo()));

    }
    private void buscarTop5Episodios(){
        System.out.println("\n****** Búsqueda de los top 5 episodios por serie ******");
        buscarSeriesPorTitulo();
        if (serieBuscadaDB.isPresent()){
            Serie serie = serieBuscadaDB.get();
            List <Episodio> topEpisodios = repositorio.top5Episodios(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("\nSerie: %s - Temporada %s - Nombre del capitulo%s - Episodio %s - Evaluación %s\n",
                           e.getSeries().getTitulo(), e.getTemporada(),e.getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
        }
    }

}
