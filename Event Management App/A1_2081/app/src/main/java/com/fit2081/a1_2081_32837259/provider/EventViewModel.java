package com.fit2081.a1_2081_32837259.provider;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.a1_2081_32837259.Event;

import java.util.List;

/**
 * ViewModel class is used for pre-processing the data,
 * before passing it to the controllers (Activity or Fragments). ViewModel class should not hold
 * direct reference to database. ViewModel class relies on repository class, hence the database is
 * accessed using the Repository class.
 */
public class EventViewModel extends AndroidViewModel {
    // reference to CardRepository
    private EventRepository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Event>> allEventsLiveData;

    public EventViewModel(@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new EventRepository(application);

        // get all items by calling method defined in repository class
        allEventsLiveData = repository.getAllEvents();
    }

    /**
     * ViewModel method to get all cards
     * @return LiveData of type List<Item>
     */
    public LiveData<List<Event>> getAllEventsLiveData() {
        return allEventsLiveData;
    }

    /**
     * ViewModel method to insert one single item,
     * usually calling insert method defined in repository class
     * @param event object containing details of new Item to be inserted
     */
    public void insert(Event event) {
        repository.insert(event);
    }

    public void deleteEvent(String event) {
        repository.deleteEvent(event);
    }

    public void deleteAllEvents() {
        repository.deleteAllEvents();
    }
}

