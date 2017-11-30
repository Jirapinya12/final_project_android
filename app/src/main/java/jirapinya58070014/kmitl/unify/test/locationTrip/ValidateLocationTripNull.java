package jirapinya58070014.kmitl.unify.test.locationTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateLocationTripNull implements MyValidator {

    @Override
    public boolean isValid(String name) {
        return (name == null);
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Location trip is null.";
    }
}
