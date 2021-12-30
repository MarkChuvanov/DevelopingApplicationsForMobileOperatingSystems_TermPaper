package ru.mivlgu.pin.volleyballreferee.ui.match;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.mivlgu.pin.volleyballreferee.MainActivity;
import ru.mivlgu.pin.volleyballreferee.R;

public class ProgressMatchFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp () throws Exception {
        Espresso.onView(ViewMatchers.withId(R.id.start_match_btn)).perform(ViewActions.click());
    }

    @Test
    public void onFirstTeamTest () {
        Espresso.onView(ViewMatchers.withId(R.id.first_team_btn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.result_tv)).check(matches(withText("1:0")));
    }

    @Test
    public void onSecondTeamTest () {
        Espresso.onView(ViewMatchers.withId(R.id.second_team_btn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.result_tv)).check(matches(withText("0:1")));
    }
}