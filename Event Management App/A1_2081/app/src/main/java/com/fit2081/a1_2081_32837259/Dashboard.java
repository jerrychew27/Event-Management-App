package com.fit2081.a1_2081_32837259;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fit2081.a1_2081_32837259.provider.EventCatViewModel;
import com.fit2081.a1_2081_32837259.provider.EventViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dashboard extends AppCompatActivity {
    private DrawerLayout drawerlayout;
    private NavigationView navigationView;
    Toolbar toolbar;
    private EventCatViewModel eventCatViewModel;
    private EventViewModel eventViewModel;

    EditText editTextEventId;
    EditText editTextEventName;
    EditText editTextEventCategoryId;
    EditText editTextTickets;
    Switch switchEventStatus;
    List<EventCategory> eventCategoryList;
    Gson gson = new Gson();
    Handler uiHandler = new Handler(Looper.getMainLooper());
    TextView tvGesture;
    View touchpad;
    // help detect basic gestures like scroll, single tap, double tap, etc
    private GestureDetectorCompat mDetector;


    ArrayList<EventCategory> categoryListArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        // Initialize the textview from the dashboard activity
        editTextEventId = findViewById(R.id.editTextEventId);
        editTextEventName = findViewById(R.id.editTextEventName);
        editTextEventCategoryId = findViewById(R.id.editTextEventCategoryId);
        editTextTickets = findViewById(R.id.editTextTickets);
        switchEventStatus = findViewById(R.id.EventsActiveSwitch);
        tvGesture = findViewById(R.id.tvGesture);
        touchpad = findViewById(R.id.touchpad);
        // initialise new instance of CustomGestureDetector class
        CustomGestureDetector customGestureDetector = new CustomGestureDetector(this);

        // register GestureDetector and set listener as CustomGestureDetector
        mDetector = new GestureDetectorCompat(this, customGestureDetector);
        mDetector.setOnDoubleTapListener(customGestureDetector);
        mDetector.setIsLongpressEnabled(true);

        touchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mDetector.onTouchEvent(event);

                return true;

            }
        });

        eventCatViewModel = new ViewModelProvider(this).get(EventCatViewModel.class);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        // Initialize the drawer layout, navigation view and toolbar
        drawerlayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assignment-2");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String failedToSave = ("Please fill in all the fields! ");

                String eventName = editTextEventName.getText().toString();
                String eventCatId = editTextEventCategoryId.getText().toString();

                boolean eventStatus = switchEventStatus.isChecked();
                ExecutorService executor = Executors.newSingleThreadExecutor();


                if (!eventName.isEmpty() && !eventCatId.isEmpty() ) {
                    executor.execute(() -> {
                        String tickets = editTextTickets.getText().toString();
                        eventCategoryList = eventCatViewModel.getEventsByCategory(eventCatId);

                        for (EventCategory category : eventCategoryList) {
                            if (category.getCategoryID().equals(eventCatId)) {
                                // event name needs to be alphanumeric
                                if (!eventName.matches("^[a-zA-Z0-9 ]*$") || !eventName.matches(".*[a-zA-Z].*")) {
                                    uiHandler.post(() -> Toast.makeText(Dashboard.this, "Invalid event name", Toast.LENGTH_SHORT).show());
                                    return;
                                }
                                if (tickets.isEmpty()) {
                                    tickets = "0";
                                    editTextTickets.setText("0");
                                    uiHandler.post(() -> Toast.makeText(Dashboard.this, "Tickets available set to 0", Toast.LENGTH_SHORT).show());
                                    int ticketsInt = Integer.parseInt(tickets);
                                    editTextEventId.setText(generateEventID());
                                    Event eventList = new Event(editTextEventId.getText().toString(), eventName, eventCatId, ticketsInt, eventStatus);
                                    eventViewModel.insert(eventList);
                                    incrementCategoryEventCount(eventCatId);
                                    Snackbar.make(view, "Event saved successfully: " +
                                            editTextEventId.getText().toString() + "to " + eventCatId +
                                            " has been created.", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            eventViewModel.deleteEvent(eventCatId);
                                            decrementCategoryEventCount(eventCatId);
                                            Snackbar.make(view, "Event creation undone!", Snackbar.LENGTH_SHORT).show();
                                        }
                                    }).show();
                                } else {
                                    int ticketsInt = Integer.parseInt(tickets);
                                    if (ticketsInt < 0) {
                                        uiHandler.post(() -> Toast.makeText(Dashboard.this, "Invalid Tickets available", Toast.LENGTH_SHORT).show());

                                    } else {
                                        editTextEventId.setText(generateEventID());
                                        Event eventList = new Event(editTextEventId.getText().toString(), eventName, eventCatId, ticketsInt, eventStatus);

                                        eventViewModel.insert(eventList);
                                        incrementCategoryEventCount(eventCatId);
                                        Snackbar.make(view, "Event saved successfully: " +
                                                editTextEventId.getText().toString() + "to " + eventCatId +
                                                " has been created.", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                eventViewModel.deleteEvent(editTextEventId.getText().toString());
                                                decrementCategoryEventCount(eventCatId);
                                                Snackbar.make(view, "Event creation undone!", Snackbar.LENGTH_SHORT).show();
                                            }
                                        }).show();
                                    }
                                }
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "Category does not exist", Toast.LENGTH_SHORT).show();
                        }
                        if (eventCategoryList.size() == 0) {
                            uiHandler.post(() -> Toast.makeText(Dashboard.this, "Category does not exist", Toast.LENGTH_SHORT).show());
                        }

                    });


                } else {
                    Toast.makeText(Dashboard.this, failedToSave, Toast.LENGTH_SHORT).show();
                }
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_categories_listing, new FragmentListCategory()).commit();
    }




    // Increment the category event count
    private void incrementCategoryEventCount(String eventCategoryId) {
        eventCatViewModel.increaseEventCount(eventCategoryId);
    }

    // Decrement the category event count
    private void decrementCategoryEventCount(String eventCategoryId) {
        eventCatViewModel.decreaseEventCount(eventCategoryId);
    }


    //  Generate the event ID
    public String generateEventID() {
        String randomEventID = "E";

        Random random = new Random();
        for (int i = 0; i < 2; i++){
            char randomChar = (char) (random.nextInt(26) + 'A'); // Generate uppercase letter
            randomEventID += randomChar;
        }

        randomEventID += "-";

        for (int i = 0; i < 5; i++) {
            int randomDigit = random.nextInt(10); // Generate digit between 0-9
            randomEventID += randomDigit;
        }
        return randomEventID;
    }

    // On logout click
    public void onLogoutClick(View view){
        finish();
    }

    // On add category click
    public void onAddCategoryClick(View view){
        Intent intent = new Intent(this, CategoryPage.class);
        startActivity(intent);
    }

    // On view all categories click
    public void onViewAllCategoriesClick(View view){
        Intent intent = new Intent(this, ListCategoryActivity.class);
        startActivity(intent);
    }

    // On view all events click
    public void onViewAllEventsClick(View view){
        Intent intent = new Intent(this, ListEventActivity.class);
        startActivity(intent);
    }

    // On event form clear click
    public void onEventFormClearClick(View view){
        editTextEventId.setText("");
        editTextEventName.setText("");
        editTextEventCategoryId.setText("");
        editTextTickets.setText("");
        switchEventStatus.setChecked(false);
    }
    public void onDeleteAllCategoriesClick(View view){
        eventCatViewModel.deleteEventCat();

    }
    public void onDeleteAllEventsClick(View view){
        eventViewModel.deleteAllEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.event_form_clear_option) {
            onEventFormClearClick(null);
        } else if (item.getItemId() == R.id.delete_all_categories_option) {
            onDeleteAllCategoriesClick(null);
        } else if (item.getItemId() == R.id.delete_all_events_option) {
            onDeleteAllEventsClick(null);
        }
        return true;
    }



    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.view_all_categories_menu_id) {
                onViewAllCategoriesClick(null);
            } else if (item.getItemId() == R.id.add_category_menu_id) {
                onAddCategoryClick(null);
            } else if (item.getItemId() == R.id.view_all_events_menu_id) {
                onViewAllEventsClick(null);
            } else if (item.getItemId() == R.id.logout_menu_id) {
                onLogoutClick(null);
            }
            // close the drawer
            drawerlayout.closeDrawers();
            // tell the OS
            return true;
        }
    }
    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private Context context;

        public CustomGestureDetector(Context context) {
            this.context = context;
        }
        public void onDoubleTapToSaveEvent(View view) {
            String failedToSave = ("Please fill in all the fields! ");

            String eventName = editTextEventName.getText().toString();
            String eventCatId = editTextEventCategoryId.getText().toString();

            boolean eventStatus = switchEventStatus.isChecked();
            ExecutorService executor = Executors.newSingleThreadExecutor();


            if (!eventName.isEmpty() && !eventCatId.isEmpty() ) {
                executor.execute(() -> {
                    String tickets = editTextTickets.getText().toString();
                    eventCategoryList = eventCatViewModel.getEventsByCategory(eventCatId);

                    for (EventCategory category : eventCategoryList) {
                        if (category.getCategoryID().equals(eventCatId)) {
                            // event name needs to be alphanumeric
                            if (!eventName.matches("^[a-zA-Z0-9 ]*$") || !eventName.matches(".*[a-zA-Z].*")) {
                                uiHandler.post(() -> Toast.makeText(Dashboard.this, "Invalid event name", Toast.LENGTH_SHORT).show());
                                return;
                            }
                            if (tickets.isEmpty()) {
                                tickets = "0";
                                editTextTickets.setText("0");
                                uiHandler.post(() -> Toast.makeText(Dashboard.this, "Tickets available set to 0", Toast.LENGTH_SHORT).show());
                                int ticketsInt = Integer.parseInt(tickets);
                                editTextEventId.setText(generateEventID());
                                Event eventList = new Event(editTextEventId.getText().toString(), eventName, eventCatId, ticketsInt, eventStatus);
                                eventViewModel.insert(eventList); // save a new record into the event database
                                incrementCategoryEventCount(eventCatId);
                                Snackbar.make(view, "Event saved successfully: " +
                                        editTextEventId.getText().toString() + "to " + eventCatId +
                                        " has been created.", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        eventViewModel.deleteEvent(eventCatId);
                                        decrementCategoryEventCount(eventCatId);
                                        Snackbar.make(view, "Event creation undone!", Snackbar.LENGTH_SHORT).show();
                                    }
                                }).show();
                            } else {
                                int ticketsInt = Integer.parseInt(tickets);
                                if (ticketsInt < 0) {
                                    uiHandler.post(() -> Toast.makeText(Dashboard.this, "Invalid Tickets available", Toast.LENGTH_SHORT).show());

                                } else {
                                    editTextEventId.setText(generateEventID());
                                    Event eventList = new Event(editTextEventId.getText().toString(), eventName, eventCatId, ticketsInt, eventStatus);

                                    eventViewModel.insert(eventList);
                                    incrementCategoryEventCount(eventCatId);
                                    Snackbar.make(view, "Event saved successfully: " +
                                            editTextEventId.getText().toString() + "to " + eventCatId +
                                            " has been created.", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            eventViewModel.deleteEvent(editTextEventId.getText().toString());
                                            decrementCategoryEventCount(eventCatId);
                                            Snackbar.make(view, "Event creation undone!", Snackbar.LENGTH_SHORT).show();
                                        }
                                    }).show();
                                }
                            }
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Category does not exist", Toast.LENGTH_SHORT).show();
                    }
                    if (eventCategoryList.size() == 0) {
                        uiHandler.post(() -> Toast.makeText(Dashboard.this, "Category does not exist", Toast.LENGTH_SHORT).show());
                    }

                });


            } else {
                Toast.makeText(Dashboard.this, failedToSave, Toast.LENGTH_SHORT).show();
            }
        }
        public void onLongPressClearFields(View view) {
            editTextEventId.setText("");
            editTextEventName.setText("");
            editTextEventCategoryId.setText("");
            editTextTickets.setText("");
            switchEventStatus.setChecked(false);
        }


        @Override
        public void onLongPress(MotionEvent e) {
            onLongPressClearFields(touchpad);
            tvGesture.setText("onLongPress");
        }
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onDoubleTapToSaveEvent(touchpad);
            tvGesture.setText("onDoubleTap");
            return true;
        }
    }
}




