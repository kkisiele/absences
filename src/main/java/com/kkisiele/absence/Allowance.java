package com.kkisiele.absence;

public class Allowance {
    private int remainingDays;

    public Allowance(int days) {
        this.remainingDays = days;
    }

    public int remainingDays() {
        return remainingDays;
    }

    public boolean hasEnoughDays(int days) {
        return remainingDays >= days;
    }

    public void reduceBy(int days) {
        remainingDays -= days;
    }
}
