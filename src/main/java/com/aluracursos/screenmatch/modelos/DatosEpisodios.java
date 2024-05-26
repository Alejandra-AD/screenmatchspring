package com.aluracursos.screenmatch.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosEpisodios(
        @JsonAlias("Title") String titulo,
        @JsonAlias("Episode") Integer numeroEpisodio ,
        @JsonAlias ("imdbRating") String evaluacion,
        @JsonAlias("Released") String fechaDeLanzamiento)

 {

  /*public String toString(){
   return "(Título: "+ titulo +", "+"Nro episodio: " + numeroEpisodio + ", "
           + "Evaluación: " + evaluacion + ", " + "Fecha de lanzamiento: "
           + fechaDeLanzamiento+ ")";
  }*/
}
