package jirapinya58070014.kmitl.unify;

import org.junit.Test;

import jirapinya58070014.kmitl.unify.test.locationTrip.ValidateLocationTripEmpty;
import jirapinya58070014.kmitl.unify.test.locationTrip.ValidateLocationTripNull;

import static junit.framework.Assert.assertTrue;

public class LocationValidationFailTest {

    @Test
    public void LocationIsEmpty() {
        ValidateLocationTripEmpty validation = new ValidateLocationTripEmpty();
        boolean result = validation.isValid("");
        assertTrue(validation.getErrorMessage(), result);
    }

    @Test
    public void LocationIsNull() {
        ValidateLocationTripNull validation = new ValidateLocationTripNull();
        boolean result = validation.isValid(null);
        assertTrue(validation.getErrorMessage(), result);
    }

}
