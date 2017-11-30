package jirapinya58070014.kmitl.unify.test.dateTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateDateTripNull implements MyValidator {

    @Override
    public boolean isValid(String date) {
        return (date == null);
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Date trip is null.";
    }
}
