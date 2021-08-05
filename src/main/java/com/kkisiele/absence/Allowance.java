package com.kkisiele.absence;

public interface Allowance {
    boolean hasEnoughDays(int days);
    void decreaseBy(int days);
    void increaseBy(int days);
    int remainingDays();
}
