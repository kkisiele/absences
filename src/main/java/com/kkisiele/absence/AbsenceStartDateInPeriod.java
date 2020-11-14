package com.kkisiele.absence;

public class AbsenceStartDateInPeriod implements AbsencePolicy {
    private final DatePeriod period;

    public AbsenceStartDateInPeriod(DatePeriod period) {
        this.period = period;
    }

    @Override
    public boolean canRequest(DatePeriod p) {
        return period.contains(p.start());
    }
}
