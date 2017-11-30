package jirapinya58070014.kmitl.unify.test.nameTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateNameTripEmpty implements MyValidator {

    @Override
    public boolean isValid(String description) {
        return "".equals(description.trim());
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Name trip is empty.";
    }
}
