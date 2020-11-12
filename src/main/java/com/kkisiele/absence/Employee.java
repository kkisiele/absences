package com.kkisiele.absence;

import java.time.Clock;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public void request(DatePeriod period, AbsenceType type) {
        if (overlaps(period)) {
            return;
        }
        int requestedDays = period.numberOfWorkingDays(calendar);
        if (type.deductible()) {
            boolean ok = allowances
                    .get(type)
                    .reduceBy(requestedDays);
            if (!ok) {
                throw new RequestRejected();
            }
        }
        absences.add(new Absence(period, requestedDays, type, type
                .workflow()
                .initialState()));
    }

    private boolean overlaps(DatePeriod period) {
        return absences
                .stream()
                .anyMatch(absence -> absence.overlaps(period));
    }

    public List<Absence> absences() {
        return absences;
    }
}
