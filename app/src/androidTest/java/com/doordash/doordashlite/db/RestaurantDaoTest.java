package com.doordash.doordashlite.db;

import android.Manifest;
import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.doordash.doordashlite.LiveDataTestUtil;
import com.doordash.doordashlite.db.dao.RestaurantDetailDao;
import com.doordash.doordashlite.db.dao.RestaurantEntryDao;
import com.doordash.doordashlite.db.entity.RestaurantEntryEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.doordash.doordashlite.db.TestData.RESTAURANT_DETAIL_ENTITY;
import static com.doordash.doordashlite.db.TestData.RESTAURANT_ENTRY;
import static com.doordash.doordashlite.db.TestData.RESTAURANT_ENTRY_ENTITY;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test the implementation of {@link RestaurantEntryDao} and {@link RestaurantDetailDao}
 */
@RunWith(AndroidJUnit4.class)
public class RestaurantDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CONTACTS);

    private RestaurantDatabase mDatabase;
    private RestaurantEntryDao mEntryDao;
    private RestaurantDetailDao mDetailDao;


    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase =
                Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                        RestaurantDatabase.class)
                        // allowing main thread queries, just for testing
                        .allowMainThreadQueries().build();
        mEntryDao = mDatabase.entryDao();
        mDetailDao = mDatabase.detailDao();
        mEntryDao.insertAll(RESTAURANT_ENTRY);
        mDetailDao.insert(RESTAURANT_DETAIL_ENTITY);
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getRestaurantAfterInserted() throws InterruptedException {
        List<RestaurantEntryEntity> contacts = LiveDataTestUtil.getValue(mEntryDao.loadAllRestaurants());

        assertThat(contacts.size(), is(RESTAURANT_ENTRY.size()));
    }

    @Test
    public void getRestaurantById() throws InterruptedException {
        RestaurantEntryEntity
                contact =
                LiveDataTestUtil.getValue(mEntryDao.loadRestaurant(RESTAURANT_ENTRY_ENTITY.getId()));

        assertThat(contact.getId(), is(RESTAURANT_ENTRY_ENTITY.getId()));
        assertThat(contact.getName(), is(RESTAURANT_ENTRY_ENTITY.getName()));
        assertThat(contact.getAverageRating(), is(RESTAURANT_ENTRY_ENTITY.getAverageRating()));
        assertThat(contact.getCoverImageUrl(), is(RESTAURANT_ENTRY_ENTITY.getCoverImageUrl()));
        assertThat(contact.getDeliveryFee(), is(RESTAURANT_ENTRY_ENTITY.getDeliveryFee()));
        assertThat(contact.getDescription(), is(RESTAURANT_ENTRY_ENTITY.getDescription()));
        assertThat(contact.getStatus(), is(RESTAURANT_ENTRY_ENTITY.getStatus()));
    }
}
