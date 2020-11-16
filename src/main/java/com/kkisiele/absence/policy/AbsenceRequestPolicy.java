package com.kkisiele.absence.policy;

public interface AbsenceRequestPolicy {
    boolean satisfiedBy(RequestedAbsence absence);
}
