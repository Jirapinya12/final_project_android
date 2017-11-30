package jirapinya58070014.kmitl.unify.test.descriptionTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateDescriptionTripEmpty implements MyValidator {

    @Override
    public boolean isValid(String input) {
        return "".equals(input.trim());
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Description trip is empty.";
    }
}
