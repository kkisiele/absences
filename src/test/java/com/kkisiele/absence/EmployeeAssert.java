package com.kkisiele.absence;

import java.util.List;
import java.util.Objects;

import static com.kkisiele.absence.AbsenceState.APPROVED;
import static com.kkisiele.absence.TestFixture.uuid;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    public EmployeeAssert hasGivenNumberOfRequestedAbsences(int count) {
        assertEquals(count, absences().size());
        return this;
    }

    public EmployeeAssert hasGivenNumberOfRemainingDays(AbsenceType type, int days) {
        assertEquals(days, result().remainingDays(type));
        return this;
    }

    public EmployeeAssert failedToRequestAbsenceBecauseOf(AbsenceRejectionReason expectedReason) {
        AbsenceRejected ex = (AbsenceRejected) thrownException();
        assertEquals(expectedReason, ex.reason());
        return this;
    }

    public EmployeeAssert hasApprovedAbsence(String id) {
        assertEquals(APPROVED, absence(id).state());
        return this;
    }

    private Employee result() {
        return result.employee;
    }

    private Throwable thrownException() {
        return result.thrownException;
    }

    private List<Absence> absences() {
        return result().absences();
    }

    private Absence absence(String id) {
        return absences().stream()
                         .filter(absence -> Objects.equals(absence.id(), uuid(id)))
                         .findFirst()
                         .get();
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
