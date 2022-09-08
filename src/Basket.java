import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 08.09.2022
 */
public class Basket {
    private final int[] prices;
    private final String[] products;
    private final Map<String, Integer> list = new HashMap<>();


    Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
    }

    public void addToCart(int productNum, int amount) {
        if (list.containsKey(products[productNum])) {
            Integer counter = list.get(products[productNum]);
            list.put(products[productNum], counter + amount);
        } else {
            list.put(products[productNum], amount);
        }
        saveTxt(new File(Main.BASKET_FILE));
    }

    public void printCart() {
        for (Map.Entry<String, Integer> entry : list.entrySet()) {
            System.out.printf("%s %d шт %d руб/шт %d руб в сумме\n"
                    , entry.getKey()
                    , entry.getValue()
                    , prices[Arrays.asList(products).indexOf(entry.getKey())]
                    , prices[Arrays.asList(products).indexOf(entry.getKey())] * entry.getValue());
        }
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
            for (Map.Entry<String, Integer> entry : list.entrySet()) {
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
}
