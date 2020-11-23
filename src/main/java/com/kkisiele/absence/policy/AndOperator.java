package com.kkisiele.absence.policy;

import java.util.List;

import static com.kkisiele.absence.policy.AbsenceRequestResult.succeed;

class AndOperator implements AbsenceRequestPolicy {
    private final List<AbsenceRequestPolicy> policies;

    public AndOperator(List<AbsenceRequestPolicy> policies) {
        this.policies = List.copyOf(policies);
    }

    @Override
    public AbsenceRequestResult satisfiedBy(RequestedAbsence absence) {
        for (AbsenceRequestPolicy policy : policies) {
            AbsenceRequestResult result = policy.satisfiedBy(absence);
            if (result.failed()) {
                return result;
            }
        }
        return succeed();
    }
}
