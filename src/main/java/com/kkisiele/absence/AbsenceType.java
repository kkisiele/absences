package com.kkisiele.absence;

import static com.kkisiele.absence.AbsenceState.APPROVAL_PENDING;
import static com.kkisiele.absence.AbsenceState.APPROVED;

public enum AbsenceType {
    SICKNESS(new DefaultWorkflow(APPROVED), false),
    HOLIDAY(new DefaultWorkflow(APPROVAL_PENDING), true),
    SPECIAL(new DefaultWorkflow(APPROVAL_PENDING), true);

    private final AbsenceWorkflow workflow;
    private final boolean deductible;

    AbsenceType(AbsenceWorkflow workflow, boolean deductible) {
        this.workflow = workflow;
        this.deductible = deductible;
    }

    public AbsenceWorkflow workflow() {
        return workflow;
    }

    public boolean deductible() {
        return deductible;
    }
}
