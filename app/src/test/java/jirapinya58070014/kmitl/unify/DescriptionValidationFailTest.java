package jirapinya58070014.kmitl.unify;

import org.junit.Test;

import jirapinya58070014.kmitl.unify.test.descriptionTrip.ValidateDescriptionTripEmpty;
import jirapinya58070014.kmitl.unify.test.descriptionTrip.ValidateDescriptionTripNull;
import static junit.framework.Assert.assertTrue;

public class DescriptionValidationFailTest {

    @Test
    public void DescriptionIsEmpty() {
        ValidateDescriptionTripEmpty validation = new ValidateDescriptionTripEmpty();
        boolean result = validation.isValid("");
        assertTrue(validation.getErrorMessage(), result);
    }

    @Test
    public void DescriptionIsNull() {
        ValidateDescriptionTripNull validation = new ValidateDescriptionTripNull();
        boolean result = validation.isValid(null);
        assertTrue(validation.getErrorMessage(), result);
    }

}
