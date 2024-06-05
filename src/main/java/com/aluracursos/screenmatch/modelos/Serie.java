package com.aluracursos.screenmatch.modelos;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;
@Entity
@Table(name = "series")

public class Serie {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)//auto genera el valor para la id

     private Long id;
     @Column(unique = true)
     private String titulo;
     private Integer totalDeTemporadas;
     private Double evaluacion;
     private String poster;
     @Enumerated(EnumType.STRING)
     private Categoria genero;
     private String actores;
     private String sinopsis;
     @OneToMany(mappedBy = "serie")
     private List<Episodio>episodios;

     public Serie(){};

     public Serie (DatosSerie datosSerie){
          this.titulo = datosSerie.titulo();
          this.totalDeTemporadas = datosSerie.totalDeTemporadas();
          this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(Double.valueOf(0));
          this.poster = datosSerie.poster();
          this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
          this.actores = datosSerie.actores();
          this.sinopsis = datosSerie.sinopsis();

     }

     public String getTitulo() {
          return titulo;
     }

     public void setTitulo(String titulo) {
          this.titulo = titulo;
     }

     public Integer getTotalDeTemporadas() {
          return totalDeTemporadas;
     }

     public void setTotalDeTemporadas(Integer totalDeTemporadas) {
          this.totalDeTemporadas = totalDeTemporadas;
     }

     public Double getEvaluacion() {
          return evaluacion;
     }

     public void setEvaluacion(Double evaluacion) {
          this.evaluacion = evaluacion;
     }

     public String getPoster() {
          return poster;
     }

     public void setPoster(String poster) {
          this.poster = poster;
     }

     public Categoria getGenero() {
          return genero;
     }

     public void setGenero(Categoria genero) {
          this.genero = genero;
     }

     public String getActores() {
          return actores;
     }

     public void setActores(String actores) {
          this.actores = actores;
     }

     public String getSinopsis() {
          return sinopsis;
     }

     public void setSinopsis(String sinopsis) {
          this.sinopsis = sinopsis;
     }

     public String toString(){
          return "\nTitulo: " + titulo
                  + "\nTotal de temporadas: "+ totalDeTemporadas
                  + "\nEvaluación: " + evaluacion
                  + "\nPoster: "   + poster
                  + "\nGenero: "   + genero
                  + "\nActores: "  + actores
                  + "\nSinopsis: " + sinopsis;
     }
}
