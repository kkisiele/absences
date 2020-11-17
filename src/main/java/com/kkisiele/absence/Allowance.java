package com.kkisiele.absence;

public interface Allowance {
    Allowance UNLIMITED = new Allowance() {
        @Override
        public boolean hasEnoughDays(int days) {
            return true;
        }

        @Override
        public void decreaseBy(int days) {
            //do nothing
        }

        @Override
        public void increaseBy(int days) {
            //do nothing
        }
    };

    boolean hasEnoughDays(int days);

    void increaseBy(int days);

    void decreaseBy(int days);
}
