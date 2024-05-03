package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodioR;
import com.aluracursos.screenmatch.model.DatosSerieR;
import com.aluracursos.screenmatch.model.DatosTemporadasR;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    //variables
    //private final indica que es una constante
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=9d40aa6e";
    private ConvierteDatos conversor = new ConvierteDatos();

    //metodos
    public void muestraElMenu(){
        System.out.println("Escribe el nombre de la serie que deseas buscar: ");
        String buscarSerie = teclado.nextLine();

        //Buscar datos de alguna serie
        //replace reemplazara espacios tecleados por el usuario por signos+
        //se envia la URL al metodo obtenerDatosApi del objeto consumoApi, el resultado se almacena en json
        //Instanciamos la clase DatosSerieR y le almacenamos lo que el metodo obtenerDatos nos trae al enviarle
        //la variable json y el nombre de la clase a la que se convertira
        var json = consumoApi.obtenerDatosApi(URL_BASE + buscarSerie.replace(" ", "+") + API_KEY);
//        System.out.println("json de la respuesta: " +json);
        DatosSerieR datosSerie = conversor.obtenerDatos(json, DatosSerieR.class);
//        System.out.println("Datos de la serie" +datosSerie); //Imprime titulo, total de temporadas y evaluacion

        //Obtenemos los datos de cada temporada y se almacenan en un ArrayList
        //La url la modificamos para iterar en ella segun el numero de temporada
        //Obtenemos los datos de la temporada y se almacenan en datosTemporadas
        //Imprimimos el ArrayList temporadas con un forEach
        List<DatosTemporadasR> temporadas = new ArrayList<>();
        for (int i = 1; i <= datosSerie.totalTemporadas() ; i++) {
            json = consumoApi.obtenerDatosApi(URL_BASE + buscarSerie.replace(" ", "+" ) + "&Season=" +i +API_KEY);
            DatosTemporadasR datosTemporadas = conversor.obtenerDatos(json, DatosTemporadasR.class);
            temporadas.add(datosTemporadas);
        }
        System.out.println("Datos por temporada: ");
        temporadas.forEach(System.out::println);







    }
}
