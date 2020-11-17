package com.kkisiele.absence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class DatePeriod implements Iterable<LocalDate> {
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

    public boolean overlaps(DatePeriod period) {
        boolean disjunctive = period.start.isAfter(end) || period.end.isBefore(start);
        return !disjunctive;
    }

    public boolean contains(LocalDate day) {
        return day.compareTo(start) >= 0 && day.compareTo(end) <= 0;
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return days().iterator();
    }

    private List<LocalDate> days() {
        List<LocalDate> result = new ArrayList<>();
        LocalDate day = start;
        while (day.compareTo(end) <= 0) {
            result.add(day);
            day = day.plusDays(1);
        }
        return result;
    }
}
