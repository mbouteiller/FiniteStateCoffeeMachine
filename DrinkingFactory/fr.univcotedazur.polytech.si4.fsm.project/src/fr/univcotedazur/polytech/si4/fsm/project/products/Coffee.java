package fr.univcotedazur.polytech.si4.fsm.project.products;

public class Coffee extends Product {

    public Coffee() {
        super("Coffee", 35);
    }

    @Override
    int prepareTime(int temperature, int size) {
        return 0;
    }

    @Override
    public String toString() {
        return "Coffee";
    }
}
