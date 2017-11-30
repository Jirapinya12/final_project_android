package jirapinya58070014.kmitl.unify.test.timeTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateTimeTripNull implements MyValidator {

    @Override
    public boolean isValid(String time) {
        return time == null;
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Time trip is null.";
    }
}
