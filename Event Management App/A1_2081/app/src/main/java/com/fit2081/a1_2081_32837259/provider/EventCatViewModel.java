package com.fit2081.a1_2081_32837259.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.a1_2081_32837259.EventCategory;

import java.util.List;

/**
 * ViewModel class is used for pre-processing the data,
 * before passing it to the controllers (Activity or Fragments). ViewModel class should not hold
 * direct reference to database. ViewModel class relies on repository class, hence the database is
 * accessed using the Repository class.
 */
public class EventCatViewModel extends AndroidViewModel {
    // reference to CardRepository
    private EventCatRepository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<EventCategory>> allCategoriesLiveData;

    public EventCatViewModel(@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new EventCatRepository(application);

        // get all items by calling method defined in repository class
        allCategoriesLiveData = repository.getAllEventCategories();
    }

    /**
     * ViewModel method to get all cards
     * @return LiveData of type List<Item>
     */
    public LiveData<List<EventCategory>> getAllEventsCatLiveData() {
        return allCategoriesLiveData;
    }

    /**
     * ViewModel method to insert one single item,
     * usually calling insert method defined in repository class
     * @param eventCat object containing details of new Item to be inserted
     */
    public void insert(EventCategory eventCat) {
        repository.insert(eventCat);
    }
    public void deleteEventCat() {
        repository.deleteEventCat();
    }

    public List<EventCategory> getEventsByCategory(String categoryID) {
        return repository.getEventsByCategory(categoryID);
    }
    public void updateEventCat(EventCategory eventCat) {
        repository.updateEventCat(eventCat);
    }

    public void decreaseEventCount(String categoryID) {
        repository.decreaseEventCount(categoryID);
    }
    public void increaseEventCount(String categoryID) {
        repository.increaseEventCount(categoryID);
    }

}

