package fr.univcotedazur.polytech.si4.fsm.project.products;

public class None extends Product {

    public None() {
        super("None", 0);
    }

    @Override
    int prepareTime(int temperature, int size) {
        return 0;
    }

    @Override
    public String toString() {
        return "None";
    }
}