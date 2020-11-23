package com.kkisiele.absence;

public class InitialAbsenceWorkflow implements AbsenceWorkflow {
    private final AbsenceState initialState;

    public InitialAbsenceWorkflow(AbsenceState initialState) {
        this.initialState = initialState;
    }

    @Override
    public AbsenceState initialState(RequestAbsence command) {
        return initialState;
    }
}
