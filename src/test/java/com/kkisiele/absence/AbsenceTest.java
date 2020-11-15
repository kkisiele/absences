package com.kkisiele.absence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

import static com.kkisiele.absence.AbsenceState.APPROVAL_PENDING;
import static com.kkisiele.absence.AbsenceState.APPROVED;
import static com.kkisiele.absence.AbsenceType.*;
import static com.kkisiele.absence.TestUtils.fixedClock;
import static com.kkisiele.absence.policy.AbsencePolicies.absenceStartsIn;
import static com.kkisiele.absence.policy.AbsencePolicies.allowanceHardLimit;
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
        assertNumberOfRemainingDays(23);
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

    @Test
    void cancellingAbsenceRefundRequestedDays() {
        //given
        employee(hasLimitedHolidayDays(26));
        requestHolidayDays(3);
        //when
        employee.cancel(absences().get(0).id());
        //then
        assertNumberOfRemainingDays(26);
    }

    @Test
    void absenceCanBeRequestedOnlyInGivenPeriod() {
        //given
        employee(
                hasDeductibleDays(1, SPECIAL),
                requestedAbsenceStartsIn(datePeriod("2020-09-01", "2020-09-11"))
        );
        //when
        Executable code = () -> request(datePeriod("2020-09-23", "2020-09-23"), SPECIAL);
        //then
        assertThrows(RequestRejected.class, code);
    }

    private Consumer<Employee> requestedAbsenceStartsIn(DatePeriod period) {
        return e -> e.addPolicy(absenceStartsIn(period));
    }

    private DatePeriod datePeriod(String start, String end) {
        return new DatePeriod(LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE), LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE));
    }

    private void employee(Consumer<Employee>... configureHandles) {
        this.employee = new Employee(new AllWorkingDaysCalendar(), clock);
        employee.addPolicy(allowanceHardLimit());
        for (Consumer<Employee> configure : configureHandles) {
            configure.accept(employee);
        }
    }

    private Consumer<Employee> hasLimitedHolidayDays(int days) {
        return hasDeductibleDays(days, HOLIDAY);
    }

    private Consumer<Employee> hasDeductibleDays(int days, AbsenceType type) {
        return e -> {
            allowance = new Allowance(days);
            e.register(type, allowance);
        };
    }

    private Consumer<Employee> hasUnlimitedSicknessDays() {
        return e -> {
        };
    }

    private void requestHolidayDays(int days) {
        request(days, HOLIDAY);
    }

    private void requestSicknessDays(int days) {
        request(days, SICKNESS);
    }

    private void request(int days, AbsenceType type) {
        request(daysToSomePeriod(days), type);
    }

    private void request(DatePeriod period, AbsenceType type) {
        employee.request(new RequestAbsence(period, type));
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

    private void assertNumberOfRemainingDays(int days) {
        assertEquals(days, allowance.remainingDays());
    }
}
