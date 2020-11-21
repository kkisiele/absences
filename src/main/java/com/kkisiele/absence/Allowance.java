package com.kkisiele.absence;

public class Allowance {
    private final String name;
    private int remainingDays;

    public Allowance(String name, int days) {
        this.name = name;
        this.remainingDays = days;
    }

    public String name() {
        return name;
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
