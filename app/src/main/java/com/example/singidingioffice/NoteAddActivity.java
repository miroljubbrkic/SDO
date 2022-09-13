package com.example.singidingioffice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DBHelper.DBHelper;

public class NoteAddActivity extends AppCompatActivity {

    EditText title;
    EditText content;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        title = findViewById(R.id.noteAddTitle);
        content = findViewById(R.id.noteAddContent);
        DB = new DBHelper(this);

        ImageButton noteAddBack = findViewById(R.id.noteAddBack);
        noteAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });

        ImageButton notecheck = findViewById(R.id.noteAddCheck);
        notecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(v);
            }
        });

        TextView title = findViewById(R.id.titleLabel);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainScreen(v);
            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void openMainScreen(View v) {
        Intent i = new Intent(v.getContext(), MainActivity.class);
        startActivity(i);
    }

    public void goBack(View v) {
        finish();
    }

    public void check(View v) {
        String titleText = title.getText().toString();
        String contentText = content.getText().toString();
        String id = DBHelper.createID();

        boolean checkInsertData = DB.insertNote(id, titleText, contentText);

        if(checkInsertData) {
            Toast toast = Toast.makeText(NoteAddActivity.this, "Note added!", Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(v.getContext(), NotesActivity.class);

            startActivity(i);
        } else {
            Toast toast = Toast.makeText(NoteAddActivity.this, "Adding note failed!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}