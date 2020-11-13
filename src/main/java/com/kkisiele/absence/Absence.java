package com.kkisiele.absence;

import java.util.UUID;

public class Absence {
    private final UUID id;
    private final DatePeriod period;
    private final AbsenceType type;
    private final AbsenceState state;
    private final int days;

    public Absence(DatePeriod period, int requestedDays, AbsenceType type, AbsenceState state) {
        days = requestedDays;
        this.id = UUID.randomUUID();
        this.period = period;
        this.type = type;
        this.state = state;
    }

    public UUID id() {
        return id;
    }

    public int days() {
        return days;
    }

    public DatePeriod period() {
        return period;
    }

    public AbsenceType type() {
        return type;
    }

    public AbsenceState state() {
        return state;
    }

    public boolean overlaps(DatePeriod period) {
        return period.overlaps(period);
    }
}
