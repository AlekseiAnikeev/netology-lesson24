package ru.agentche.grocerybasket;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 08.09.2022
 */
public class Main {
    public static final String BASKET_FILE = "basket.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Черешня", "Яблоки", "Персики", "Хрен"};
        int[] prices = {350, 99, 170, 100};
        Basket basket = initializationBasket(products, prices);
        ClientLog clientLog = new ClientLog();
        while (true) {
            int productNum;
            int amount;
            System.out.println("Введи номер товара и его количество.");
            System.out.println("Список возможных товаров для покупки:");
            for (int i = 0; i < products.length; i++) {
                System.out.printf("%d. %s %d руб/шт\n", i + 1, products[i], prices[i]);
            }
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            try {
                productNum = Integer.parseInt(input.split(" ")[0]);
                amount = Integer.parseInt(input.split(" ")[1]);
            } catch (Exception e) {
                System.out.println("Не корректный ввод");
                continue;
            }
            clientLog.log(productNum, amount);
            basket.addToCart(productNum - 1, amount);
        }
        basket.printCart();
        saveToJSON(basket);
        clientLog.exportAsCSV(new File("log.csv"));
    }

    private static void saveToJSON(Basket basket) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(BASKET_FILE), basket);
        } catch (IOException e) {
            System.out.println("Файл поврежден!");
            throw new RuntimeException(e);
        }
    }

    private static Basket initializationBasket(String[] products, int[] prices) {
        File file = new File(BASKET_FILE);
        Basket basket;
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                basket = mapper.readValue(new File(BASKET_FILE), Basket.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            basket = new Basket(prices, products);
        }
        return basket;
    }
}
