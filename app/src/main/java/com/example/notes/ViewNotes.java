package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

public class ViewNotes extends AppCompatActivity {
    TextView textView;

    public void goToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Creates menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout) { //logout
            SharedPreferences sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove("username").apply();
            goToLogin();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_notes);

        textView = (TextView) findViewById(R.id.viewName);
//        Intent intent = getIntent();
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(usernameKey, "");
//        String name = intent.getStringExtra("username");
        textView.setText("Welcome "+username+"!");
    }
}