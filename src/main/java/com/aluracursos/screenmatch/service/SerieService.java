package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.modelos.Categoria;
import com.aluracursos.screenmatch.modelos.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obtenerTodasLasSeries(){
        return convieteDatosSerieaSerieDTO(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convieteDatosSerieaSerieDTO(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO>obtenerLanzamientosMasRecientes(){
        return convieteDatosSerieaSerieDTO(repository.lanzamientosMasRecientes());
    }

    private List<SerieDTO> convieteDatosSerieaSerieDTO(List<Serie>serie){

       return serie.stream()
                .map(s->new SerieDTO(s.getId(),s.getTitulo(),s.getTotalDeTemporadas(),s.getEvaluacion(),s.getPoster()
                        ,s.getGenero(),s.getActores(),s.getSinopsis())).collect(Collectors.toList());
    }

    public SerieDTO obtenerSeriePorID(Long id) {

        Optional<Serie> serieID = repository.findByID(id);
        if (serieID.isPresent()){
            Serie s = serieID.get();
            return new SerieDTO(s.getId(),s.getTitulo(),s.getTotalDeTemporadas(),s.getEvaluacion(),s.getPoster()
                    ,s.getGenero(),s.getActores(),s.getSinopsis());
        }else{
            return null;
        }
    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serieID = repository.findByID(id);
        if (serieID.isPresent()){
            Serie s = serieID.get();
            return s.getEpisodios().stream()
                    .map(e->new EpisodioDTO(e.getTitulo(),e.getTemporada(),e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }else{
            return null;
        }
    }


    public List<EpisodioDTO> obtenerEpisodiosPorTemporada(Long id, Long numeroTemporada) {
        return repository.episodiosPorTemporada(id,numeroTemporada).stream()
                .map(e->new EpisodioDTO(e.getTitulo(),e.getTemporada(),e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesPorCategoria(String categoriaSeleccionada) {
        var categoria = Categoria.fromEspanol(categoriaSeleccionada);
        return repository.findByGenero(categoria).stream()
                .map(s-> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalDeTemporadas(),s.getEvaluacion(),s.getPoster()
                        ,s.getGenero(),s.getActores(),s.getSinopsis())).collect(Collectors.toList());
    }

    public List<EpisodioDTO> obtener5MejoresEpisodios(Long id) {
        Optional<Serie> SerieID = repository.findByID(id);
        if (SerieID.isPresent()){
            Serie serie = SerieID.get();
            return repository.top5Episodios(serie).stream()
                    .map(e->new EpisodioDTO(e.getTitulo(),e.getTemporada(),e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }else {
            return null;
        }
    }
}
