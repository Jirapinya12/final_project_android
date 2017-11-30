package jirapinya58070014.kmitl.unify.controller;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import jirapinya58070014.kmitl.unify.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddNewTripTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addTripEmptyLocationTest() {

        ViewInteraction AddButton = onView(withId(R.id.addBtn));
        AddButton.perform(click());

        //name
        ViewInteraction edNameTrip = onView(withId(R.id.edNameTrip));
        edNameTrip.perform(scrollTo(), replaceText("Final Exam KMITL"), closeSoftKeyboard());

        //description
        ViewInteraction edDescriptionTrip = onView(withId(R.id.edDescriptionTrip));
        edDescriptionTrip.perform(scrollTo(), replaceText("มาอ่านหนังสือกันนะเพื่อนๆ"), closeSoftKeyboard());

        //time
        ViewInteraction pickTime = onView(withId(R.id.pickTime));
        pickTime.perform(click());

        ViewInteraction confirmBeginTimeButton = onView(withId(android.R.id.button1));
        confirmBeginTimeButton.perform(scrollTo(), click());

        //beginDate
        ViewInteraction pickBeginDate = onView(withId(R.id.pickBeginDate));
        pickBeginDate.perform(click());

        ViewInteraction confirmBeginDateButton = onView(withId(android.R.id.button1));
        confirmBeginDateButton.perform(scrollTo(), click());

        //endDate
        ViewInteraction pickEndDate = onView(withId(R.id.pickEndDate));
        pickEndDate.perform(click());

        ViewInteraction confirmEndDateButton = onView(withId(android.R.id.button1));
        confirmEndDateButton.perform(scrollTo(), click());

        //save
        ViewInteraction saveBtn = onView(withId(R.id.btnSave));
        saveBtn.perform(click());

        //Check
        onView(withText("Fail! Location trip is empty.")).inRoot(withDecorView(not(mActivityTestRule
                .getActivity()
                .getWindow()
                .getDecorView()))).check(matches(isDisplayed()));

        //Check
        //onView(withText("Saved successfully.")).check(matches(isDisplayed()));
    }
}
