package com.kkisiele.absence.policy;

import java.util.List;

class AndOperator implements AbsenceRequestPolicy {
    private final List<AbsenceRequestPolicy> policies;

    public AndOperator(List<AbsenceRequestPolicy> policies) {
        this.policies = List.copyOf(policies);
    }

    @Override
    public boolean satisfiedBy(RequestedAbsence absence) {
        for (AbsenceRequestPolicy policy : policies) {
            boolean success = policy.satisfiedBy(absence);
            if (!success) {
                return false;
            }
        }
        return true;
    }
}
