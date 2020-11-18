package com.kkisiele.absence;

import com.kkisiele.absence.policy.AbsenceRequestPolicy;
import com.kkisiele.absence.policy.RequestedAbsence;

import java.util.List;
import java.util.UUID;

import static com.kkisiele.absence.AbsenceState.CANCELLED;

class Absence {
    private final UUID id;
    private DatePeriod period;
    private AbsenceType type;
    private AbsenceState state;
    private int deducedDays;
    private List<Allowance> allowances;

    public Absence(UUID id) {
        this.id = id;
    }

    public void request(RequestAbsence command, AbsenceWorkflow workflow, List<Allowance> allowances, Calendar calendar, AbsenceRequestPolicy policy) {
        int requestedDays = calendar.numberOfWorkingDays(command.period());

        if (!policy.satisfiedBy(new RequestedAbsence(command.period(), command.type(), requestedDays, allowances))) {
            throw new RequestRejected();
        }

        allowances.forEach(a -> a.decreaseBy(requestedDays));
        this.period = command.period();
        this.type = command.type();
        this.state = workflow.initialState();
        this.deducedDays = command.type().deductible() ? requestedDays : 0;
        this.allowances = List.copyOf(allowances);
    }

    public void cancel() {
        if (state == CANCELLED) {
            throw new IllegalStateException("Cannot cancel already cancelled absence");
        }
        allowances.forEach(a -> a.increaseBy(deducedDays));
        state = CANCELLED;
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
