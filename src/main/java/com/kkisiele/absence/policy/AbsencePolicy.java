package com.kkisiele.absence.policy;

import com.kkisiele.absence.Allowance;
import com.kkisiele.absence.RequestAbsence;

public interface AbsencePolicy {
    boolean canRequest(RequestAbsence command, int requestedDays, Allowance allowance);
}
