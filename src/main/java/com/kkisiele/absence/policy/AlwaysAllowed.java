package com.kkisiele.absence.policy;

class AlwaysAllowed implements AbsenceRequestPolicy {
    @Override
    public boolean satisfiedBy(RequestedAbsence absence) {
        return true;
    }
}
