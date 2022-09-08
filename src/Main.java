import java.io.File;
import java.util.Scanner;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 08.09.2022
 */
public class Main {
    public static final String BASKET_FILE = "basket.txt";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Черешня", "Яблоки", "Персики", "Хрен"};
        int[] prices = {350, 99, 170, 100};
        Basket basket = initializationBasket(products, prices);
        while (true) {
            int numberOfProduct;
            int productCount;
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
                numberOfProduct = Integer.parseInt(input.split(" ")[0]);
                productCount = Integer.parseInt(input.split(" ")[1]);
            } catch (Exception e) {
                System.out.println("Не корректный ввод");
                continue;
            }
            basket.addToCart(numberOfProduct - 1, productCount);
        }
        basket.printCart();
    }

    private static Basket initializationBasket(String[] products, int[] prices) {
        File file = new File(BASKET_FILE);
        Basket basket;
        if (file.exists()) {
            basket = Basket.loadFromTxtFile(file);
        } else {
            basket = new Basket(prices, products);
        }
        return basket;
    }
}
