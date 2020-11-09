package fr.univcotedazur.polytech.si4.fsm.project.products;

public class Expresso extends Product {

    public Expresso() {
        super("expresso", 50);
    }

    @Override
    int prepareTime(int temperature, int size) {
        return 0;
    }

    @Override
    public String toString() {
        return "expresso";
    }
}