package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Clase record para mapear los datos por serie
//Deserealizamos los datos del json o sea convertirlos en objetos de java, para eso
//usamos la biblioteca Jackson Databind que descargamos de mvnrepository.com, escogemos la version y damos clic
//en la ficha Maven, nos aparece un codigo, copiamos la etiqueta <dependency> junto con lo que esta adentro
//y pegamos dentro del archivo pom.xml enseguida del ultimo <dependency> que ya este, no los unimos. Al pegar
//aparecera un incono para cargar los cambios de Maven en nuestro proyecto. Si el icono no aparece, del lado derecho
//del IDE, aparece Maven o una m con el icono de actualizar y listo

//Mapeamos solo los datos del json, que nos interesan
//@JsonIgnoreProperties(ignoreUnknown = true) se tiene que indicar para decir que ignore las propiedades del json que no se estan mapeando
//Usamos @JsonAlias para indicar entre parentesis el nombre tal y como esta en el json. Enseguida especificamos
//el tipo y nombre de variable al que sera mapeado en java
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalTemporadas,
        @JsonAlias("imdbRating") String evaluacion
        ) {
}
