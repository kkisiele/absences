package com.kkisiele.absence.policy;

class AlwaysAllowed implements AbsenceRequestPolicy {
    @Override
    public AbsenceRequestResult satisfiedBy(RequestedAbsence absence) {
        return AbsenceRequestResult.succeed();
    }
}
