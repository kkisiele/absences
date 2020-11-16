package com.kkisiele.absence.policy;

import com.kkisiele.absence.AbsenceType;
import com.kkisiele.absence.Allowance;
import com.kkisiele.absence.DatePeriod;

public class RequestedAbsence {
    private final DatePeriod period;
    private final AbsenceType type;
    private final int requestedDays;
    private final Allowance allowance;

    public RequestedAbsence(DatePeriod period, AbsenceType type, int requestedDays, Allowance allowance) {
        this.period = period;
        this.type = type;
        this.requestedDays = requestedDays;
        this.allowance = allowance;
    }

    public DatePeriod period() {
        return period;
    }

    public AbsenceType type() {
        return type;
    }

    public int requestedDays() {
        return requestedDays;
    }

    public Allowance allowance() {
        return allowance;
    }
}
