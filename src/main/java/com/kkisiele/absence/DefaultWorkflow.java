package com.kkisiele.absence;

class DefaultWorkflow implements AbsenceWorkflow {
    private AbsenceState initialState;

    public DefaultWorkflow(AbsenceState initialState) {
        this.initialState = initialState;
    }

    @Override
    public AbsenceState initialState() {
        return initialState;
    }
}
