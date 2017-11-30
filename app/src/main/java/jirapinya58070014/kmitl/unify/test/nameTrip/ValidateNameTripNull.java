package jirapinya58070014.kmitl.unify.test.nameTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateNameTripNull implements MyValidator {

    @Override
    public boolean isValid(String name) {
        return (name == null);
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Name trip is null.";
    }
}
