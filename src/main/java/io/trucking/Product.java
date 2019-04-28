package main.java.io.trucking;

public class Product {
    private String name;
    private int weight;
    private int cost;
    private int averageCost;

    public Product(String name, int weight, int cost, int averageCost) {
        this.name = name;
        this.weight = weight;
        this.cost = cost;
        this.averageCost = averageCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(int averageCost) {
        this.averageCost = averageCost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", cost=" + cost +
                ", averageCost=" + averageCost +
                '}';
    }
}
