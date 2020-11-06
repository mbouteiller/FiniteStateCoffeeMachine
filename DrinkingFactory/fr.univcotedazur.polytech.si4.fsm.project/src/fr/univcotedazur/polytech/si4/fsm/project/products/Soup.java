package fr.univcotedazur.polytech.si4.fsm.project.products;

public class Soup extends Product {

    public Soup() {
        super("Soup", 75);
    }

    @Override
    int prepareTime(int temperature, int size) {
        return 0;
    }

    @Override
    public String toString() {
        return "soup";
    }
}
