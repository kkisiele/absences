package com.kkisiele.absence.policy;

class AllowanceHardLimit implements AbsenceRequestPolicy {
    @Override
    public boolean satisfiedBy(RequestedAbsence absence) {
        if (absence.type().deductible()) {
            return absence.allowance().hasEnoughDays(absence.requestedDays());
        }
        return true;
    }
}
