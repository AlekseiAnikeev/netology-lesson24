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
    public static final String SETTINGS_FILE = "shop.xml";
    public static File settings = new File(SETTINGS_FILE);
    public static Config config = new Config();

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
            if (settings.exists()) {
                if (config.isSaveEnables()) {
                    if ("text".equals(config.getSaveFormat())) {
                        basket.saveTxt(new File(config.getSaveFileName()));
                    } else if ("json".equals(config.getSaveFormat())) {
                        basket.saveToJSON(basket);
                    }
                }
            }
        }
        if (settings.exists()) {
            if (config.isLogEnables()) {
                clientLog.exportAsCSV(new File(config.getLogFileName()));
            }
        }
        basket.printCart();
    }

    private static Basket initializationBasket(String[] products, int[] prices) {
        Basket basket = new Basket(prices, products);
        if (settings.exists()) {
            config.readConfig();
            if (config.isLoadEnables()) {
                if ("text".equals(config.getLoadFormat())) {
                    basket = Basket.loadFromTxtFile(new File(config.getLoadFileName()));
                } else if ("json".equals(config.getLoadFormat())) {
                    basket = loadBasketJson(products, prices, config.getLoadFileName());
                }
            }
        }
        return basket;
    }

    private static Basket loadBasketJson(String[] products, int[] prices, String fileName) {
        Basket basket;
        File file = new File(fileName);
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                basket = mapper.readValue(new File(fileName), Basket.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            basket = new Basket(prices, products);
        }
        return basket;
    }
}