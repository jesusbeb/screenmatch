package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodioR;
import com.aluracursos.screenmatch.model.DatosSerieR;
import com.aluracursos.screenmatch.model.DatosTemporadasR;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
//        System.out.println("Datos por temporada: ");
//        temporadas.forEach(System.out::println);

//        //Mostrar solo titulo de episodios
//        //itera la lista temporadas para traer los episodios
//        for (int i = 0; i < datosSerie.totalTemporadas(); i++) {
//            List<DatosEpisodioR> episodiosTemporada = temporadas.get(i).episodiosTemporada();
//            //iteramos la lista episodiosTemporadas para obtener los titulos
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).tituloEpisodio());
//            }
//        }

//        //Expresion lambda para simplificar el for anidado anterior
//        //temporadas.forEach itera la lista de temporadas
//        //(t -> t.episodiosTemporada) de la lista de temporadas traemos los episodios.
//        // t es el argumento -> t.episodios es el cuerpo de la funcion
//        //t.episodiosTemporada().forEach  hacemos tambien un forEach de la lista de episodios
//        //"e" es el argumento -> y el cuerpo de la funcion imprime el titulo
//        temporadas.forEach(t -> t.episodiosTemporada().forEach(e -> System.out.println(e.tituloEpisodio())));

        //Creamos una lista de tipo DatosEpisodioR
        //Creamos lista del tipo DatosEpisodio que sera igual al ArrayList temporadas.stream
        //flatMap contiene una temporada t y lo convertira en una lista de episodios t.episodios y
        //a esa lista de episodios se le aplica tambien stream y finalmente se convierte todo en una lista con
        //collect(Collectors.toList()) que crea una lista mutable
        List<DatosEpisodioR> datosEpisodioR = temporadas.stream()
                .flatMap(t -> t.episodiosTemporada().stream())
                .collect(Collectors.toList());

        //Top 5 de episodios mejor valorados
        //filter() e para cada episodio que sea diferente ! de e.evaluacion o sea que contenga "N/A" se ignorara
        //sorted() compara las evaluaciones de todos los episodios y los ordena, reversed() ordena de mayor a menor
        //limit() limita a cierto numero de elementos
        //peek() (ojeada) nos permite ver las etapas de stream, algo asi como debugear
        System.out.println("Top 5 de episodios");
        datosEpisodioR.stream()
                .filter(e -> !e.evaluacionEpisodio().equalsIgnoreCase("N/A"))
                //.peek(e -> System.out.println("Pasando por el 1er filtro N/A: " +e))
                .sorted(Comparator.comparing(DatosEpisodioR::evaluacionEpisodio).reversed())
                //.peek(e -> System.out.println("Pasando por ordenacion de mayor a menor: " +e))
                .map(e -> e.tituloEpisodio().toUpperCase())
                //.peek(e -> System.out.println("Pasando filtro de mayusculas: " +e))
                .limit(5)
                .forEach(System.out::println);


    }
}
