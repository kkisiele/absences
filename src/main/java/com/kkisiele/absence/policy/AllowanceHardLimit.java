package com.kkisiele.absence.policy;

class AllowanceHardLimit implements AbsenceRequestPolicy {
    @Override
    public boolean satisfiedBy(RequestedAbsence absence) {
        return absence.allowance().hasEnoughDays(absence.requestedDays());
    }
}
