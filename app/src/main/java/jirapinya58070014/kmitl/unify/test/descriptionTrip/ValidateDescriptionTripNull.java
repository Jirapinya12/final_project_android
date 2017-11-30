package jirapinya58070014.kmitl.unify.test.descriptionTrip;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateDescriptionTripNull implements MyValidator {

    @Override
    public boolean isValid(String name) {
        return (name == null);
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Description trip is null.";
    }
}
