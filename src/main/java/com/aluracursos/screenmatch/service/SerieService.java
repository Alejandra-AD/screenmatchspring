package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.dto.SerieDTO;
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
}
