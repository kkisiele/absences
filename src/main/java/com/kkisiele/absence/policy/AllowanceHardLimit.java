package com.kkisiele.absence.policy;

import com.kkisiele.absence.Allowance;

class AllowanceHardLimit implements AbsenceRequestPolicy {
    @Override
    public boolean satisfiedBy(RequestedAbsence absence) {
        for (Allowance allowance : absence.allowances()) {
            if (!allowance.hasEnoughDays(absence.requestedDays())) {
                return false;
            }
        }
        return true;
    }
}
