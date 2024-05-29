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


    public void mostrarMenu(){




    }

}
