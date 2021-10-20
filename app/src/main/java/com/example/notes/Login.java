package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class Login extends AppCompatActivity {


    public void clickFunction(View view) {
        //Get username from user input
        EditText myTextField = (EditText) findViewById(R.id.username);
        String username = myTextField.getText().toString();
        //Save username in shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();
        //Go to View Notes page
        goToViewNotes(username);
    }

    public void goToViewNotes(String s) {
        Intent intent = new Intent(this, ViewNotes.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(usernameKey, "");

        if(!username.equals("")) { //user has logged in
            goToViewNotes(username);
        } else { //user hasn't logged in yet
            setContentView(R.layout.login);
        }
    }
}