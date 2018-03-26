package com.doordash.doordashlite.ui;

import android.Manifest;
import android.arch.core.executor.testing.CountingTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import com.doordash.common.AppExecutors;
import com.doordash.doordashlite.EspressoTestUtil;
import com.doordash.doordashlite.R;
import com.doordash.doordashlite.db.RestaurantDatabase;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity>
            mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CONTACTS);

    @Rule
    public CountingTaskExecutorRule mCountingTaskExecutorRule = new CountingTaskExecutorRule();

    public MainActivityTest() {
        // delete the database
        InstrumentationRegistry.getTargetContext().deleteDatabase(RestaurantDatabase.DATABASE_NAME);
    }

    @Before
    public void disableRecyclerViewAnimations() {
        // Disable RecyclerView animations
        EspressoTestUtil.disableAnimations(mActivityRule);
    }

    @Before
    public void waitForDbCreation() throws Throwable {
        final CountDownLatch latch = new CountDownLatch(1);
        final LiveData<Boolean>
                databaseCreated =
                RestaurantDatabase
                        .getInstance(InstrumentationRegistry.getTargetContext(), new AppExecutors())
                        .getDatabaseCreated();
        mActivityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                databaseCreated.observeForever(new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (Boolean.TRUE.equals(aBoolean)) {
                            databaseCreated.removeObserver(this);
                            latch.countDown();
                        }
                    }
                });
            }
        });
        MatcherAssert.assertThat("database should've initialized",
                latch.await(1, TimeUnit.MINUTES),
                CoreMatchers.is(true));
    }

    @Test
    public void clickOnFirstItem_openRestaurantDetail() throws Throwable {
        drain();
        onView(ViewMatchers.withContentDescription(R.string.cd_restaurant_detail_container)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        drain();
        //onView(ViewMatchers.withContentDescription(R.string.)).check(matches(isDisplayed()));
    }

    private void drain() throws TimeoutException, InterruptedException {
        mCountingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES);
    }
}