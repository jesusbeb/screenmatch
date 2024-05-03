package com.aluracursos.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

//Clase para almacenar mejor los datos de un Episodio
public class Episodio {
    //variables
    private Integer temporadaDeEpisodio;
    private String tituloEpisodio;
    private Integer numeroEpisodio;
    private Double evaluacionEpisodio;
    private LocalDate fechaLanzamientoEpisodio;

    //constructor
    public Episodio(Integer numero, DatosEpisodioR d){
        this.temporadaDeEpisodio = numero; //viene de Integer numero
        this.tituloEpisodio = d.tituloEpisodio(); //viene de los datos que vienen en d, obtenemos el titulo
        this.numeroEpisodio = d.numeroEpisodio();
        try{ //Usamos try cuando la evaluacion no se pueda parsear a Double por traer "N/A"
            //d.evaluacionEpisodio viene de la clase Record como un String y dentro de
            //esta clase Episodio esta como Double por lo que hacemos un parseo
            this.evaluacionEpisodio = Double.valueOf(d.evaluacionEpisodio());
        }catch(NumberFormatException e){
            this.evaluacionEpisodio = 0.0; //
        }
        try{ //cuando la fecha de lanzamiento no se pueda parsear a LocalDate por traer "N/A"
            this.fechaLanzamientoEpisodio = LocalDate.parse(d.fechaLanzamientoEpisodio());
        } catch (DateTimeParseException e){
            this.fechaLanzamientoEpisodio = null;
        }

    }

    //getters & setters
    public Integer getTemporada(){
        return temporadaDeEpisodio;
    }

    public void setTemporada(Integer temporada){
        this.temporadaDeEpisodio = temporada;
    }

    public String getTitulo(){
        return tituloEpisodio;
    }

    public void setTitulo(String Titulo){
        this.tituloEpisodio = tituloEpisodio;
    }

    public Integer getNumeroEpisodio(){
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio){
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getEvaluacion(){
        return evaluacionEpisodio;
    }

    public void setEvaluacion(Double evaluacion){
        this.evaluacionEpisodio = evaluacion;
    }

    public LocalDate getFechaDeLanzamiento(){
        return fechaLanzamientoEpisodio;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento){
        this.fechaLanzamientoEpisodio = fechaDeLanzamiento;
    }

    //Sobreescribimos el metodo toString para mostrar los datos que queremos y no quiza la direccion fisica de esta clase
    @Override
    public String toString() {
        return
                "temporada=" + temporadaDeEpisodio +
                        ", titulo='" + tituloEpisodio + '\'' +
                        ", numeroEpisodio=" + numeroEpisodio +
                        ", evaluacion=" + evaluacionEpisodio +
                        ", fechaDeLanzamiento=" + fechaLanzamientoEpisodio;
    }


}
