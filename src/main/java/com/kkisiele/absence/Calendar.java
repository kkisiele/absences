package com.kkisiele.absence;

import java.time.LocalDate;

public interface Calendar {
    boolean workingDay(LocalDate day);
}
