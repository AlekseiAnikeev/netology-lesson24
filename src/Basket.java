import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 08.09.2022
 */
public class Basket implements Serializable {

    @Serial
    private static final long serialVersionUID = 6291314363810904633L;
    private final int[] prices;
    private final String[] products;
    private final Map<String, Integer> shoppingList = new HashMap<>();
    private int sumProducts;


    Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
    }

    public void addToCart(int productNum, int amount) {
        if (shoppingList.containsKey(products[productNum])) {
            Integer counter = shoppingList.get(products[productNum]);
            shoppingList.put(products[productNum], counter + amount);
        } else {
            shoppingList.put(products[productNum], amount);
        }
        saveBin(new File(Main.BASKET_FILE));
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

    public void saveBin(File file) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
            os.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Basket loadFromBinFile(File file) {
        Basket basket;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}
