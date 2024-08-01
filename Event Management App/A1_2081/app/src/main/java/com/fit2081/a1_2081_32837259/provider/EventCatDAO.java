package com.fit2081.a1_2081_32837259.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fit2081.a1_2081_32837259.EventCategory;

import java.util.List;

@Dao
public interface EventCatDAO {
    @Query("select * from event_category")
    LiveData<List<EventCategory>> getAllEventCategories();

    @Insert
    void addEventCat(EventCategory eventCat);

    @Query ("DELETE FROM event_category")
    void deleteEventCat();

    @Query("SELECT * FROM event_category WHERE categoryID = :categoryID")
    List<EventCategory> getEventsByCategory(String categoryID);

    @Update
    void updateEventCat(EventCategory eventCat);

    @Query("UPDATE event_category SET eventCount = eventCount - 1 WHERE categoryID = :categoryID")
    void decreaseEventCount(String categoryID);

    @Query("UPDATE event_category SET eventCount = eventCount + 1 WHERE categoryID = :categoryID")
    void increaseEventCount(String categoryID);
}
