package jirapinya58070014.kmitl.unify.test.locationTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateLocationTripEmpty implements MyValidator {

    @Override
    public boolean isValid(String description) {
        return "".equals(description.trim());
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Location trip is empty.";
    }
}
