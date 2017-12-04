
package jirapinya58070014.kmitl.unify;


import android.os.SystemClock;
import android.support.test.espresso.DataInteraction;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import jirapinya58070014.kmitl.unify.R;
import jirapinya58070014.kmitl.unify.controller.LoginActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UnifyEspressoTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void login(){
        ViewInteraction loginButton = onView(withId(R.id.login_button));
        loginButton.perform(click());
    }

    @After
    public void logout(){
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        4),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
    }

    public void updateInformation(){
        ViewInteraction edNameTrip = onView(withId(R.id.edNameTrip));
        edNameTrip.perform(scrollTo(), replaceText("TestTripUpdate"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.edDescriptionTrip));
        appCompatEditText2.perform(scrollTo(), replaceText("ไปเที่ยวกันUpdate"), closeSoftKeyboard());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.pickTime),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction okButton = onView(
                allOf(withId(android.R.id.button1), withText("ตกลง"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        okButton.perform(scrollTo(), click());

        ViewInteraction pickBeginDate = onView(
                allOf(withId(R.id.pickBeginDate),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        pickBeginDate.perform(click());
        okButton.perform(scrollTo(), click());

        ViewInteraction pickEndDate = onView(
                allOf(withId(R.id.pickEndDate),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        pickEndDate.perform(click());
        okButton.perform(scrollTo(), click());
    }

    public void setInformation(){
        ViewInteraction edNameTrip = onView(withId(R.id.edNameTrip));
        edNameTrip.perform(scrollTo(), replaceText("TestTrip"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.edDescriptionTrip));
        appCompatEditText2.perform(scrollTo(), replaceText("ไปเที่ยวกัน"), closeSoftKeyboard());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.pickTime),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction okButton = onView(
                allOf(withId(android.R.id.button1), withText("ตกลง"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        okButton.perform(scrollTo(), click());

        ViewInteraction pickBeginDate = onView(
                allOf(withId(R.id.pickBeginDate),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        pickBeginDate.perform(click());
        okButton.perform(scrollTo(), click());

        ViewInteraction pickEndDate = onView(
                allOf(withId(R.id.pickEndDate),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        pickEndDate.perform(click());
        okButton.perform(scrollTo(), click());
    }

    public void addTrip(){
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.addBtn),
                        childAtPosition(
                                allOf(withId(R.id.relativeListview),
                                        withParent(withId(R.id.container))),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        setInformation();

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.btnSave),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                3),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        SystemClock.sleep(2000);
    }

    public void editTrip(){
        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.btnEdit), withText("EDIT"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatTextView4.perform(click());
        SystemClock.sleep(2000);

        updateInformation();

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.btnUpdate),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton3.perform(click());
        SystemClock.sleep(2000);
    }
    @Test
    public void unifyEspressoTest() {
        //addTrip
        addTrip();

        //clickTrip
        DataInteraction tripItem = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView),
                        childAtPosition(
                                withId(R.id.relativeListview),
                                0)))
                .atPosition(2);
        tripItem.perform(click());
        SystemClock.sleep(1000);

        //editTrip
        editTrip();

        //clickTrip
        tripItem.perform(scrollTo(),click());
        SystemClock.sleep(1000);

        //showCompanions
        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.companionsBtn)));
        appCompatTextView5.perform(scrollTo(),click());
        SystemClock.sleep(1000);

        //inviteBtn
        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.inviteBtn)));
        floatingActionButton4.perform(click());
        SystemClock.sleep(1000);

        //inviteFriend
        DataInteraction friendsListItem = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(0);
        friendsListItem.perform(click());
        SystemClock.sleep(1000);

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton4.perform(scrollTo(), click());

        //edit
        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(R.id.btnEdit), withText("EDIT"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatTextView6.perform(click());

        //delete
        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(R.id.btnDelete), withText("DELETE"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatTextView7.perform(click());


        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton5.perform(scrollTo(), click());

        //back
        ViewInteraction appCompatTextView9 = onView(
                allOf(withId(R.id.btnBack), withText("BACK"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatTextView9.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
