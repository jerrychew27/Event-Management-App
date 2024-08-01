package com.fit2081.a1_2081_32837259;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "event_category")
public class EventCategory {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  int id;

    @ColumnInfo(name = "categoryID")
    private String categoryID;

    @ColumnInfo(name = "categoryName")
    private String categoryName;

    @ColumnInfo(name = "eventCount")
    private int eventCount;

    @ColumnInfo(name = "initialEventCount")
    private int initialEventCount;

    @ColumnInfo(name = "catActive")
    private boolean isActive;

    @ColumnInfo(name = "eventLocation")
    private String eventLocation;

    public EventCategory(String categoryID, String categoryName, int eventCount, boolean isActive,
                         int initialEventCount, String eventLocation) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.isActive = isActive;
        this.initialEventCount = initialEventCount;
        this.eventLocation = eventLocation;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setInitialEventCount(int initialEventCount) {
        this.initialEventCount = initialEventCount;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setEventCount(int eventCounts) {
        eventCount = eventCounts;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public int getInitialEventCount() {
        return initialEventCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
