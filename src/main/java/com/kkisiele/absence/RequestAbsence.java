package com.kkisiele.absence;

import java.util.UUID;

public class RequestAbsence {
    private final UUID id;
    private final DatePeriod period;
    private final AbsenceType type;

    public RequestAbsence(UUID id, DatePeriod period, AbsenceType type) {
        this.id = id;
        this.period = period;
        this.type = type;
    }

    public UUID id() {
        return id;
    }

    public DatePeriod period() {
        return period;
    }

    public AbsenceType type() {
        return type;
    }
}
