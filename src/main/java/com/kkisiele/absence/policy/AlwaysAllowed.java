package com.kkisiele.absence.policy;

import com.kkisiele.absence.Allowance;
import com.kkisiele.absence.RequestAbsence;

class AlwaysAllowed implements AbsenceRequestPolicy {
    @Override
    public boolean canRequest(RequestAbsence command, int requestedDays, Allowance allowance) {
        return true;
    }
}
