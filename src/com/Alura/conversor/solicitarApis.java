package com.Alura.conversor;

import javax.swing.*;

public class solicitarApis{
   
   private String api1, api2;
   
   public String getApi1(){return api1;}
   
   public String getApi2(){return api2;}
   
   public solicitarApis(){
   
      api1 = JOptionPane.showInputDialog("Ingresa tu Apikey de conversion de divisa: ");
      api2 = JOptionPane.showInputDialog("Ingresa tu Apikey de conversion de medidas: ");
   }
}
