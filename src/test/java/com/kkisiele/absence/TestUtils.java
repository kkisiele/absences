package com.kkisiele.absence;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestUtils {
    public static Clock fixedClock(int year, int month, int day) {
        var zone = ZoneId.of("UTC");
        var datetime = LocalDateTime.of(year, month, day, 0, 0);
        var instant = ZonedDateTime
                .of(datetime, zone)
                .toInstant();
        return Clock.fixed(instant, zone);
    }
}
