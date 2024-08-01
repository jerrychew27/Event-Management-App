package com.fit2081.a1_2081_32837259.provider;
import com.fit2081.a1_2081_32837259.Event;
import com.fit2081.a1_2081_32837259.EventCategory;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EventDAO {
    @Query("select * from event_table")
    LiveData<List<Event>> getAllEvents();

    @Insert
    void addEvent(Event event);

    @Query("DELETE FROM event_table WHERE eventID = :eventId")
    void deleteEvent(String eventId);

    @Query("DELETE FROM event_table")
    void deleteAllEvents();
}
