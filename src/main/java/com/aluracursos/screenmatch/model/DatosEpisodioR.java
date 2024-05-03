package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Clase para mapear los datos por episodio
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEpisodioR(
        @JsonAlias("Title") String tituloEpisodio,
        @JsonAlias("Episode") Integer numeroEpisodio,
        @JsonAlias("imdbRating") String evaluacionEpisodio,
        @JsonAlias("Released") String fechaLanzamientoEpisodio){

}
