package com.kkisiele.absence;

import java.time.LocalDate;

public final class DatePeriod {
    private final LocalDate start;
    private final LocalDate end;

    public DatePeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate start() {
        return start;
    }

    public LocalDate end() {
        return end;
    }

    public int numberOfWorkingDays(Calendar calendar) {
        int days = 0;
        LocalDate day = start;
        while (day.compareTo(end) <= 0) {
            if (calendar.workingDay(day)) days++;
            day = day.plusDays(1);
        }
        return days;
    }

    public boolean overlaps(DatePeriod period) {
        boolean disjunctive = period.start.isAfter(end) || period.end.isBefore(start);
        return !disjunctive;
    }

    public boolean contains(LocalDate day) {
        return day.compareTo(start) >= 0 && day.compareTo(end) <= 0;
    }
}
