package com.kkisiele.absence.policy;

import com.kkisiele.absence.Allowance;
import com.kkisiele.absence.RequestAbsence;

import java.util.List;

class AndOperator implements AbsencePolicy {
    private final List<AbsencePolicy> policies;

    public AndOperator(AbsencePolicy... policies) {
        this.policies = List.of(policies);
    }

    @Override
    public boolean canRequest(RequestAbsence command, int requestedDays, Allowance allowance) {
        for (AbsencePolicy policy : policies) {
            boolean success = policy.canRequest(command, requestedDays, allowance);
            if (!success) {
                return false;
            }
        }
        return true;
    }
}
