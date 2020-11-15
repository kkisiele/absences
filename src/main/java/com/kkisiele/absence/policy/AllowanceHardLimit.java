package com.kkisiele.absence.policy;

import com.kkisiele.absence.Allowance;
import com.kkisiele.absence.RequestAbsence;

class AllowanceHardLimit implements AbsencePolicy {
    @Override
    public boolean canRequest(RequestAbsence command, int requestedDays, Allowance allowance) {
        if (command.type().deductible()) {
            return allowance.hasEnoughDays(requestedDays);
        }
        return true;
    }
}
