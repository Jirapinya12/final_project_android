package jirapinya58070014.kmitl.unify;

import org.junit.Test;

import jirapinya58070014.kmitl.unify.test.nameTrip.ValidateNameTripEmpty;
import jirapinya58070014.kmitl.unify.test.nameTrip.ValidateNameTripNull;

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

}
