import java.io.File;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 08.09.2022
 */
public class Basket {
    private int[] prices;
    private String[] products;

    Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
    }

    public void addToCart(int productNum, int amount) {

    }

    public void printCart() {

    }

    public void saveTxt(File textFile) {

    }

    public static Basket loadFromTxtFile(File textFile) {

    }
}
