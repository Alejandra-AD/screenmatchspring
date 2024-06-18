package com.aluracursos.screenmatch.controller;


import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;

import com.aluracursos.screenmatch.modelos.Categoria;
import com.aluracursos.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService servicio;

    @GetMapping()
    public List<SerieDTO> obtenerTodasLasSeries() {
        return servicio.obtenerTodasLasSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5() {
        return servicio.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosMasRecientes() {
        return servicio.obtenerLanzamientosMasRecientes();
    }

    @GetMapping("/{id}")
    public SerieDTO obtenerSeriePorID(@PathVariable Long id) {
        return servicio.obtenerSeriePorID(id);
    }

    ;

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTodasLasTemporadas(@PathVariable Long id) {
        return servicio.obtenerTodasLasTemporadas(id);
    }

    ;

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obtenerEpisodiosPorTemporada(@PathVariable Long id, @PathVariable Long numeroTemporada) {
        return servicio.obtenerEpisodiosPorTemporada(id,numeroTemporada);
    };

    @GetMapping("/categoria/{categoriaSeleccionada}")//funcionando!!
    public List<SerieDTO>obtenerSeriesPorCategoria(@PathVariable String categoriaSeleccionada){
        return servicio.obtenerSeriesPorCategoria(categoriaSeleccionada);
    }

    @GetMapping ("/{id}/temporadas/top")//funcionando!!
    public List<EpisodioDTO>top5MejoresEpisodiosPorSerie(@PathVariable Long id){
        return servicio.obtener5MejoresEpisodios(id);
    }



}