package com.Alura.conversor;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class conexionConversionDivisas {

   private JSONObject contenido;
   private HashMap<String, String>  divisas = new HashMap();
   private String[] llaves;
   private String[] valores;
   private String resultado;
   
   public String[] getLlaves() {
      return llaves;
   }
   public String[] getValores() {
      return valores;
   }
   public String getResultado(){return resultado;}
   
   public conexionConversionDivisas(String url){

      try{

         Scanner sc = new Scanner(
                 new URI(
                         url).
                         toURL().openStream());

         StringBuilder sb = new StringBuilder();

         while(sc.hasNext()){

            sb.append(sc.nextLine());
         }

         sc.close();

         contenido = new JSONObject(sb.toString());

      }catch(MalformedURLException e){
         throw new RuntimeException(e);
      }catch(URISyntaxException e){
         throw new RuntimeException(e);
      }catch(IOException e){
         System.out.println("Error en API, ingrese una apiKey valida.");
         System.exit(0);
      }
   }
   
   public void obtenerArrays(){
      
      for (Object valor: contenido.getJSONArray("supported_codes")) {
            
            
            divisas.put(((JSONArray)valor).get(0).toString(), ((JSONArray)valor).get(1).toString());
         }

         llaves = new String[divisas.size()];
         valores = new String[divisas.size()];
         int i = 0;

         for(Map.Entry<String, String> moneda : divisas.entrySet()) {
            llaves[i] = moneda.getKey();
            valores[i] = moneda.getValue();
            i++;
         }
   }
   
   public void obtenerConversion(){
      
      resultado = contenido.get("conversion_result").toString();
   }
}
