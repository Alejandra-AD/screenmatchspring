package com.aluracursos.screenmatch.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalDeTemporadas,
        @JsonAlias("imdbRating") String evaluacion,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Gender") String genero,
        @JsonAlias("Actors")String actores,
        @JsonAlias("Plot") String sinopsis) {


       /* @Override
        public String toString(){
                return "\n-Serie: "+titulo+"\n-Total de temporadas: "+totalDeTemporadas+"\n-Evaluación: "+evaluacion;
        }*/

}



