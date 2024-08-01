package com.fit2081.a1_2081_32837259;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPage extends AppCompatActivity {

    // create instances for user input value of username, password and password confirmation
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        // Get the value input by the user
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
    }

    // a method for when the register button is clicked
    public void onClickRegisterButton(View view) {

        // A new intent for going to the login page
        Intent intent = new Intent(this, LoginPage.class);

        // convert value input to string for comparison
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        // first if condition to check if the its empty, if not show failure message
        if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            // second if condition to check if password and password confirmation are the same
            if (password.equals(confirmPassword)) {
                saveDataToSharedPreference(username, password); // saved to shared preferences
                Toast.makeText(this, "You have successfully registered an account!", Toast.LENGTH_SHORT).show();
                startActivity(intent); // show login page
            } else { // if password and password confirmation are different
                Toast.makeText(this, "Registration failed! ", Toast.LENGTH_SHORT).show();
            }
        } else { // if any of the fields are empty
            Toast.makeText(this, "Registration failed! ", Toast.LENGTH_SHORT).show();
        }

    }

    // if user already has account, they can click this and immediately go to login page
    public void onClickRegisterLoginButton(View view){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    // shared preference method
    private void saveDataToSharedPreference(String userValue, String passwordValue){
        // initialise shared preference class variable to access Android's persistent storage
        SharedPreferences sharedPreferences = getSharedPreferences("REGISTER_PAGE", MODE_PRIVATE);

        // use .edit function to access file using Editor variable
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save key-value pairs to the shared preference file
        editor.putString("KEY_USERNAME", userValue);
        editor.putString("KEY_PASSWORD", passwordValue);

        // use editor.apply() to save data to the file asynchronously (in background without freezing the UI)
        editor.apply();

    }
}