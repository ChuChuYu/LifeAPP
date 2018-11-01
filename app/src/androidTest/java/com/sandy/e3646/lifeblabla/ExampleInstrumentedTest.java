package com.sandy.e3646.lifeblabla;

import android.app.Activity;
import android.content.Context;
import static android.support.test.espresso.Espresso.onView;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sandy.e3646.lifeblabla.guideactivity.GuideActivity;
import com.sandy.e3646.lifeblabla.mainactivity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<GuideActivity> mActivityTestRule = new ActivityTestRule<>(GuideActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRuletwo = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSkipButton() {
//        onView(withId(R.id.button_skip))
//                .perform(click());

        onView(withId(R.id.button_switch_layout))
                .perform(click());
    }

    @Test
    public void testToggleButton() {


    }

    @Test
    public void testAddNote() {
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.sandy.e3646.lifeblabla", appContext.getPackageName());
    }
}
