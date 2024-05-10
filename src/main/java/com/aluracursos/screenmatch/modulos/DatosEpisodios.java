package com.aluracursos.screenmatch.modulos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosEpisodios(
        @JsonAlias("Title") String titulo,
        @JsonAlias("Episode") Integer numeroEpisodio ,
        @JsonAlias ("imdbRating") String evaluacion,
        @JsonAlias("Released") String fechaDeLanzamiento)

 {
}
