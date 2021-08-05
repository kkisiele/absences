package com.kkisiele.absence;

public class UnlimitedAllowance implements Allowance {
    @Override
    public boolean hasEnoughDays(int days) {
        return true;
    }

    @Override
    public void decreaseBy(int days) {
    }

    @Override
    public void increaseBy(int days) {
    }

    @Override
    public int remainingDays() {
        return 0;
    }
}
