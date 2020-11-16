package com.kkisiele.absence.policy;

import com.kkisiele.absence.DatePeriod;

import java.util.List;

public class AbsencePolicies {
    private static final AbsenceRequestPolicy ALWAYS_ALLOWED = new AlwaysAllowed();
    private static final AbsenceRequestPolicy ALLOWANCE_HARD_LIMIT = new AllowanceHardLimit();

    public static AbsenceRequestPolicy allowed() {
        return ALWAYS_ALLOWED;
    }

    public static AbsenceRequestPolicy allowanceHardLimit() {
        return ALLOWANCE_HARD_LIMIT;
    }

    public static AbsenceRequestPolicy absenceStartsIn(DatePeriod period) {
        return new AbsenceStartsInPeriod(period);
    }

    public static AbsenceRequestPolicy and(List<AbsenceRequestPolicy> policies) {
        return new AndOperator(policies);
    }
}
