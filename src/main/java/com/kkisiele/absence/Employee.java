package com.kkisiele.absence;

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

    public void request(DatePeriod period, AbsenceType type) {
        if (overlaps(period)) {
            return;
        }
        int deductibleDays = period.numberOfWorkingDays(calendar);
        if (type.deductible()) {
            if (!allowances.get(type).hasEnoughDays(deductibleDays)) {
                throw new RequestRejected();
            }
            allowances.get(type).request(deductibleDays);
        }
        absences.add(new Absence(period, deductibleDays, type, type.workflow().initialState()));
    }

    public void cancel(UUID absenceId) {
        Absence absence = absences.stream().filter(a -> a.id().equals(absenceId)).findAny().get();
        allowances.get(absence.type()).cancel(absence.days());
        absences.remove(absence);
    }

    private boolean overlaps(DatePeriod period) {
        return absences.stream().anyMatch(absence -> absence.overlaps(period));
    }

    public List<Absence> absences() {
        return absences;
    }
}
