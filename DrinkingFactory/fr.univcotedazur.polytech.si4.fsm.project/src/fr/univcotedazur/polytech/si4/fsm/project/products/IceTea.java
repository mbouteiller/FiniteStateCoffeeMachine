package fr.univcotedazur.polytech.si4.fsm.project.products;

public class IceTea extends Product {

    public IceTea() {
        super("icetea", 50);
    }

    public IceTea(int price) {
        super("icetea", price);
    }

    @Override
    public String toString() {
        return "icetea";
    }
}