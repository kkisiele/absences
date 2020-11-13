package com.kkisiele.absence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static com.kkisiele.absence.AbsenceState.APPROVAL_PENDING;
import static com.kkisiele.absence.AbsenceState.APPROVED;
import static com.kkisiele.absence.AbsenceType.HOLIDAY;
import static com.kkisiele.absence.AbsenceType.SICKNESS;
import static com.kkisiele.absence.TestUtils.fixedClock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbsenceTest {
    private final Clock clock = fixedClock(2020, 11, 11);
    private Employee employee;
    private Allowance allowance;

    @Test
    void requestingSicknessResultsInApprovedAbsence() {
        //given
        employee(hasUnlimitedSicknessDays());
        //when
        requestSicknessDays(3);
        //then
        assertAbsenceRequestedInState(APPROVED);
    }

    @Test
    void requestingHolidayByEmployeeResultsInApprovalPendingAbsence() {
        //given
        employee(hasLimitedHolidayDays(26));
        //when
        requestHolidayDays(3);
        //then
        assertAbsenceRequestedInState(APPROVAL_PENDING);
    }

    @Test
    void cannotRequestWhenPeriodOverlapsAnyOfExistingAbsences() {
        //given
        employee(hasLimitedHolidayDays(26));
        requestSicknessDays(3);
        //when
        requestHolidayDays(3);
        //then
        assertNumberOfRequestedAbsences(1);
    }

    @Test
    void requestingHolidayDeducesRemainingDays() {
        //given
        employee(hasLimitedHolidayDays(26));
        //when
        requestHolidayDays(3);
        //then
        assertNumberOfRemainingHolidayDays(23);
    }

    @Test
    void cannotRequestMoreDaysThanAvailable() {
        //given
        employee(hasLimitedHolidayDays(2));
        //when
        Executable code = () -> requestHolidayDays(3);
        //then
        assertThrows(RequestRejected.class, code);
    }

    private void employee(AllowanceOfType allowanceOfType) {
        var employee = new Employee(new AllWorkingDaysCalendar(), clock);
        this.employee = employee;
        if (allowanceOfType != null) {
            employee.register(allowanceOfType.type, allowanceOfType.allowance);
            this.allowance = allowanceOfType.allowance;
        }
    }

    private AllowanceOfType hasLimitedHolidayDays(int days) {
        var allowance = new Allowance(days);
        return new AllowanceOfType(allowance, HOLIDAY);
    }

    private AllowanceOfType hasUnlimitedSicknessDays() {
        return null;
    }

    private void requestHolidayDays(int days) {
        request(days, HOLIDAY);
    }

    private void requestSicknessDays(int days) {
        request(days, SICKNESS);
    }

    private void request(int days, AbsenceType type) {
        employee.request(daysToSomePeriod(days), type);
    }

    private DatePeriod daysToSomePeriod(int days) {
        final LocalDate now = LocalDate.now(clock);
        return new DatePeriod(now, now.plusDays(days - 1));
    }

    private void assertAbsenceRequestedInState(AbsenceState state) {
        assertNumberOfRequestedAbsences(1);
        assertEquals(state, absences().get(0).state());
    }

    private void assertNumberOfRequestedAbsences(int count) {
        assertEquals(count, absences().size());
    }

    private List<Absence> absences() {
        return employee.absences();
    }

    private void assertNumberOfRemainingHolidayDays(int days) {
        assertEquals(days, allowance.remainingDays());
    }

    private static class AllowanceOfType {
        public final Allowance allowance;
        public final AbsenceType type;

        public AllowanceOfType(Allowance allowance, AbsenceType type) {
            this.allowance = allowance;
            this.type = type;
        }
    }
}
