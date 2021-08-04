package com.kkisiele.absence;

import java.time.Clock;
import java.util.Arrays;
import java.util.UUID;

import static com.kkisiele.absence.TestFixture.uuid;
import static com.kkisiele.absence.policy.AbsencePolicies.allowed;

public class EmployeeBuilder {
    private final Employee employee;
    private Allowance allowance;

    public EmployeeBuilder(Clock clock) {
        this.employee = new Employee(new AllWorkingDaysCalendar(), clock);
    }

    public EmployeeBuilder havingDeductibleDays(int days) {
        return havingDeductibleDays("default", days);
    }

    public EmployeeBuilder havingDeductibleDays(String name, int days) {
        allowance = new Allowance(name, days);
        return this;
    }

    public EmployeeBuilder havingUnlimitedDays() {
        return havingDeductibleDays(Integer.MAX_VALUE);
    }

    public EmployeeBuilder handledBy(AbsenceType... types) {
        Arrays.asList(types).forEach(type -> employee.register(type, allowance));
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
