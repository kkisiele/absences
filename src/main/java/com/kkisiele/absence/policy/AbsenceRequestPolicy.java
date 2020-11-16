package com.kkisiele.absence.policy;

import com.kkisiele.absence.Allowance;
import com.kkisiele.absence.RequestAbsence;

public interface AbsenceRequestPolicy {
    boolean canRequest(RequestAbsence command, int requestedDays, Allowance allowance);
}
