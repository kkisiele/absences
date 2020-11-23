package com.kkisiele.absence;

public class AbsenceRejected extends RuntimeException {
    private final AbsenceRejectionReason reason;

    public AbsenceRejected() {
        this(null);
    }

    public AbsenceRejected(AbsenceRejectionReason reason) {
        this.reason = reason;
    }

    public AbsenceRejectionReason reason() {
        return reason;
    }
}
