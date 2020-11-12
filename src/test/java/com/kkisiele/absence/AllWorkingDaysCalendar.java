package com.kkisiele.absence;

import java.time.LocalDate;

public class AllWorkingDaysCalendar implements Calendar {
    @Override
    public boolean workingDay(LocalDate day) {
        return true;
    }
}
