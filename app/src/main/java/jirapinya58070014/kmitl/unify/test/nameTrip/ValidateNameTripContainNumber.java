package jirapinya58070014.kmitl.unify.test.nameTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateNameTripContainNumber implements MyValidator {

    @Override
    public boolean isValid(String name) {
        return (name.matches(".*\\d+.*"));
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Name trip have contain number.";
    }
}
