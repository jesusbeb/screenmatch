package com.aluracursos.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//Clase que contiene el metodo para consultar a la Api
public class ConsumoAPI {

    //Metodo para consultar a la API
    public String obtenerDatosApi(String url) {
        //Solicitamos request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            //Recibimos respuesta
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Almacenamos el cuerpo de la respuesta en un String y lo retornamos
        String json = response.body();
        return json;
    }

}
