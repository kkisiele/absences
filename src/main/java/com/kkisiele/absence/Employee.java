package com.kkisiele.absence;

import com.kkisiele.absence.policy.AbsenceRequestPolicy;

import java.time.Clock;
import java.util.*;

public class Employee {
    private final Calendar calendar;
    private final Clock clock;
    private final List<Absence> absences = new LinkedList<>();
    private Map<AbsenceType, Allowance> allowances = new HashMap<>();

    public Employee(Calendar calendar, Clock clock) {
        this.calendar = calendar;
        this.clock = clock;
    }

    public void register(AbsenceType type, Allowance allowance) {
        allowances.put(type, allowance);
    }

    public void request(RequestAbsence command, AbsenceRequestPolicy requestPolicy) {
        if (overlaps(command.period())) {
            return;
        }
        var absence = new Absence(UUID.randomUUID());
        absence.request(command, command.type().workflow(), allowances.get(command.type()), calendar, requestPolicy);
        absences.add(absence);
    }

    public void cancel(UUID absenceId) {
        Absence absence = absences.stream().filter(a -> a.id().equals(absenceId)).findAny().get();
        absence.cancel();
        absences.remove(absence);
    }

    private boolean overlaps(DatePeriod period) {
        return absences.stream().anyMatch(absence -> absence.overlaps(period));
    }

    public List<Absence> absences() {
        return absences;
    }
}
