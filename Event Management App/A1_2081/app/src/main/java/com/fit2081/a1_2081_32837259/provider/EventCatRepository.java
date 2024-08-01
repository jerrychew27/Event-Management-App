package com.fit2081.a1_2081_32837259.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.a1_2081_32837259.EventCategory;

import java.util.List;

public class EventCatRepository {
    // private class variable to hold reference to DAO
    private EventCatDAO eventCatDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<EventCategory>> allCategoriesLiveData;

    // constructor to initialise the repository class
    EventCatRepository(Application application) {
        // get reference/instance of the database
        EventCatDatabase db = EventCatDatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        eventCatDAO = db.eventCatDAO();

        // once the class is initialised get all the items in the form of LiveData
        allCategoriesLiveData = eventCatDAO.getAllEventCategories();
    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    LiveData<List<EventCategory>> getAllEventCategories() {
        return allCategoriesLiveData;
    }

    /**
     * Repository method to insert one single item
     * @param eventCat object containing details of new Item to be inserted
     */
    void insert(EventCategory eventCat) {
        EventCatDatabase.databaseWriteExecutor.execute(() -> eventCatDAO.addEventCat(eventCat));
    }
    void deleteEventCat() {
        EventCatDatabase.databaseWriteExecutor.execute(() -> eventCatDAO.deleteEventCat());
    }
    List<EventCategory> getEventsByCategory(String categoryID) {
        return eventCatDAO.getEventsByCategory(categoryID);
    }
    void updateEventCat(EventCategory eventCat) {
        EventCatDatabase.databaseWriteExecutor.execute(() -> eventCatDAO.updateEventCat(eventCat));
    }
    void decreaseEventCount(String categoryID) {
        EventCatDatabase.databaseWriteExecutor.execute(() -> eventCatDAO.decreaseEventCount(categoryID));
    }
    void increaseEventCount(String categoryID) {
        EventCatDatabase.databaseWriteExecutor.execute(() -> eventCatDAO.increaseEventCount(categoryID));
    }
}
