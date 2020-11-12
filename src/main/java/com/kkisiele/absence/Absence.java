package com.kkisiele.absence;

public class Absence {
    private final DatePeriod period;
    private final AbsenceType type;
    private final AbsenceState state;

    public Absence(DatePeriod period, int requestedDays, AbsenceType type, AbsenceState state) {
        this.period = period;
        this.type = type;
        this.state = state;
    }

    public AbsenceState state() {
        return state;
    }

    public boolean overlaps(DatePeriod period) {
        return period.overlaps(period);
    }
}
