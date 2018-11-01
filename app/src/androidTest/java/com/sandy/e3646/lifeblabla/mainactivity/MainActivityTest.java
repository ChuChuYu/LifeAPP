package com.sandy.e3646.lifeblabla.mainactivity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.sandy.e3646.lifeblabla.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
//        ViewInteraction appCompatButton = onView(
//                allOf(withId(R.id.button_skip), withText("skip"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                1),
//                        isDisplayed()));
//        appCompatButton.perform(click());

        for (int i = 0; i < 100; i++) {
            ViewInteraction toggleButton = onView(
                    allOf(withId(R.id.button_switch_layout),
                            childAtPosition(
                                    allOf(withId(R.id.whole_container),
                                            childAtPosition(
                                                    withId(android.R.id.content),
                                                    0)),
                                    5),
                            isDisplayed()));
            toggleButton.perform(click());

            ViewInteraction toggleButton2 = onView(
                    allOf(withId(R.id.button_switch_layout),
                            childAtPosition(
                                    allOf(withId(R.id.whole_container),
                                            childAtPosition(
                                                    withId(android.R.id.content),
                                                    0)),
                                    5),
                            isDisplayed()));
            toggleButton2.perform(click());

            ViewInteraction appCompatImageButton = onView(
                    allOf(withId(R.id.button_add_notes),
                            childAtPosition(
                                    allOf(withId(R.id.whole_container),
                                            childAtPosition(
                                                    withId(android.R.id.content),
                                                    0)),
                                    7),
                            isDisplayed()));
            appCompatImageButton.perform(click());

            ViewInteraction appCompatImageButton2 = onView(
                    allOf(withId(R.id.button_form_text),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                            0),
                                    4),
                            isDisplayed()));
            appCompatImageButton2.perform(click());

            ViewInteraction appCompatButton2 = onView(
                    allOf(withId(R.id.button_complete), withText("完成"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.whole_container),
                                            0),
                                    5),
                            isDisplayed()));
            appCompatButton2.perform(click());

        }

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
