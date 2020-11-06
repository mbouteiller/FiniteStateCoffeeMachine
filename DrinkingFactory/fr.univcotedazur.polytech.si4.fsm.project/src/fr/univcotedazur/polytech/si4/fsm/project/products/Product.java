package fr.univcotedazur.polytech.si4.fsm.project.products;

public abstract class Product {
    public String name;
    public int price;

    Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    abstract int prepareTime(int temperature, int size);
    public abstract String toString();
}
