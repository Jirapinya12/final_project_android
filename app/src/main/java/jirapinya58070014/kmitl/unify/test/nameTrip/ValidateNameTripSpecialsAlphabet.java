package jirapinya58070014.kmitl.unify.test.nameTrip;

import java.util.regex.Pattern;

import jirapinya58070014.kmitl.unify.test.MyValidator;

public class ValidateNameTripSpecialsAlphabet implements MyValidator {

    @Override
    public boolean isValid(String description) {
        return(!Pattern.matches("[a-zA-Z]+", description));
    }

    @Override
    public String getErrorMessage() {
        return "Fail! Name trip is specials alphabet.";
    }
}
