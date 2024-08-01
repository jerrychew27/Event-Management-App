package com.fit2081.a1_2081_32837259.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fit2081.a1_2081_32837259.EventCategory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {EventCategory.class}, version = 1)
public abstract class EventCatDatabase extends RoomDatabase {

    // database name, this is important as data is contained inside a file named "card_database"
    public static final String EVENT_CAT_DATABASE = "event_cat_db";
    // reference to DAO, here RoomDatabase parent class will implement this interface
    public abstract EventCatDAO eventCatDAO();


    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile EventCatDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static EventCatDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EventCatDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EventCatDatabase.class, EVENT_CAT_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
