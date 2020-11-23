package com.kkisiele.absence;

interface AbsenceWorkflow {
    AbsenceState initialState(RequestAbsence command);
}
