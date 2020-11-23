package com.kkisiele.absence.policy;

import com.kkisiele.absence.DatePeriod;

import static com.kkisiele.absence.AbsenceRejectionReason.NOT_START_IN_VALID_PERIOD;

class AbsenceStartsInPeriod implements AbsenceRequestPolicy {
    private final DatePeriod allowedPeriod;

    public AbsenceStartsInPeriod(DatePeriod period) {
        this.allowedPeriod = period;
    }

    @Override
    public AbsenceRequestResult satisfiedBy(RequestedAbsence absence) {
        boolean succeeded = allowedPeriod.contains(absence.period().start());
        return succeeded ? AbsenceRequestResult.succeed() : AbsenceRequestResult.failed(NOT_START_IN_VALID_PERIOD);
    }
}
