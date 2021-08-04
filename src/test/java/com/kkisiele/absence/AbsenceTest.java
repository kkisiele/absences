package com.kkisiele.absence;

import com.kkisiele.absence.policy.AbsenceRequestPolicy;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static com.kkisiele.absence.AbsenceRejectionReason.*;
import static com.kkisiele.absence.AbsenceState.APPROVAL_PENDING;
import static com.kkisiele.absence.AbsenceState.APPROVED;
import static com.kkisiele.absence.AbsenceType.*;
import static com.kkisiele.absence.EmployeeAssert.EmployeeResult;
import static com.kkisiele.absence.TestFixture.*;
import static com.kkisiele.absence.policy.AbsencePolicies.*;

public class AbsenceTest {
    private static final AbsenceRequestPolicy REQUEST_POLICY_WHEN_NONE_PROVIDED = allowanceHardLimit();
    private static final Clock CLOCK = fixedClock(2020, 11, 11);

    private EmployeeBuilder builder;
    private List<AbsenceRequestPolicy> requestPolicies = new LinkedList<>();
    private EmployeeResult result;

    @Test
    void cannotRequestNotHandledAbsenceType() {
        givenEmployee();

        whenRequestAbsence(days(3), SICKNESS);

        thenEmployee()
                .failedToRequestAbsenceBecauseOf(ABSENCE_TYPE_NOT_SUPPORTED);
    }

    @Test
    void requestingSicknessResultsInApprovedAbsence() {
        givenEmployee()
                .havingUnlimitedDays().handledBy(SICKNESS);

        whenRequestAbsence(days(3), SICKNESS);

        thenEmployee()
                .hasAbsenceRequestedInState(APPROVED);
    }

    @Test
    void requestingHolidayByEmployeeResultsInApprovalPendingAbsence() {
        givenEmployee()
                .havingDeductibleDays(26).handledBy(HOLIDAY);

        whenRequestAbsence(days(3), HOLIDAY);

        thenEmployee()
                .hasAbsenceRequestedInState(APPROVAL_PENDING);
    }

    @Test
    void cannotRequestWhenPeriodOverlapsAnyOfExistingAbsences() {
        givenEmployee()
                .havingDeductibleDays("holiday", 26).handledBy(HOLIDAY)
                .havingUnlimitedDays().handledBy(SICKNESS)
                .havingRequestedAbsence(period("2020-09-01", "2020-09-05"), SICKNESS);

        whenRequestAbsence(period("2020-09-05", "2020-09-10"), HOLIDAY);

        thenEmployee()
                .failedToRequestAbsenceBecauseOf(OVERLAPS_EXISTING_ABSENCE)
                .hasGivenNumberOfRequestedAbsences(1)
                .hasGivenNumberOfRemainingDays("holiday", 26);
    }

    @Test
    void requestingHolidayDeducesRemainingDays() {
        givenEmployee()
                .havingDeductibleDays("holiday", 26).handledBy(HOLIDAY);

        whenRequestAbsence(days(3), HOLIDAY);

        thenEmployee()
                .hasGivenNumberOfRemainingDays("holiday", 23);
    }

    @Test
    void cannotRequestMoreDaysThanAvailable() {
        givenEmployee()
                .havingDeductibleDays("holiday", 2).handledBy(HOLIDAY);

        whenRequestAbsence(days(3), HOLIDAY);

        thenEmployee()
                .failedToRequestAbsenceBecauseOf(NOT_ENOUGH_DAYS_AVAILABLE);
    }

    @Test
    void cancellingAbsenceRefundRequestedDays() {
        givenEmployee()
                .havingDeductibleDays("holiday", 26).handledBy(HOLIDAY)
                .havingRequestedAbsence("id-1", days(3), HOLIDAY);

        whenCancelAbsence("id-1");

        thenEmployee()
                .hasGivenNumberOfRemainingDays("holiday", 26);
    }

    @Test
    void cannotRequestAbsenceOutOfGivenPeriod() {
        givenEmployee()
                .havingDeductibleDays("special", 1).handledBy(SPECIAL);
        givenRequestPolicy(absenceStartsIn(period("2020-09-01", "2020-09-11")));

        whenRequestAbsence(period("2020-09-23", "2020-09-23"), SPECIAL);

        thenEmployee()
                .failedToRequestAbsenceBecauseOf(NOT_START_IN_VALID_PERIOD);
    }

    @Test
    void absenceCanBeRequestedOnlyInGivenPeriod() {
        givenEmployee()
                .havingDeductibleDays("special", 1).handledBy(SPECIAL);
        givenRequestPolicy(absenceStartsIn(period("2020-09-01", "2020-09-11")));

        whenRequestAbsence(period("2020-09-01"), SPECIAL);

        thenEmployee()
                .hasGivenNumberOfRemainingDays("special", 0);
    }

    @Test
    void onDemandAbsenceDeducesFromBothOnDemandAndHolidayAllowances() {
        givenEmployee()
                .havingDeductibleDays("on-demand", 4).handledBy(ON_DEMAND)
                .havingDeductibleDays("holiday", 26).handledBy(HOLIDAY, ON_DEMAND);

        whenRequestAbsence(period("2020-09-01"), ON_DEMAND);

        thenEmployee()
                .hasGivenNumberOfRemainingDays("on-demand", 3)
                .hasGivenNumberOfRemainingDays("holiday", 25);
    }

    @Test
    void cannotRequestMoreOnDemandAbsencesThanAvailable() {
        givenEmployee()
                .havingDeductibleDays("on-demand", 0).handledBy(ON_DEMAND)
                .havingDeductibleDays("holiday", 20).handledBy(HOLIDAY, ON_DEMAND);

        whenRequestAbsence(period("2020-09-01"), ON_DEMAND);

        thenEmployee()
                .failedToRequestAbsenceBecauseOf(NOT_ENOUGH_DAYS_AVAILABLE);
    }

    @Test
    void approveAbsence() {
        givenEmployee()
                .havingDeductibleDays("holiday", 26).handledBy(HOLIDAY)
                .havingRequestedAbsence("id-1", days(3), HOLIDAY);

        whenApproveAbsence("id-1");

        thenEmployee()
                .hasApprovedAbsence("id-1")
                .hasGivenNumberOfRemainingDays("holiday", 23);
    }

    private EmployeeBuilder givenEmployee() {
        this.builder = new EmployeeBuilder(CLOCK);
        return this.builder;
    }

    private void givenRequestPolicy(AbsenceRequestPolicy requestPolicy) {
        this.requestPolicies.add(requestPolicy);
    }

    private void whenRequestAbsence(DatePeriod period, AbsenceType type) {
        whenRequestAbsence(UUID.randomUUID(), period, type);
    }

    private void whenRequestAbsence(UUID id, DatePeriod period, AbsenceType type) {
        onEmployee(e -> {
            AbsenceRequestPolicy requestPolicy = requestPolicies.isEmpty() ? REQUEST_POLICY_WHEN_NONE_PROVIDED : and(requestPolicies);
            e.request(new RequestAbsence(id, period, type), type.workflow(), requestPolicy);
        });
    }

    private void whenCancelAbsence(String id) {
        onEmployee(e -> e.cancel(uuid(id)));
    }

    private void whenApproveAbsence(String id) {
        onEmployee(e -> e.approve(uuid(id)));
    }

    private void onEmployee(Consumer<Employee> callback) {
        Employee employee = builder.build();
        try {
            callback.accept(employee);
            result = new EmployeeResult(employee);
        } catch (Exception ex) {
            result = new EmployeeResult(employee, ex);
        }
    }

    private EmployeeAssert thenEmployee() {
        return new EmployeeAssert(result);
    }

    private DatePeriod days(int days) {
        return daysToPeriod(days, CLOCK);
    }
}
