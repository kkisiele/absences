package com.kkisiele.absence.policy;

public interface AbsenceRequestPolicy {
    AbsenceRequestResult satisfiedBy(RequestedAbsence absence);
}
