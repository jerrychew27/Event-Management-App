package com.fit2081.a1_2081_32837259;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    // create instances for user input value of login username, and login password
    EditText editTextLoginUsername;
    EditText editTextLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // restore preferences
        SharedPreferences sharedPreferences = getSharedPreferences("REGISTER_PAGE", MODE_PRIVATE);
        String usernameInitial = sharedPreferences.getString("KEY_USERNAME", "");
        TextView initialUsername = findViewById(R.id.editTextLoginUsername);

        // show registered username
        initialUsername.setText(usernameInitial);

        editTextLoginUsername = findViewById(R.id.editTextLoginUsername);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);

    }

    // a method for the user to go back to the register page by clicking on the register button
    public void onClickBackToRegisterButton(View view){
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }

    // a method for when the login button is clicked, it will bring the user to the Dashboard page
    public void onClickLoginButton(View view){
        Intent intent = new Intent(this, Dashboard.class);

        // check with the shared preference of the registered username and password
        SharedPreferences sharedPreferences = getSharedPreferences("REGISTER_PAGE", MODE_PRIVATE);

        String usernameRestored = sharedPreferences.getString("KEY_USERNAME", "DEFAULT VALUE");
        String passwordRestored = sharedPreferences.getString("KEY_PASSWORD", "DEFAULT VALUE");

        // convert username and password value to string for comparison
        String loginUsername = editTextLoginUsername.getText().toString();
        String loginPassword = editTextLoginPassword.getText().toString();

        // check if the login username and password are the same
        if (loginUsername.equals(usernameRestored)){
            if (loginPassword.equals(passwordRestored)){ // if same, it will show a success message and go to the dashboard
                Toast.makeText(this, "Login successful! ", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            } else {
                Toast.makeText(this, "Wrong password or username! ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Wrong password or username! ", Toast.LENGTH_SHORT).show();
        }
    }
}