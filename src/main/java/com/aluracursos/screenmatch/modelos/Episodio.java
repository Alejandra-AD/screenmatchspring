package com.aluracursos.screenmatch.modelos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {

    private Integer temporada;
    private String titulo;
    private  Integer numeroEpisodio;
    private Double evaluacion;
    private LocalDate fechaLanzamiento;

    public Episodio(Integer numero ,DatosEpisodios d) {
        this.temporada = numero;
        this.titulo = d.titulo();
        this.numeroEpisodio = d.numeroEpisodio();
        try {
            this.evaluacion = Double.valueOf(d.evaluacion());
        }
        catch(NumberFormatException e){
            this.evaluacion = 0.0;
        }
        try{
            this.fechaLanzamiento = LocalDate.parse(d.fechaDeLanzamiento());

        }catch (DateTimeParseException e){
            this.fechaLanzamiento = null;
        }
    }

    public Integer getTemporada() {
        return temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public String toString(){

        return
                "\ntemporada: " + temporada +
                "\ntitulo: " + titulo +
                "\nnumero de episodio: "+ numeroEpisodio +
                "\nevaluación: " + evaluacion +
                "\nfecha de lanzamiento: " + fechaLanzamiento;

    }
}
