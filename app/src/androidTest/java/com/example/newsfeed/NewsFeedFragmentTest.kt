package com.example.newsfeed

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsfeed.presentation.ui.Activity.MainActivity
import com.example.newsfeed.presentation.ui.common.NewsFeedAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsFeedFragmentTest {

    @get : Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var instrumentationContext: Context

    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun checkIsNewsFeedListFragmentIsVisible() {
        onView(withId(R.id.newsFeedMainContainer)).check(matches(isDisplayed()))
        onView(withId(R.id.newsFeedRv)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_circular)).check(matches(isDisplayed()))
    }

    @Test
    fun isRecyclerViewInitiateCorrectlyOrNot() {
        onView(withId(R.id.newsFeedRv)).check(matches(isDisplayed()))
    }

    @Test
    fun performClickAndCheckForDetailsViewVisibility() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.newsFeedRv)).perform(
            actionOnItemAtPosition<NewsFeedAdapter.NewsFeedViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.detailsImageVw)).check(matches(isDisplayed()))
    }
}



