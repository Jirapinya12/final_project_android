package jirapinya58070014.kmitl.unify;

import org.junit.Test;

import jirapinya58070014.kmitl.unify.test.dateTrip.ValidateDateTripEmpty;
import jirapinya58070014.kmitl.unify.test.dateTrip.ValidateDateTripNull;

import static junit.framework.Assert.assertTrue;

public class DateValidationFailTest {

    @Test
    public void DateIsEmpty() {
        ValidateDateTripEmpty validation = new ValidateDateTripEmpty();
        boolean result = validation.isValid("");
        assertTrue(validation.getErrorMessage(), result);
    }

    @Test
    public void DateIsNull() {
        ValidateDateTripNull validation = new ValidateDateTripNull();
        boolean result = validation.isValid(null);
        assertTrue(validation.getErrorMessage(), result);
    }

}
