package fr.univcotedazur.polytech.si4.fsm.project.products;

public abstract class Product {
    public String name;
    public long price;

    Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public abstract String toString();

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isCoffee() {
        return this.name.equals("coffee");
    }
    public boolean isTea() {
        return this.name.equals("tea");
    }
    public boolean isExpresso() {
        return this.name.equals("expresso");
    }
    public boolean isSoup() {
        return this.name.equals("soup");
    }
    public boolean isIceTea() {
        return this.name.equals("icetea");
    }
}
