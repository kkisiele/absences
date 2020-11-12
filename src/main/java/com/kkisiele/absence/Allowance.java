package com.kkisiele.absence;

public class Allowance {
    private int remainingDays;

    public Allowance(int days) {
        this.remainingDays = days;
    }

    public int remainingDays() {
        return remainingDays;
    }

    public boolean reduceBy(int days) {
        if (days > remainingDays) {
            return false;
        }
        remainingDays -= days;
        return true;
    }
}
