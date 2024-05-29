package com.aluracursos.screenmatch.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Optional;
import java.util.OptionalDouble;

public class Serie {
     private String titulo;
     private Integer totalDeTemporadas;
     private Double evaluacion;
     private String poster;
     private Categoria genero;
     private String actores;
     private String sinopsis;

     public Serie (DatosSerie datosSerie){
          this.titulo = datosSerie.titulo();
          this.totalDeTemporadas = datosSerie.totalDeTemporadas();
          this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(Double.valueOf(0));
          this.poster = datosSerie.poster();
          this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
          this.actores = datosSerie.actores();
          this.sinopsis = datosSerie.sinopsis();


     }

     public String toString(){
          return "Titulo: "+ titulo
                  + "\nTotal de temporadas: "+ totalDeTemporadas
                  + "\nEvaluación: " + evaluacion
                  + "\nPoster: "   + poster
                  + "\nGenero: "   + genero
                  + "\nActores: "  + actores
                  + "\nSinopsis: " + sinopsis;
     }
}
