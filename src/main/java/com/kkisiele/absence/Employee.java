package com.kkisiele.absence;

import com.kkisiele.absence.policy.AbsenceRequestPolicy;

import java.time.Clock;
import java.util.*;

class Employee {
    private final Calendar calendar;
    private final Clock clock;
    private final Map<UUID, Absence> absences = new HashMap<>();
    private Map<AbsenceType, List<Allowance>> allowances = new HashMap<>();

    public Employee(Calendar calendar, Clock clock) {
        this.calendar = calendar;
        this.clock = clock;
    }

    public void register(AbsenceType type, Allowance allowance) {
        allowances.computeIfAbsent(type, t -> new ArrayList<>()).add(allowance);
    }

    public void request(RequestAbsence command, AbsenceWorkflow workflow, AbsenceRequestPolicy requestPolicy) {
        if (overlaps(command.period())) {
            throw new AbsenceRejected();
        }
        if (!allowances.containsKey(command.type())) {
            throw new AbsenceRejected();
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

    public int remainingDays(String name) {
        return allowances.values().stream()
                         .flatMap(List::stream)
                         .filter(allowance -> allowance.name().equals(name))
                         .findFirst().get()
                         .remainingDays();
    }
}
