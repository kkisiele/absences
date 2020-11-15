package com.kkisiele.absence.policy;

import com.kkisiele.absence.DatePeriod;

public class AbsencePolicies {
    public static final AbsencePolicy ALWAYS_ALLOWED = new AlwaysAllowed();
    public static final AbsencePolicy ALLOWANCE_HARD_LIMIT = new AllowanceHardLimit();

    public static AbsencePolicy absenceStartsIn(DatePeriod period) {
        return new AbsenceStartsInPeriod(period);
    }

    public static AbsencePolicy and(AbsencePolicy... policies) {
        return new AndOperator(policies);
    }
}
