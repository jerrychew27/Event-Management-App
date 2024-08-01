package com.fit2081.a1_2081_32837259.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.a1_2081_32837259.Event;

import java.util.List;

public class EventRepository {
    // private class variable to hold reference to DAO
    private EventDAO eventDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Event>> allEventsLiveData;

    // constructor to initialise the repository class
    EventRepository(Application application) {
        // get reference/instance of the database
        EventDatabase db = EventDatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        eventDAO = db.eventDAO();

        // once the class is initialised get all the items in the form of LiveData
        allEventsLiveData = eventDAO.getAllEvents();
    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }

    /**
     * Repository method to insert one single item
     * @param event object containing details of new Item to be inserted
     */
    void insert(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> eventDAO.addEvent(event));
    }
    void deleteEvent(String event) {
        EventDatabase.databaseWriteExecutor.execute(() -> eventDAO.deleteEvent(event));
    }
    void deleteAllEvents() {
        EventDatabase.databaseWriteExecutor.execute(() -> eventDAO.deleteAllEvents());
    }
}
