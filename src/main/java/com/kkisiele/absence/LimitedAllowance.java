package com.kkisiele.absence;

class LimitedAllowance implements Allowance {
    private int remainingDays;

    public LimitedAllowance(int days) {
        this.remainingDays = days;
    }

    @Override
    public boolean hasEnoughDays(int days) {
        return remainingDays >= days;
    }

    @Override
    public void decreaseBy(int days) {
        remainingDays -= days;
    }

    @Override
    public void increaseBy(int days) {
        remainingDays += days;
    }

    public int remainingDays() {
        return remainingDays;
    }
}
