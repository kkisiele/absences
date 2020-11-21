package com.kkisiele.absence;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TestFixture {
    public static Clock fixedClock(int year, int month, int day) {
        var zone = ZoneId.of("UTC");
        var datetime = LocalDateTime.of(year, month, day, 0, 0);
        var instant = ZonedDateTime
                .of(datetime, zone)
                .toInstant();
        return Clock.fixed(instant, zone);
    }

    public static DatePeriod period(String day) {
        return period(day, day);
    }

    public static DatePeriod period(String start, String end) {
        return new DatePeriod(LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE), LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public static DatePeriod daysToPeriod(int days, Clock clock) {
        final LocalDate now = LocalDate.now(clock);
        return new DatePeriod(now, now.plusDays(days - 1));
    }

    public static UUID uuid(String id) {
        return UUID.nameUUIDFromBytes(id.getBytes());
    }
}
