package com.kkisiele.absence;

public class RequestedAbsence {
    private final DatePeriod period;

    public RequestedAbsence(DatePeriod period) {
        this.period = period;
    }

    public DatePeriod period() {
        return period;
    }
}
