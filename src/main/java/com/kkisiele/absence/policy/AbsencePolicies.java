package com.kkisiele.absence.policy;

import com.kkisiele.absence.DatePeriod;

import java.util.List;

public class AbsencePolicies {
    private static final AbsencePolicy ALWAYS_ALLOWED = new AlwaysAllowed();
    private static final AbsencePolicy ALLOWANCE_HARD_LIMIT = new AllowanceHardLimit();

    public static AbsencePolicy allowed() {
        return ALWAYS_ALLOWED;
    }

    public static AbsencePolicy allowanceHardLimit() {
        return ALLOWANCE_HARD_LIMIT;
    }

    public static AbsencePolicy absenceStartsIn(DatePeriod period) {
        return new AbsenceStartsInPeriod(period);
    }

    public static AbsencePolicy and(List<AbsencePolicy> policies) {
        return new AndOperator(policies);
    }
}
