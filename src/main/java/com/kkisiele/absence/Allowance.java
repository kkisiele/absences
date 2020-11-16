package com.kkisiele.absence;

public interface Allowance {
    Allowance UNLIMITED = new Allowance() {
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
    };

    boolean hasEnoughDays(int days);

    void decreaseBy(int days);

    void increaseBy(int days);
}
