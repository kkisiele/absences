package com.kkisiele.absence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Clock;
import java.time.LocalDate;

import static com.kkisiele.absence.AbsenceState.APPROVAL_PENDING;
import static com.kkisiele.absence.AbsenceState.APPROVED;
import static com.kkisiele.absence.AbsenceType.HOLIDAY;
import static com.kkisiele.absence.AbsenceType.SICKNESS;
import static com.kkisiele.absence.TestUtils.fixedClock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbsenceTest {
    private Clock clock = fixedClock(2020, 11, 11);

    @Test
    void requestingSicknessResultsInApprovedAbsence() {
        //given
        var employee = new Employee(new AllWorkingDaysCalendar(), clock);
        //when
        employee.request(periodOf3Days(), SICKNESS);
        //then
        assertEquals(1, employee
                .absences()
                .size());
        assertEquals(APPROVED, employee
                .absences()
                .get(0)
                .state());
    }

    @Test
    void requestingHolidayByEmployeeResultsInApprovalPendingAbsence() {
        //given
        var allowance = new Allowance(26);
        var employee = new Employee(new AllWorkingDaysCalendar(), clock);
        employee.register(HOLIDAY, allowance);
        //when
        employee.request(periodOf3Days(), HOLIDAY);
        //then
        assertEquals(1, employee
                .absences()
                .size());
        assertEquals(APPROVAL_PENDING, employee
                .absences()
                .get(0)
                .state());
    }

    @Test
    void cannotRequestWhenPeriodOverlapsAnyOfExistingAbsences() {
        //given
        var employee = new Employee(new AllWorkingDaysCalendar(), clock);
        employee.request(periodOf3Days(), SICKNESS);
        //when
        employee.request(periodOf3Days(), HOLIDAY);
        //then
        assertEquals(1, employee
                .absences()
                .size());
    }

    @Test
    void requestingHolidayDeducesRemainingDays() {
        //given
        var allowance = new Allowance(26);
        var employee = new Employee(new AllWorkingDaysCalendar(), clock);
        employee.register(HOLIDAY, allowance);
        //when
        employee.request(periodOf3Days(), HOLIDAY);
        //then
        assertEquals(23, allowance.remainingDays());
    }

    @Test
    void cannotRequestMoreDaysThanAvailable() {
        //given
        var employee = new Employee(new AllWorkingDaysCalendar(), clock);
        employee.register(HOLIDAY, new Allowance(2));
        //when
        Executable code = () -> employee.request(periodOf3Days(), HOLIDAY);
        //then
        assertThrows(RequestRejected.class, code);
    }

    private DatePeriod periodOf3Days() {
        return new DatePeriod(
                LocalDate.of(2020, 11, 16),
                LocalDate.of(2020, 11, 18)
        );
    }
}
