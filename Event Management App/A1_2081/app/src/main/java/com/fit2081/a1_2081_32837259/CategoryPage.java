package com.fit2081.a1_2081_32837259;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;

import com.fit2081.a1_2081_32837259.provider.EventCatViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class CategoryPage extends AppCompatActivity {
    EditText editTextCategoryID;
    EditText editTextCategoryName;
    EditText editTextEventCount;
    Switch switchCategoryStatus;
    EditText editTextEventLocation;
    private EventCatViewModel eventCatViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

        editTextCategoryID = findViewById(R.id.editTextCategoryId);
        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        editTextEventCount = findViewById(R.id.editTextEventCount);
        switchCategoryStatus = findViewById(R.id.CategoryActiveSwitch);
        editTextEventLocation = findViewById(R.id.editTextEventLocation);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        eventCatViewModel = new EventCatViewModel(getApplication());


    }


    public void onSaveCategoryClick(View view){

        String failedToSave = ("Please fill in all the fields! ");;
        // Category ID needs to be auto generated upon saving new category

        String categoryName = editTextCategoryName.getText().toString();
        String eventCount = editTextEventCount.getText().toString();
        boolean categoryStatus = switchCategoryStatus.isChecked();
        String eventLocation = editTextEventLocation.getText().toString();

        if (!categoryName.isEmpty()) {
            if (!categoryName.matches("^[a-zA-Z0-9 ]*$") || !categoryName.matches(".*[a-zA-Z].*")) {
                Toast.makeText(this, "Invalid category name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (eventLocation.isEmpty()){
                eventLocation = "";
            }
            if (eventCount.isEmpty()) {
                eventCount = "0";
                editTextEventCount.setText("0");
                Toast.makeText(this, "Event count has been set to 0", Toast.LENGTH_SHORT).show();
                int eventCountInt = Integer.parseInt(eventCount);
                editTextCategoryID.setText(generateCategoryID());
                EventCategory categoryList = new EventCategory(editTextCategoryID.getText().toString(), categoryName, eventCountInt, categoryStatus, eventCountInt, eventLocation);
                eventCatViewModel.insert(categoryList);
                Toast.makeText(this, "Category saved successfully: " + editTextCategoryID.getText().toString() + " has been created.",
                        Toast.LENGTH_SHORT).show();
                finish();

            } else {
                int eventCountInt = Integer.parseInt(eventCount);
                if (eventCountInt < 0) {
                    Toast.makeText(this, "Invalid event count", Toast.LENGTH_SHORT).show();
                } else {
                    editTextCategoryID.setText(generateCategoryID());
                    EventCategory categoryList = new EventCategory(editTextCategoryID.getText().toString(), categoryName, eventCountInt, categoryStatus, eventCountInt, eventLocation);
                    eventCatViewModel.insert(categoryList);
                    Toast.makeText(this, "Category saved successfully: " + editTextCategoryID.getText().toString() + " has been created.",
                            Toast.LENGTH_SHORT).show();
                    finish();
            }}
        } else {
            Toast.makeText(this, failedToSave, Toast.LENGTH_SHORT).show();
        }
    }


    public String generateCategoryID(){
        String randomCategoryID = "C";

        Random random = new Random();
        for (int i = 0; i < 2; i++){
            char randomChar = (char) (random.nextInt(26) + 'A'); // Generate uppercase letter
            randomCategoryID += randomChar;
        }

        randomCategoryID += "-";

        for (int i = 0; i < 4; i++) {
            int randomDigit = random.nextInt(10); // Generate digit between 0-9
            randomCategoryID += randomDigit;
        }
        return randomCategoryID;
    }

}