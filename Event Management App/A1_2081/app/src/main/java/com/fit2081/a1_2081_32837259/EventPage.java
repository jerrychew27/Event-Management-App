package com.fit2081.a1_2081_32837259;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Random;
import java.util.StringTokenizer;

public class EventPage extends AppCompatActivity {

    EditText editTextEventId;
    EditText editTextEventName;
    EditText editTextEventCategoryId;
    EditText editTextTickets;
    Switch switchEventStatus;
    EventsBroadCastReceiver myBroadCastReceiver = new EventsBroadCastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        editTextEventId = findViewById(R.id.editTextEventId);
        editTextEventName = findViewById(R.id.editTextEventName);
        editTextEventCategoryId = findViewById(R.id.editTextEventCategoryId);
        editTextTickets = findViewById(R.id.editTextTickets);
        switchEventStatus = findViewById(R.id.EventsActiveSwitch);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);

        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Register the broadcast receiver here

        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the broadcast receiver here
        unregisterReceiver(myBroadCastReceiver);
    }

    class EventsBroadCastReceiver extends BroadcastReceiver {

        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            // Tokenize received message here

            String eventMsg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            StringTokenizer sT = new StringTokenizer(eventMsg, ":"); // a new tokenizer with ":" as delimiter
            String event = sT.nextToken();
            // check if category is the first token
            if (event.equals("event")) {
                String remainingMessage = sT.nextToken();
                // Tokenize the remaining message with ";" as delimiter
                String[] eventDetails = remainingMessage.split(";", -1);

                if (eventDetails.length == 4){ // check if the message has 4 tokens
                    String eventName;
                    if (!eventDetails[0].isEmpty()){ // check if the first token is not empty
                        eventName = eventDetails[0];
                        if (!eventDetails[1].isEmpty()){ // check if the second token is not empty
                            String categoryId = eventDetails[1];
                            if (!eventDetails[2].isEmpty()){ // check if the third token is not empty
                                String ticketsAvailable = eventDetails[2];
                                if (ticketsAvailable.matches("[0-9]+")){
                                    int ticketsInt = Integer.parseInt(ticketsAvailable);
                                    if (ticketsInt > 0 ){
                                        if (!eventDetails[3].isEmpty()){ // check if the fourth token is not empty
                                            String eventIsActive = eventDetails[3];
                                            String eventActiveUpper = eventIsActive.toUpperCase();
                                            if (eventActiveUpper.equals("TRUE") || eventActiveUpper.equals("FALSE")){
                                                editTextEventName.setText(eventName);
                                                editTextEventCategoryId.setText(categoryId);
                                                editTextTickets.setText(String.valueOf(ticketsInt));
                                                switchEventStatus.setChecked(eventActiveUpper.equals("TRUE"));
                                            } else {
                                                Toast.makeText(context, "Invalid message received", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            String categoryIsActive = "FALSE";
                                            editTextEventName.setText(eventName);
                                            editTextEventCategoryId.setText(categoryId);
                                            editTextTickets.setText(String.valueOf(ticketsInt));
                                            switchEventStatus.setChecked(categoryIsActive.equals("TRUE"));
                                        }
                                    } else {
                                        Toast.makeText(context, "Invalid message received", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "Invalid message received", Toast.LENGTH_SHORT).show();
                                    }

                            } else {
                                String ticketsAvailable = editTextTickets.getText().toString();
                                if (!eventDetails[3].isEmpty()){
                                    String eventIsActive = eventDetails[3];
                                    String eventActiveUpper = eventIsActive.toUpperCase();
                                    if (eventActiveUpper.equals("TRUE") || eventActiveUpper.equals("FALSE")){
                                        editTextEventName.setText(eventName);
                                        editTextEventCategoryId.setText(categoryId);
                                        editTextTickets.setText(ticketsAvailable);
                                        switchEventStatus.setChecked(eventActiveUpper.equals("TRUE"));
                                    } else {
                                        Toast.makeText(context, "Invalid message received", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    String categoryIsActive = "FALSE";
                                    editTextEventName.setText(eventName);
                                    editTextEventCategoryId.setText(categoryId);
                                    editTextTickets.setText(ticketsAvailable);
                                    switchEventStatus.setChecked(categoryIsActive.equals("TRUE"));
                                }
                            }
                        } else {
                            Toast.makeText(context, "Invalid message received", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, "Invalid message received", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Invalid message received", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Invalid message received", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onSaveEventClickButton(View view){

        String failedToSave = ("Please fill in all the fields! ");

        String eventName = editTextEventName.getText().toString();
        String eventCatId = editTextEventCategoryId.getText().toString();
        String tickets = editTextTickets.getText().toString();
        boolean eventStatus = switchEventStatus.isChecked();

        if (!eventName.isEmpty() && !eventCatId.isEmpty()) {
            if (tickets.isEmpty()) {
                tickets = "-1";
                int ticketsInt = Integer.parseInt(tickets);
                editTextEventId.setText(generateCategoryID());
                saveDataToSharedPreference(editTextEventId.getText().toString(), eventName, eventCatId, ticketsInt, eventStatus);
                Toast.makeText(this, "Category saved successfully: " + editTextEventId.getText().toString() + "to " + eventCatId + " has been created.",
                        Toast.LENGTH_SHORT).show();
            } else if (tickets.equals("0")){
                Toast.makeText(this, "Tickets available must be a positive integer", Toast.LENGTH_SHORT).show();

            } else {
                int ticketsInt = Integer.parseInt(tickets);
                editTextEventId.setText(generateCategoryID());
                saveDataToSharedPreference(editTextEventId.getText().toString(), eventName, eventCatId, ticketsInt, eventStatus);
                Toast.makeText(this, "Category saved successfully: " + editTextEventId.getText().toString() + " to " + eventCatId + " has been created.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, failedToSave, Toast.LENGTH_SHORT).show();
        }
    }
    private void saveDataToSharedPreference(String eventId, String eventName, String eventCatId, int tickets, boolean eventStatus){
        // initialise shared preference class variable to access Android's persistent storage
        SharedPreferences sharedPreferences = getSharedPreferences("EVENT_PAGE", MODE_PRIVATE);

        // use .edit function to access file using Editor variable
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save key-value pairs to the shared preference file
        editor.putString("KEY_eventID", eventId);
        editor.putString("KEY_eventName", eventName);
        editor.putString("KEY_eventCatID", eventCatId);
        editor.putInt("KEY_eventCount", tickets);
        editor.putBoolean("KEY_catStatus", eventStatus);

        // use editor.apply() to save data to the file asynchronously (in background without freezing the UI)
        editor.apply();

    }
    public String generateCategoryID(){
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
}