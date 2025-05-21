package br.com.leison.conversor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
public class Conversor {
    public static void main(String[] args) throws IOException, InterruptedException {

        String apiKey = "1f96d73661ea5fed08bf6fe9";
        Scanner leitura = new Scanner(System.in);

        int opcao = 0;

        while (opcao != 7) {
            System.out.println("""
                    ********************************
                    Bem vindo ao conversor de moedas
                    Opções
                    
                    01 - Dólar para peso argentino
                    02 - Peso argentino para Dólar
                    03 - Dólar para real brasileiro
                    04 - Real brasileiro para Dólar
                    05 - Dólar para peso chileno
                    06 - Peso chileno para dólar
                    07 - Sair
                    
                    *****************************************
                    """);

            System.out.println("Escolha uma opção:");
            opcao = leitura.nextInt();

            if (opcao == 7) {
                System.out.println("Você saiu. Volte sempre!");
                break; // Sai do loop
            }

            if (opcao >= 1 && opcao <= 6) {
                System.out.println("Digite um valor para converter:");
                double valorConverter = leitura.nextDouble();

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD"))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                JsonObject jsonobj = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonObject rates = jsonobj.getAsJsonObject("conversion_rates");
                double valorBRL = rates.get("BRL").getAsDouble();
                double valorCLP = rates.get("CLP").getAsDouble();
                double valorARS = rates.get("ARS").getAsDouble();

                switch (opcao) {
                    case 1 -> System.out.println("Conversão Dólar para peso argentino: " + (valorConverter * valorARS));
                    case 2 -> System.out.println("Conversão Peso argentino para Dólar: " + (valorConverter / valorARS));
                    case 3 -> System.out.println("Conversão Dólar para real brasileiro: " + (valorConverter * valorBRL));
                    case 4 -> System.out.println("Conversão Real brasileiro para Dólar: " + (valorConverter / valorBRL));
                    case 5 -> System.out.println("Conversão Dólar para peso chileno: " + (valorConverter * valorCLP));
                    case 6 -> System.out.println("Conversão Peso chileno para dólar: " + (valorConverter / valorCLP));
                }
            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }

        leitura.close();
    }
}