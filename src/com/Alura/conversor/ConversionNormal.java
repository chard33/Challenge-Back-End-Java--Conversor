package com.Alura.conversor;

public class ConversionNormal {

    String[][] divisas = {

            {"Pesos Mexicanos", "Dólares"},
            {"Pesos Mexicanos", "Euros"},
            {"Pesos Mexicanos", "Libras Esterlinas"},
            {"Pesos Mexicanos", "Yen Japonéses"},
            {"Pesos Mexicanos", "Won sul-coreanos"},
            {"Dólares", "Pesos Mexicanos"},
            {"Euros", "Pesos Mexicanos"},
            {"Libras Esterlinas", "Pesos Mexicanos"},
            {"Yen Japonéses", "Pesos Mexicanos"},
            {"Won sul-coreanos", "Pesos Mexicanos"}

    };
    double[] valores =
            {
                    0.058,
                    0.053,
                    0.046,
                    8.39,
                    76.23,
                    17.21,
                    18.70,
                    21.85,
                    0.12,
                    0.013
            };

    String[][] temperaturas = {

            {"Celsius", "Fahrenheit"},
            {"Celsius", "Kelvin"},
            {"Fahrenheit", "Celsius"},
            {"Fahrenheit", "Kelvin"},
            {"Kelvin", "Celsius"},
            {"Kelvin", "Fahrenheit"}
    };

    public String[][] getDivisas() {return divisas;}
    public String[][] getTemperaturas() {return temperaturas;}

    public double obteneResultadoD(double cantidad, int indice){

        return cantidad * valores[indice];
    }

    public double obteneResultadoT(double cantidad, int indice){

        double resultado;

        switch (indice){
            case 0:
                resultado = cantidad * 9 / 5 + 32;
                break;
            case 1:
                resultado = cantidad + 273.15;
                break;
            case 2:
                resultado = (cantidad - 32) * 5 / 9;
                break;
            case 3:
                resultado = (cantidad + 459.67) * 5 / 9;
                break;
            case 4:
                resultado = cantidad - 273.15;
                break;
            case 5:
                resultado = (cantidad - 273.15) * 9/5 + 32;
                break;
            default:
                resultado = 0.0;
                break;
        }

        return resultado;
    }
}
