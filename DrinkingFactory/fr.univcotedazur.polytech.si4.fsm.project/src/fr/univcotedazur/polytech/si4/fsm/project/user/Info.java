package fr.univcotedazur.polytech.si4.fsm.project.user;

public class Info {
    private int count;
    private int sum;

    public Info(int count, int sum) {
        this.count = count;
        this.sum = sum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int average) {
        this.sum = average;
    }
}
