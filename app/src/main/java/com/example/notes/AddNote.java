package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNote extends AppCompatActivity {
    String content;
    DBHelper dbHelper;

    public void saveMethod(View view) {
        //1. Get editText view and the content that the user entered
        EditText editText = (EditText) findViewById(R.id.editNote);
        content = editText.toString();

        //2. Initialize SQLite Database instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        //3. Initialize DBHelper class
        dbHelper = new DBHelper(sqLiteDatabase);

        //4. Set username in the following variable by fetching it from SharedPreferences
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(usernameKey, "");

        //5. Save information to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == 1) { //Add note
            title = "NOTE_" + (ViewNotes.notes.size()+1);
            dbHelper.saveNotes(username, title, content, date);
        } else { //Update note
            title = "NOTE_"+ (noteid+1);
            dbHelper.updateNote(title, date, content, username);
        }
        //6. Go to second activity using intents
        Intent intent = new Intent(this, ViewNotes.class);
        startActivity(intent);
    }

    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        //1. Get EditText view
        EditText editText = (EditText) findViewById(R.id.editNote);
        //2. Get Intent
        Intent intent = getIntent();
        //3. Get the value of integer "noteid" from intent
        //4. Intialize class variable "noteid" with the value from intent
        noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1) {
            //Display content of note by retrieving "notes" ArrayList in ViewNotes
            Note note = ViewNotes.notes.get(noteid);
            String noteContent = note.getContent();
            //Use editText.setText() to display the contents of this note
            editText.setText(noteContent);
        }

    }
}