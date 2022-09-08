import java.util.Scanner;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 22.06.2022
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Черешня", "Яблоки", "Персики"};
        int[] prices = {350, 99, 170};

        Basket basket = new Basket(prices, products);


        int[] productCountList = new int[products.length];
        boolean[] isSelected = new boolean[products.length];
        int sumProducts = 0;
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
            productCountList[numberOfProduct - 1] += productCount;
            sumProducts += prices[numberOfProduct - 1] * productCount;
            isSelected[numberOfProduct - 1] = true;
        }
        System.out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            if (isSelected[i]) {
                System.out.printf("%s %d шт %d руб/шт %d руб в сумме\n", products[i], productCountList[i], prices[i], prices[i] * productCountList[i]);
            }
        }
        System.out.printf("Итого %d руб\n", sumProducts);
    }
}
