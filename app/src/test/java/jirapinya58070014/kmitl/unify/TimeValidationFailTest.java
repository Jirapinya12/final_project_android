package jirapinya58070014.kmitl.unify;

import org.junit.Test;

import jirapinya58070014.kmitl.unify.test.timeTrip.ValidateTimeTripEmpty;
import jirapinya58070014.kmitl.unify.test.timeTrip.ValidateTimeTripNull;

import static junit.framework.Assert.assertTrue;

public class TimeValidationFailTest {

    @Test
    public void TimeIsEmpty() {
        ValidateTimeTripEmpty validation = new ValidateTimeTripEmpty();
        boolean result = validation.isValid("");
        assertTrue(validation.getErrorMessage(), result);
    }

    @Test
    public void TimeIsNull() {
        ValidateTimeTripNull validation = new ValidateTimeTripNull();
        boolean result = validation.isValid(null);
        assertTrue(validation.getErrorMessage(), result);
    }

}
