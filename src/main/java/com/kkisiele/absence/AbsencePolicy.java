package com.kkisiele.absence;

public interface AbsencePolicy {
    AbsencePolicy ALWAYS = period -> true;

    boolean canRequest(DatePeriod period);
}
