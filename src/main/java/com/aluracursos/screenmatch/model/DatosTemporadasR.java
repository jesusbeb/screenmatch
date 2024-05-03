package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//Clase record para mapear los datos por temporada
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosTemporadasR (
        @JsonAlias("Season") Integer numeroTemporada,
        @JsonAlias("Episodes") List<DatosEpisodioR> episodiosTemporada){
}
