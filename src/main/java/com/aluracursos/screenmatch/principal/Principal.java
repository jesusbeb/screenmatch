package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodioR;
import com.aluracursos.screenmatch.model.DatosSerieR;
import com.aluracursos.screenmatch.model.DatosTemporadasR;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        System.out.println("Datos de la serie" +datosSerie); //Imprime titulo, total de temporadas y evaluacion

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

//        //Creamos una lista de tipo DatosEpisodioR
//        //Creamos lista del tipo DatosEpisodio que sera igual al ArrayList temporadas.stream
//        //flatMap contiene una temporada t y lo convertira en una lista de episodios t.episodios y
//        //a esa lista de episodios se le aplica tambien stream y finalmente se convierte todo en una lista con
//        //collect(Collectors.toList()) que crea una lista mutable
//        List<DatosEpisodioR> datosEpisodioR = temporadas.stream()
//                .flatMap(t -> t.episodiosTemporada().stream())
//                .collect(Collectors.toList());

//        //Top 5 de episodios mejor valorados
//        //filter() e para cada episodio que sea diferente ! de e.evaluacion o sea que contenga "N/A" se ignorara
//        //sorted() compara las evaluaciones de todos los episodios y los ordena, reversed() ordena de mayor a menor
//        //limit() limita a cierto numero de elementos
//        //peek() (ojeada) nos permite ver las etapas de stream, algo asi como debugear
//        System.out.println("Top 5 de episodios");
//        datosEpisodioR.stream()
//                .filter(e -> !e.evaluacionEpisodio().equalsIgnoreCase("N/A"))
//                //.peek(e -> System.out.println("Pasando por el 1er filtro N/A: " +e))
//                .sorted(Comparator.comparing(DatosEpisodioR::evaluacionEpisodio).reversed())
//                //.peek(e -> System.out.println("Pasando por ordenacion de mayor a menor: " +e))
//                .map(e -> e.tituloEpisodio().toUpperCase())
//                //.peek(e -> System.out.println("Pasando filtro de mayusculas: " +e))
//                .limit(5)
//                .forEach(System.out::println);

        //Creamos una nueva lista de tipo episodios a partir de la lista de temporadas. Usamos la clase Episodio,
        //Creamos lista de episodios del tipo de clase Episodio que sera igual a la lista temporadas.stream
        //flatMap() cada elemento t de la temporada se convierte a una lista de episodios, a su vez le aplicamos stream
        //.map transforma cada dato del tipo Episodio en un nuevo Episodio que tendra el numero de temporada t.numero y
        //se pasan todos los datos de ese episodio representados por d. t.numero y d se pasan al constructor de Episodio
        //collect... crea una lista mutable
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodiosTemporada().stream()
                        .map(d -> new Episodio(t.numeroTemporada(),d)))
                .collect(Collectors.toList());
//        System.out.println("Todas las temporadas con sus episodios: ");
//        episodios.forEach(System.out::println);

//        //Busqueda de episodios a partir de x año
//        System.out.println("Indica el año a partir del cual deseas ver los episodios: ");
//        var fecha = teclado.nextInt(); //var se adapta al tipo de valor que se almacene en la variable
//        teclado.nextLine(); //se usa para cuando hay errores al no leer correctamente lo ingresado
//        //Instanciamos una variable llamada fechaBusqueda de tipo LocalDate. Le inicializamos el mes y el dia, el año lo ingresara el usuario
//        //Instanciamos una variable de tipo DateTimeFormatter llamada dtf y usamos ofPattern para
//        //indicar en que formato queremos imprimir la fecha
//        //A la lista episodios le aplicamos stream() con sus operaciones intermediarias
//        //filter() para cada episodio e-> obtenemos la fecha de lanzamiento. Como tenemos fechas de lanzamiento que
//        //son null, indicamos que esas no se consideren. Tambien usamos el metodo de la api de fechas isAfter() para
//        //indicar que solo considere a las que son despues del año que ingresa el usuario
//        //finalmente imprimimos pero usamos unos elementos diferentes para imprimir de forma diferente
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1,1);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaLanzamientoEpisodio() != null && e.getFechaLanzamientoEpisodio().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada " +e.getTemporadaDeEpisodio() +
//                                "Episodio " +e.getTituloEpisodio() +
//                                "Fecha de Lanzamiento " +e.getFechaLanzamientoEpisodio().format(dtf) //usamos .format para indicar el formato en que queremos la fecha
//                ));

//        //Buscar episodios por pedazo de titulo
//        //La lista de episodios la tratamos con stream()
//        //filter() e para cada episodio -> obtenemos el titulo con e.getTituloEpisodio y lo pasamos a mayusculas
//        //contains() busca en alguna parte del titulo lo que el usuario ingreso, igual convertido a mayusculas
//        //findFirst() obtiene la primera coincidencia. Y retorna un optional
//        //Optional es un contenedor donde puede o no haber un resultado. En una variable local del
//        //tipo Optional de <Episodio>, se almacena el resultado de los filtros de stream y la tratamos con if-else
//        System.out.println("Escribe el titulo completo que deseas buscar, o parte de él: ");
//        var pedazoTitulo = teclado.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTituloEpisodio().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado: ");
//            System.out.println("Los datos son: " +episodioBuscado.get()); //get().getTitulo() traeria solo el titulo
//        } else {
//            System.out.println("Episodio no encontrado.");
//        }

        //Obtenemos las evaluaciones por temporada
        //Trabajamos ahora con Map que recibe un Integer (temporada) y un Double (evaluacion) de la lista de episodios
        //filter() omite los episodios que no tienen evaluacion y que anteriormente les asignamos 0 con try-catch
        //collect(Collectors.groupingBy le pasamos el episodio y que obtenga la Temporada, entonces trae todos los
        //datos y los agrupa, este sera el valor Integer del Map
        //Collectors.averagingDouble le pasamos el Episodio y que obtenga la evaluacion. Este es el Double del Map
        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect((Collectors.groupingBy(Episodio::getTemporadaDeEpisodio,
                        Collectors.averagingDouble(Episodio::getEvaluacion))));
        System.out.println("Evaluaciones por temporada: " +evaluacionesPorTemporada);

        //DoubleSummaryStatistics es una clase que genera estadisticas de forma preestablecida
        //Instanciamos una variable de esa clase y la llamamos "est", nombre comun en el mercado
        //collect colecta los datos y summarizingDouble obtendra las evaluaciones de los datos de los Episodios
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Todas las evaluaciones: " +est);
        System.out.println("Media de las evaluaciones: " +est.getAverage());
        System.out.println("Episodio mejor evaluado: " +est.getMax());
        System.out.println("Episodio peor evaluado: " +est.getMin());



    }
}
