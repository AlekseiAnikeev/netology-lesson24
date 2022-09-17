package ru.agentche.grocerybasket;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static ru.agentche.grocerybasket.Main.BASKET_FILE;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 08.09.2022
 */
public class Basket {
    private int[] prices;
    private String[] products;
    private final Map<String, Integer> shoppingList = new HashMap<>();
    private int sumProducts;

    public Basket() {

    }


    Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
    }

    public void addToCart(int productNum, int amount) {
        try {
            if (shoppingList.containsKey(products[productNum])) {
                Integer counter = shoppingList.get(products[productNum]);
                shoppingList.put(products[productNum], counter + amount);
            } else {
                shoppingList.put(products[productNum], amount);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Товар не найден");
        }
    }

    public void printCart() {
        for (Map.Entry<String, Integer> entry : shoppingList.entrySet()) {
            sumProducts += prices[Arrays.asList(products).indexOf(entry.getKey())] * entry.getValue();
            System.out.printf("%s %d шт %d руб/шт %d руб в сумме\n"
                    , entry.getKey()
                    , entry.getValue()
                    , prices[Arrays.asList(products).indexOf(entry.getKey())]
                    , prices[Arrays.asList(products).indexOf(entry.getKey())] * entry.getValue());
        }
        System.out.printf("Итого %d руб\n", sumProducts);
    }

    public void saveTxt(File textFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFile))) {
            for (String product : products) {
                writer.write(product + "@");
            }
            writer.write("\n");
            for (int price : prices) {
                writer.write(price + "@");
            }
            writer.write("\n");
            for (Map.Entry<String, Integer> entry : shoppingList.entrySet()) {
                writer.write(entry.getKey() + "@" + entry.getValue() + "@");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Basket loadFromTxtFile(File textFile) {
        Basket basket;
        String[] products = new String[0];
        int[] prices = new int[0];
        Map<String, Integer> list = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String[] tmp;
            int count = 0;
            while (reader.ready()) {
                if (count == 0) {
                    products = reader.readLine().split("@");
                } else if (count == 1) {
                    tmp = reader.readLine().split("@");
                    prices = new int[tmp.length];
                    for (int i = 0; i < tmp.length; i++) {
                        prices[i] = Integer.parseInt(tmp[i]);
                    }
                } else {
                    tmp = reader.readLine().split("@");
                    for (int i = 0; i < tmp.length - 1; i += 2) {
                        list.put(tmp[i], Integer.parseInt(tmp[i + 1]));
                    }
                }
                count++;
            }
            basket = new Basket(prices, products);
            for (Map.Entry<String, Integer> entry : list.entrySet()) {
                basket.addToCart(Arrays.asList(products).indexOf(entry.getKey()), entry.getValue());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }


    public void saveToJSON(Basket basket) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(BASKET_FILE), basket);
        } catch (IOException e) {
            System.out.println("Файл поврежден!");
            throw new RuntimeException(e);
        }
    }

    public int[] getPrices() {
        return prices;
    }

    public String[] getProducts() {
        return products;
    }

    public Map<String, Integer> getShoppingList() {
        return shoppingList;
    }
}
