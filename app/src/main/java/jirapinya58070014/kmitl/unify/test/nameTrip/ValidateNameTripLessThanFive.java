package jirapinya58070014.kmitl.unify.test.nameTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateNameTripLessThanFive implements MyValidator {

    @Override
    public boolean isValid(String name) {
        return (name.length() < 5);
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Name trip is less than 5.";
    }
}
