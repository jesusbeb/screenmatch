package com.aluracursos.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//Clase que contiene el metodo para convertir el json a una clase e implementamos
//de la interfaz que creamos
public class ConvierteDatos implements IConvierteDatos{
    //Creamos una instancia de ObjectMapper que nos servira para mapear los valores que vienen de la API
    private ObjectMapper objectMapper = new ObjectMapper();

    //metodo obtenerDatos que tenemos que implementar de la Interfaz y que retorna algo generico
    //retorna un objeto del tipo objectMapper, readValue lee el valor del json convertido en
    //string y lo transforma en la clase que pasemos
    //readValue puede generar una excepcion, por lo que hay que tratarla
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
