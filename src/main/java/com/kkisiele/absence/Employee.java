package com.kkisiele.absence;

import com.kkisiele.absence.policy.AbsenceRequestPolicy;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Employee {
    private final Calendar calendar;
    private final Clock clock;
    private final Map<UUID, Absence> absences = new HashMap<>();
    private Map<AbsenceType, Allowance> allowances = new HashMap<>();

    public Employee(Calendar calendar, Clock clock) {
        this.calendar = calendar;
        this.clock = clock;
    }

    public void register(AbsenceType type, LimitedAllowance allowance) {
        allowances.put(type, allowance);
    }

    public void request(RequestAbsence command, AbsenceRequestPolicy requestPolicy) {
        if (overlaps(command.period())) {
            return;
        }
        var absence = new Absence(UUID.randomUUID());
        absence.request(command, command.type().workflow(), allowance(command), calendar, requestPolicy);
        absences.put(absence.id(), absence);
    }

    private Allowance allowance(RequestAbsence command) {
        return allowances.getOrDefault(command.type(), Allowance.UNLIMITED);
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
}
