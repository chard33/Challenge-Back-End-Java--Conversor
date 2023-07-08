package com.Alura.conversor;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class conexionConversionMedidas{
    
    private String contenido, resultado;
    private String[] tipoMedida;
    private ArrayList medidas = new ArrayList();
    private HttpResponse<String> response;

    public String[] getTipoMedida() {return tipoMedida;}
    public ArrayList getMedidas() {return medidas;}
    public String getResultado() {return resultado;}

    public conexionConversionMedidas(String url, String apiK){
        
        try {
            
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(URI.create(url))
                                             .header("X-RapidAPI-Key", apiK)
                                             .header("X-RapidAPI-Host", "measurement-unit-converter.p.rapidapi.com")
                                             .method("GET", HttpRequest.BodyPublishers.noBody())
                                             .build();
            
             response = HttpClient.
                    newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            
            contenido = response.body();
            
        } catch (IOException e) {
            System.out.println("Error en API, ingrese una apiKey valida.");
            
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void obtenerNombres(){
        
        tipoMedida = new String[new JSONArray(contenido).length()];

        
        int indice1 = -1;

        for(Object obj : new JSONArray(contenido)){

            int indice2 = -1;

            indice1++;

            tipoMedida[indice1] = ((JSONObject) obj).get("name").toString();

            String[][] arr1 = new String[((JSONObject) obj).getJSONArray(
                    "measurements").length()][2];

            for(Object obj2 : ((JSONObject) obj).getJSONArray("measurements")){

                indice2++;

                arr1[indice2][0] = ((JSONObject) obj2).get("abbr").toString();
                arr1[indice2][1] = ((JSONObject) obj2).get("plural").toString();
            }

            medidas.add(arr1);
        }
    }

    public void obtenerResultado(){

        resultado = new JSONObject(contenido).get("result").toString();
    }
}
