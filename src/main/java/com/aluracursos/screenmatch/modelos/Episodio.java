package com.aluracursos.screenmatch.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
@Entity
@Table(name = "episodios")

public class Episodio {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Integer temporada;
    private String titulo;
    private  Integer numeroEpisodio;
    private Double evaluacion;
    private LocalDate fechaLanzamiento;
    @ManyToOne
    private Serie serie;

    public Episodio(){};

    public Episodio(Integer numero , DatosEpisodios d) {
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

    public Serie getSeries() {
        return serie;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
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
