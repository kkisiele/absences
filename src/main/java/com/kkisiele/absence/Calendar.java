package com.kkisiele.absence;

import java.time.LocalDate;

interface Calendar {
    boolean workingDay(LocalDate day);
}
