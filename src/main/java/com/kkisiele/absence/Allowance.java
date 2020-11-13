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

    public void request(int days) {
        remainingDays -= days;
    }

    public void cancel(int days) {
        remainingDays += days;
    }
}
