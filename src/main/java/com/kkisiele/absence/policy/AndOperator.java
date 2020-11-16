package com.kkisiele.absence.policy;

import com.kkisiele.absence.Allowance;
import com.kkisiele.absence.RequestAbsence;

import java.util.List;

class AndOperator implements AbsenceRequestPolicy {
    private final List<AbsenceRequestPolicy> policies;

    public AndOperator(List<AbsenceRequestPolicy> policies) {
        this.policies = List.copyOf(policies);
    }

    @Override
    public boolean canRequest(RequestAbsence command, int requestedDays, Allowance allowance) {
        for (AbsenceRequestPolicy policy : policies) {
            boolean success = policy.canRequest(command, requestedDays, allowance);
            if (!success) {
                return false;
            }
        }
        return true;
    }
}
