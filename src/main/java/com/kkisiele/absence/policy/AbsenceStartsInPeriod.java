package com.kkisiele.absence.policy;

import com.kkisiele.absence.DatePeriod;

class AbsenceStartsInPeriod implements AbsenceRequestPolicy {
    private final DatePeriod allowedPeriod;

    public AbsenceStartsInPeriod(DatePeriod period) {
        this.allowedPeriod = period;
    }

    @Override
    public boolean satisfiedBy(RequestedAbsence absence) {
        return allowedPeriod.contains(absence.period().start());
    }
}
