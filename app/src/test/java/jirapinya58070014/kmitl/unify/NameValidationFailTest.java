package jirapinya58070014.kmitl.unify;

import org.junit.Test;

import jirapinya58070014.kmitl.unify.test.nameTrip.ValidateNameTripContainNumber;
import jirapinya58070014.kmitl.unify.test.nameTrip.ValidateNameTripEmpty;
import jirapinya58070014.kmitl.unify.test.nameTrip.ValidateNameTripLessThanFive;
import jirapinya58070014.kmitl.unify.test.nameTrip.ValidateNameTripNull;
import jirapinya58070014.kmitl.unify.test.nameTrip.ValidateNameTripSpecialsAlphabet;

import static junit.framework.Assert.assertTrue;

public class NameValidationFailTest {

    @Test
    public void NameIsEmpty() {
        ValidateNameTripEmpty validation = new ValidateNameTripEmpty();
        boolean result = validation.isValid("");
        assertTrue(validation.getErrorMessage(), result);
    }

    @Test
    public void NameIsNull() {
        ValidateNameTripNull validation = new ValidateNameTripNull();
        boolean result = validation.isValid(null);
        assertTrue(validation.getErrorMessage(), result);
    }

    @Test
    public void NameHaveSpecialsAlphabet() {
        ValidateNameTripSpecialsAlphabet validation = new ValidateNameTripSpecialsAlphabet();
        boolean result = validation.isValid("@>N.S<@");
        assertTrue(validation.getErrorMessage(), result);
    }

    @Test
    public void NameContainNumber() {
        ValidateNameTripContainNumber validation = new ValidateNameTripContainNumber();
        boolean result = validation.isValid("12123saii12121");
        assertTrue(validation.getErrorMessage(), result);
    }

    @Test
    public void NameIsLessThanFive() {
        ValidateNameTripLessThanFive validation = new ValidateNameTripLessThanFive();
        boolean result = validation.isValid("abc");
        assertTrue(validation.getErrorMessage(), result);
    }

}
