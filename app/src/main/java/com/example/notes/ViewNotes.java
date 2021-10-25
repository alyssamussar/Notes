package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import android.view.View;

public class ViewNotes extends AppCompatActivity {
    TextView textView;
    public static ArrayList<Note> notes = new ArrayList();

    public void goToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void goToAddNote() {
        Intent intent = new Intent(this, AddNote.class);
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
        if (item.getItemId()==R.id.addNote) {
            goToAddNote();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_notes);

        //1. Display welcome message. Fetch username from Shared Preferences
        textView = (TextView) findViewById(R.id.viewName);
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(usernameKey, "");
        textView.setText("Welcome "+username+"!");

        //2. Get SQLite database instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        //3. Inflate the "notes" class variable using readNotes method implemented in DBHelper class.
        //  Use the username you got from SharedPreference as a parameter to readNotes method
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username);

        //4. Create an ArrayList<String> object by iterating over notes object
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        //5. Use ListView view to display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        //6. Add onItemClickListener for ListView item, a note in our case
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Initialize intent to take user to third activity
                Intent intent = new Intent(getApplicationContext(), AddNote.class);
                //Add the position of the item that was clicked on as "noteid"
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });

    }
}