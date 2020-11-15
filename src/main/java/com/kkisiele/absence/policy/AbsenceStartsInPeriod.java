package com.kkisiele.absence.policy;

import com.kkisiele.absence.Allowance;
import com.kkisiele.absence.DatePeriod;
import com.kkisiele.absence.RequestAbsence;

class AbsenceStartsInPeriod implements AbsencePolicy {
    private final DatePeriod period;

    public AbsenceStartsInPeriod(DatePeriod period) {
        this.period = period;
    }

    @Override
    public boolean canRequest(RequestAbsence command, int requestedDays, Allowance allowance) {
        return period.contains(command.period().start());
    }
}
