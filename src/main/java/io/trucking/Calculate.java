package main.java.io.trucking;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate {
    private static int LOAD_CAPACITY;
    private static List<Product> products;
    private Scanner scanner;
    private int sumWeightInStack;
    private int sumCost;
    private int max;
    private Stack<Integer> stack;
    private TreeMap<Integer, String> tempMap;
    private Map<StringBuilder, Integer> map;

    public Calculate() {
        sumWeightInStack = 0;
        sumCost = 0;
        max = 0;
        stack = new Stack<>();
        tempMap = new TreeMap<>();
        map = new HashMap<>();
    }

    public void start() {
        inputUserData();
        scanner.close();
        calculateDelivery(products, 0, products.size());
        printResult();
    }

    private void printResult() {
        for (Map.Entry<StringBuilder, Integer> entry : map.entrySet()) {
            if (entry.getValue() == max) {
                System.out.println(entry.getKey());
            }
        }
    }

    private void inputUserData() {
        boolean flag = true;
        while (flag) {
            System.out.println("Введите грузоподъёмность машины - целое число в килограммах:");
            scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                String line = scanner.nextLine();
                LOAD_CAPACITY = Integer.valueOf(line);
                flag = false;
            } else {
                System.out.println("Введеные данные некорректные, попробуйте еще");
            }
        }

        flag = true;

        while (flag) {
            System.out.println("Введите товары для перевозки в формате название_предмета/вес/цена через пробел");
            String line = scanner.nextLine();
            String[] productArray = line.split(" ");
            if (checkCorrectData(productArray)) {
                products = fillProductList(productArray);
                flag = false;
            } else {
                System.out.println("Введеные данные некорректные, попробуйте еще");
            }
        }
    }

    private boolean checkCorrectData(String[] productArray) {
        boolean b = false;
        for (String product : productArray) {
            Pattern pattern = Pattern.compile("^[а-яА-я]+" + "/" + "\\d+" + "/" + "\\d+");
            Matcher matcher = pattern.matcher(product);
            b = matcher.matches();
        }
        return b;
    }

    private void calculateDelivery(List<Product> products, int fromIndex, int lastIndex) {
        if (sumWeightInStack == LOAD_CAPACITY) {
            StringBuilder builder = new StringBuilder();
            Integer key = tempMap.lastKey();
            if (key >= max) {
                max = key;
                for (String name : tempMap.values()) {
                    builder.append(name + " ");
                }
                builder.append(key);
                map.put(builder, key);
            }

            tempMap = new TreeMap<>();
            sumCost = 0;
        }
        for (int i = fromIndex; i < lastIndex; i++) {
            int weight = products.get(i).getWeight();
            if (sumWeightInStack + weight <= LOAD_CAPACITY) {
                stack.push(weight);
                sumWeightInStack += weight;
                int cost = products.get(i).getCost();
                sumCost += cost;
                tempMap.put(sumCost, products.get(i).getName());
                calculateDelivery(products, i + 1, lastIndex);

                sumWeightInStack -= stack.pop();
            }
        }
    }

    private List<Product> fillProductList(String[] productArray) {
        List<Product> productList = new ArrayList<>();
        for (String s : productArray) {
            String[] feature = s.split("/");
            String name = feature[0];
            int weight = Integer.valueOf(feature[1]);
            int cost = Integer.valueOf(feature[2]);
            int averageCost = cost / weight;
            Product product = new Product(name, weight, cost, averageCost);
            productList.add(product);
        }
        Collections.sort(productList, (Comparator.comparingInt(Product::getCost)).reversed());
        return productList;
    }
}
