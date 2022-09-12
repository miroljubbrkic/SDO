package com.example.singidingioffice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NoteEditActivity extends AppCompatActivity {

    String id;
    EditText title;
    EditText content;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        title = findViewById(R.id.noteEditTitle);
        content = findViewById(R.id.noteEditContent);
        DB = new DBHelper(this);

        id = (String) getIntent().getSerializableExtra("id");
        String oldTitle = (String) getIntent().getSerializableExtra("title");
        String oldContent = (String) getIntent().getSerializableExtra("content");

        title.setText(oldTitle);
        content.setText(oldContent);

        ImageButton noteEditBack = findViewById(R.id.noteEditBack);
        noteEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });

        ImageButton noteEditCheck = findViewById(R.id.noteEditCheck);
        noteEditCheck.setOnClickListener(new View.OnClickListener() {
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

        ImageButton noteEditDel = findViewById(R.id.noteEditDel);
        noteEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
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

        boolean checkUpdateData = DB.updateNote(id, titleText, contentText);

        if(checkUpdateData) {
            Toast toast = Toast.makeText(NoteEditActivity.this, "Note updated!", Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(v.getContext(), NotesActivity.class);

            startActivity(i);
        } else {
            Toast toast = Toast.makeText(NoteEditActivity.this, "Updating note failed!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void delete(View v) {

        boolean checkDeleteData = DB.deleteNote(id);

        if(checkDeleteData) {
            Toast toast = Toast.makeText(NoteEditActivity.this, "Note deleted!", Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(v.getContext(), NotesActivity.class);

            startActivity(i);
        } else {
            Toast toast = Toast.makeText(NoteEditActivity.this, "Deleting note failed!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}