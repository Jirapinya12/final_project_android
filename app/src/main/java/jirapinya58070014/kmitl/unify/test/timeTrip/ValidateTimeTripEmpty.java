package jirapinya58070014.kmitl.unify.test.timeTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateTimeTripEmpty implements MyValidator {

    @Override
    public boolean isValid(String time) {
        return "".equals(time.trim());
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Time trip is empty.";
    }
}
