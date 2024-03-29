package com.kkisiele.absence;

import com.kkisiele.absence.policy.AbsenceRequestPolicy;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.kkisiele.absence.AbsenceRejectionReason.ABSENCE_TYPE_NOT_SUPPORTED;
import static com.kkisiele.absence.AbsenceRejectionReason.OVERLAPS_EXISTING_ABSENCE;

class Employee {
    private final Calendar calendar;
    private final Clock clock;
    private final Map<UUID, Absence> absences = new HashMap<>();
    private Map<AbsenceType, Allowance> allowances = new HashMap<>();

    public Employee(Calendar calendar, Clock clock) {
        this.calendar = calendar;
        this.clock = clock;
    }

    public void register(AbsenceType type, Allowance allowance) {
        allowances.put(type, allowance);
    }

    public void request(RequestAbsence command, AbsenceWorkflow workflow, AbsenceRequestPolicy requestPolicy) {
        if (overlaps(command.period())) {
            throw new AbsenceRejected(OVERLAPS_EXISTING_ABSENCE);
        }
        if (!allowances.containsKey(command.type())) {
            throw new AbsenceRejected(ABSENCE_TYPE_NOT_SUPPORTED);
        }
        var absence = new Absence(command.id());
        absence.request(command, workflow, allowances.get(command.type()), calendar, requestPolicy);
        absences.put(absence.id(), absence);
    }

    public void cancel(UUID absenceId) {
        absences.get(absenceId).cancel();
        absences.remove(absenceId);
    }

    private boolean overlaps(DatePeriod period) {
        return absences.values().stream().anyMatch(absence -> absence.overlaps(period));
    }

    public List<Absence> absences() {
        return List.copyOf(absences.values());
    }

    public int remainingDays(AbsenceType type) {
        return allowances.get(type).remainingDays();
    }

    public void approve(UUID id) {
        absences.get(id).approve();
    }

    public void decline(UUID id) {
        absences.get(id).decline();
        absences.remove(id);
    }
}
