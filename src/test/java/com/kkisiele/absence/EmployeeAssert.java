package com.kkisiele.absence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeAssert {
    private final EmployeeResult result;

    public EmployeeAssert(EmployeeResult result) {
        this.result = result;
    }

    public EmployeeAssert hasAbsenceRequestedInState(AbsenceState state) {
        assertNumberOfRequestedAbsences(1);
        assertEquals(state, absences().get(0).state());
        return this;
    }

    private EmployeeAssert assertNumberOfRequestedAbsences(int count) {
        assertEquals(count, absences().size());
        return this;
    }

    private List<Absence> absences() {
        return result().absences();
    }

    public EmployeeAssert hasGivenNumberOfRequestedAbsences(int count) {
        assertEquals(count, absences().size());
        return this;
    }

    public EmployeeAssert hasGivenNumberOfRemainingDays(String name, int days) {
        assertEquals(days, result().remainingDays(name));
        return this;
    }

    public EmployeeAssert doesNotHaveAbsenceRequested() {
        assertTrue(thrownException().getClass() == RequestRejected.class);
        return this;
    }

    private Employee result() {
        return result.employee;
    }

    private Throwable thrownException() {
        return result.thrownException;
    }

    public static class EmployeeResult {
        public final Employee employee;
        public final Throwable thrownException;

        public EmployeeResult(Employee employee) {
            this(employee, null);
        }

        public EmployeeResult(Employee employee, Throwable thrownException) {
            this.employee = employee;
            this.thrownException = thrownException;
        }
    }
}
