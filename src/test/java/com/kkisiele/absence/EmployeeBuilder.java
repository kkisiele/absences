package com.kkisiele.absence;

import java.time.Clock;
import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

import static com.kkisiele.absence.TestFixture.uuid;
import static com.kkisiele.absence.policy.AbsencePolicies.allowed;

public class EmployeeBuilder {
    private final Employee employee;
    private Deque<Allowance> addedAllowances = new LinkedList<>();

    public EmployeeBuilder(Clock clock) {
        this.employee = new Employee(new AllWorkingDaysCalendar(), clock);
    }

    public EmployeeBuilder havingDeductibleDays(AbsenceType type, int days) {
        return setupAllowance(type, new FixedDaysAllowance(days));
    }

    public EmployeeBuilder including(AbsenceType type, int days) {
        return setupAllowance(type, new CoupledAllowance(new FixedDaysAllowance(days), addedAllowances.getLast()));
    }

    public EmployeeBuilder havingUnlimitedDays(AbsenceType type) {
        return setupAllowance(type, new UnlimitedAllowance());
    }

    private EmployeeBuilder setupAllowance(AbsenceType type, Allowance allowance) {
        employee.register(type, allowance);
        addedAllowances.add(allowance);
        return this;
    }

    public EmployeeBuilder havingRequestedAbsence(DatePeriod period, AbsenceType type) {
        return havingRequestedAbsence(UUID.randomUUID(), period, type);
    }

    public EmployeeBuilder havingRequestedAbsence(String id, DatePeriod period, AbsenceType type) {
        return havingRequestedAbsence(uuid(id), period, type);
    }

    public EmployeeBuilder havingRequestedAbsence(UUID id, DatePeriod period, AbsenceType type) {
        employee.request(new RequestAbsence(id, period, type), new InitialAbsenceWorkflow(AbsenceState.APPROVED), allowed());
        return this;
    }

    public Employee build() {
        return employee;
    }
}
