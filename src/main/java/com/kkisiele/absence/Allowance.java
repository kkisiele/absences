package com.kkisiele.absence;

public class Allowance {
    private int remainingDays;

    public Allowance(String name, int days) {
        this.remainingDays = days;
    }

    public boolean hasEnoughDays(int days) {
        return remainingDays >= days;
    }

    public void decreaseBy(int days) {
        remainingDays -= days;
    }

    public void increaseBy(int days) {
        remainingDays += days;
    }

    public int remainingDays() {
        return remainingDays;
    }
}
