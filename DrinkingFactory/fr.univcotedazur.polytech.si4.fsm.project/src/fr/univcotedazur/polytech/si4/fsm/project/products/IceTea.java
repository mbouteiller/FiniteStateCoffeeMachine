package fr.univcotedazur.polytech.si4.fsm.project.products;

public class IceTea extends Product {

    public IceTea() {
        super("IceTea", 50);
    }

    @Override
    int prepareTime(int temperature, int size) {
        return 0;
    }

    @Override
    public String toString() {
        return "IceTea";
    }
}