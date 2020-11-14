package com.kkisiele.absence;

public class RequestAbsence {
    private final DatePeriod period;
    private final AbsenceType type;

    public RequestAbsence(DatePeriod period, AbsenceType type) {
        this.period = period;
        this.type = type;
    }

    public DatePeriod period() {
        return period;
    }

    public AbsenceType type() {
        return type;
    }
}
