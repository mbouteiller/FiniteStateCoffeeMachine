package fr.univcotedazur.polytech.si4.fsm.project.products;

public class Tea extends Product {

    public Tea() {
        super("Tea", 40);
    }

    @Override
    int prepareTime(int temperature, int size) {
        return 0;
    }

    @Override
    public String toString() {
        return "tea";
    }
}
