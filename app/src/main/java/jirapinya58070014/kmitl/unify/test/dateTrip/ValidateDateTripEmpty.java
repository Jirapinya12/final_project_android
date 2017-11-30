package jirapinya58070014.kmitl.unify.test.dateTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateDateTripEmpty implements MyValidator {

    @Override
    public boolean isValid(String date) {
        return "".equals(date);
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Date trip is empty.";
    }
}
