package com.fit2081.a1_2081_32837259;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  int id;
    @ColumnInfo(name = "eventID")
    private String eventID;

    @ColumnInfo(name = "eventName")
    private String eventName;

    @ColumnInfo(name = "eventCatID")
    private String eventCategoryID;

    @ColumnInfo(name = "tickets")
    private int tickets;

    @ColumnInfo(name = "eventActive")
    private boolean isActive;



    public Event(String eventID, String eventName, String eventCategoryID, int tickets, boolean isActive) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventCategoryID = eventCategoryID;
        this.tickets = tickets;
        this.isActive = isActive;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public int getTickets() {
        return tickets;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventCategoryID(String eventCategoryID) {
        this.eventCategoryID = eventCategoryID;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getEventCategoryID() {
        return eventCategoryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
