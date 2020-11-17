package com.kkisiele.absence;

import java.time.LocalDate;

interface Calendar {
    boolean workingDay(LocalDate day);

    default int numberOfWorkingDays(Iterable<LocalDate> days) {
        int result = 0;
        for (LocalDate day : days) {
            if (workingDay(day)) ++result;
        }
        return result;
    }
}
