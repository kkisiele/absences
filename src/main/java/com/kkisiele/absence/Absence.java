package com.kkisiele.absence;

import com.kkisiele.absence.policy.AbsenceRequestPolicy;

import java.util.UUID;

public class Absence {
    private final UUID id;
    private DatePeriod period;
    private AbsenceType type;
    private AbsenceState state;
    private int deducedDays;
    private Allowance allowance;

    public Absence(UUID id) {
        this.id = id;
    }

    public void request(RequestAbsence command, AbsenceWorkflow workflow, Allowance allowance, Calendar calendar, AbsenceRequestPolicy policy) {
        int requestedDays = command.period().numberOfWorkingDays(calendar);

        if (!policy.canRequest(command, requestedDays, allowance)) {
            throw new RequestRejected();
        }

        if (command.type().deductible()) {
            allowance.decreaseBy(requestedDays);
        }
        this.period = command.period();
        this.type = command.type();
        this.state = workflow.initialState();
        this.deducedDays = command.type().deductible() ? requestedDays : 0;
        this.allowance = allowance;
    }

    public void cancel() {
        if (state == AbsenceState.CANCELLED) {
            throw new IllegalStateException("Cannot cancel already cancelled absence");
        }
        allowance.increaseBy(deducedDays);
    }

    public UUID id() {
        return id;
    }

    public int days() {
        return deducedDays;
    }

    public DatePeriod period() {
        return period;
    }

    public AbsenceType type() {
        return type;
    }

    public AbsenceState state() {
        return state;
    }

    public boolean overlaps(DatePeriod period) {
        return period.overlaps(period);
    }
}
