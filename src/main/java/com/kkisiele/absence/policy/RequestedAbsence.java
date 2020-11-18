package com.kkisiele.absence.policy;

import com.kkisiele.absence.AbsenceType;
import com.kkisiele.absence.Allowance;
import com.kkisiele.absence.DatePeriod;

import java.util.List;

public class RequestedAbsence {
    private final DatePeriod period;
    private final AbsenceType type;
    private final int requestedDays;
    private final List<Allowance> allowances;

    public RequestedAbsence(DatePeriod period, AbsenceType type, int requestedDays, List<Allowance> allowances) {
        this.period = period;
        this.type = type;
        this.requestedDays = requestedDays;
        this.allowances = List.copyOf(allowances);
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

    public List<Allowance> allowances() {
        return allowances;
    }
}
