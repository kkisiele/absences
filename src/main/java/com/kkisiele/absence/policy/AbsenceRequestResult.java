package com.kkisiele.absence.policy;

import com.kkisiele.absence.AbsenceRejectionReason;

public class AbsenceRequestResult {
    private boolean succeed;
    private AbsenceRejectionReason reason;

    public AbsenceRequestResult(boolean result) {
        this.succeed = result;
    }

    public AbsenceRequestResult(boolean succeed, AbsenceRejectionReason reason) {
        this.succeed = succeed;
        this.reason = reason;
    }

    public static AbsenceRequestResult failed(AbsenceRejectionReason reason) {
        return new AbsenceRequestResult(false, reason);
    }

    public static AbsenceRequestResult succeed() {
        return new AbsenceRequestResult(true);
    }

    public boolean failed() {
        return !succeed;
    }

    public AbsenceRejectionReason reason() {
        return reason;
    }
}
