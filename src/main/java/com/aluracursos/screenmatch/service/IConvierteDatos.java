package com.aluracursos.screenmatch.service;

//I se utiliza para identificar que es una interfaz
//Usamos una interfaz que trabaja con datos genericos, pensando en que la app se podria escalar en un futuro y
//que ahora no solo trabajara con Peliculas y Series, sino p.e. con actores. En caso contrario tendriamos que
//crear diferentes metodos para trabajar con diferentes objetos, aumentando el codigo
public interface IConvierteDatos {
    //metodo obtenerDatos que recibe el json en String y recibe Class<T> clase de tipo generico
    //<T> T -- Esto indica que trabajamos con tipos de datos genericos y retornara un generico
    <T> T obtenerDatos(String json, Class<T> clase);

}