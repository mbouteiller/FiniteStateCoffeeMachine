package fr.univcotedazur.polytech.si4.fsm.project.products;

public abstract class Product {
    public String name;
    public long price;

    Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public abstract String toString();

    public boolean isSoup() {
        return this.name.equals("soup");
    };
}
