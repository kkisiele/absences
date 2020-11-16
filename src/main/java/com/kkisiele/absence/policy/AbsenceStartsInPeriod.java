package com.kkisiele.absence.policy;

import com.kkisiele.absence.DatePeriod;

class AbsenceStartsInPeriod implements AbsenceRequestPolicy {
    private final DatePeriod period;

    public AbsenceStartsInPeriod(DatePeriod period) {
        this.period = period;
    }

    @Override
    public boolean satisfiedBy(RequestedAbsence absence) {
        return period.contains(absence.period().start());
    }
}
