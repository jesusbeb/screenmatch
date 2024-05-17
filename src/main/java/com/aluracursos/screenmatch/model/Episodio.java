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

    //constructor. Recibe un objeto de tipo Integer y un objeto de tipo DatosEpisodio
    public Episodio(Integer temporadaDeEpisodio, DatosEpisodio d){
        this.temporadaDeEpisodio = temporadaDeEpisodio;
        this.tituloEpisodio = d.tituloEpisodio(); //viene de los datos que vienen en d, obtenemos el titulo
        this.numeroEpisodio = d.numeroEpisodio();
        //d.evaluacionEpisodio viene de la clase Record como un String y dentro de
        //esta clase Episodio esta como Double por lo que hacemos un parseo
        try{ //Usamos try cuando la evaluacion no se pueda parsear a Double por traer "N/A"
            this.evaluacionEpisodio = Double.valueOf(d.evaluacionEpisodio());
        }catch(NumberFormatException e){
            this.evaluacionEpisodio = 0.0; //
        }
        //d.fechaLanzamientoEpisodio viene como String, parseamos a LocalDate
        try{ //cuando la fecha de lanzamiento no se pueda parsear por traer "N/A"
            this.fechaLanzamientoEpisodio = LocalDate.parse(d.fechaLanzamientoEpisodio());
        } catch (DateTimeParseException e){
            this.fechaLanzamientoEpisodio = null;
        }

    }

    //getters & setters
    public Integer getTemporadaDeEpisodio(){
        return temporadaDeEpisodio;
    }

    public void setTemporadaDeEpisodio(Integer temporadaDeEpisodio){
        this.temporadaDeEpisodio = temporadaDeEpisodio;
    }

    public String getTituloEpisodio(){
        return tituloEpisodio;
    }

    public void setTituloEpisodio(String tituloEpisodio){
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

    public LocalDate getFechaLanzamientoEpisodio(){
        return fechaLanzamientoEpisodio;
    }

    public void setFechaLanzamientoEpisodio(LocalDate fechaLanzamientoEpisodio){
        this.fechaLanzamientoEpisodio = fechaLanzamientoEpisodio;
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
