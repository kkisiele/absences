package com.kkisiele.absence.policy;

import com.kkisiele.absence.Allowance;

import static com.kkisiele.absence.AbsenceRejectionReason.NOT_ENOUGH_DAYS_AVAILABLE;
import static com.kkisiele.absence.policy.AbsenceRequestResult.failed;
import static com.kkisiele.absence.policy.AbsenceRequestResult.succeed;

class AllowanceHardLimit implements AbsenceRequestPolicy {
    @Override
    public AbsenceRequestResult satisfiedBy(RequestedAbsence absence) {
        for (Allowance allowance : absence.allowances()) {
            if (!allowance.hasEnoughDays(absence.requestedDays())) {
                return failed(NOT_ENOUGH_DAYS_AVAILABLE);
            }
        }
        return succeed();
    }
}
