package com.acme.tictactoe.view


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.acme.tictactoe.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TicTacToeActivityTest {

  @Rule
  @JvmField
  var mActivityTestRule = ActivityTestRule(TicTacToeActivity::class.java)

  @Test
  fun ticTacToeActivityTest() {
    val appCompatButton = onView(
        allOf(withId(R.id.cell_0_0),
            childAtPosition(
                allOf(withId(R.id.buttonGrid),
                    childAtPosition(
                        withId(R.id.tictactoe),
                        0)),
                0),
            isDisplayed()))
    appCompatButton.perform(click())

    val appCompatButton2 = onView(
        allOf(withId(R.id.cell_1_0),
            childAtPosition(
                allOf(withId(R.id.buttonGrid),
                    childAtPosition(
                        withId(R.id.tictactoe),
                        0)),
                3),
            isDisplayed()))
    appCompatButton2.perform(click())

    val appCompatButton3 = onView(
        allOf(withId(R.id.cell_0_1),
            childAtPosition(
                allOf(withId(R.id.buttonGrid),
                    childAtPosition(
                        withId(R.id.tictactoe),
                        0)),
                1),
            isDisplayed()))
    appCompatButton3.perform(click())

    val appCompatButton4 = onView(
        allOf(withId(R.id.cell_1_1),
            childAtPosition(
                allOf(withId(R.id.buttonGrid),
                    childAtPosition(
                        withId(R.id.tictactoe),
                        0)),
                4),
            isDisplayed()))
    appCompatButton4.perform(click())

    val appCompatButton5 = onView(
        allOf(withId(R.id.cell_0_2),
            childAtPosition(
                allOf(withId(R.id.buttonGrid),
                    childAtPosition(
                        withId(R.id.tictactoe),
                        0)),
                2),
            isDisplayed()))
    appCompatButton5.perform(click())

    val textView = onView(
        allOf(withId(R.id.winnerPlayerLabel), withText("X"),
            childAtPosition(
                allOf(withId(R.id.winnerPlayerViewGroup),
                    childAtPosition(
                        withId(R.id.tictactoe),
                        1)),
                0),
            isDisplayed()))
    textView.check(matches(withText("X")))

    val textView2 = onView(
        allOf(withText("Winner"),
            childAtPosition(
                allOf(withId(R.id.winnerPlayerViewGroup),
                    childAtPosition(
                        withId(R.id.tictactoe),
                        1)),
                1),
            isDisplayed()))
    textView2.check(matches(withText("Winner")))

    val actionMenuItemView = onView(
        allOf(withId(R.id.action_reset), withText("Reset"),
            childAtPosition(
                childAtPosition(
                    withId(R.id.action_bar),
                    1),
                0),
            isDisplayed()))
    actionMenuItemView.perform(click())
  }

  private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
      override fun describeTo(description: Description) {
        description.appendText("Child at position $position in parent ")
        parentMatcher.describeTo(description)
      }

      public override fun matchesSafely(view: View): Boolean {
        val parent = view.parent
        return parent is ViewGroup && parentMatcher.matches(parent)
            && view == parent.getChildAt(position)
      }
    }
  }
}
