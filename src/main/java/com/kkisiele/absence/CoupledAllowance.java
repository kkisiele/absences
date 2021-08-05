package com.kkisiele.absence;

public class CoupledAllowance implements Allowance {
    private final Allowance main;
    private final Allowance sub;

    public CoupledAllowance(Allowance main, Allowance sub) {
        this.main = main;
        this.sub = sub;
    }

    @Override
    public boolean hasEnoughDays(int days) {
        return main.hasEnoughDays(days) && sub.hasEnoughDays(days);
    }

    @Override
    public void decreaseBy(int days) {
        main.decreaseBy(days);
        sub.decreaseBy(days);
    }

    @Override
    public void increaseBy(int days) {
        main.increaseBy(days);
        sub.increaseBy(days);
    }

    @Override
    public int remainingDays() {
        return main.remainingDays();
    }
}
