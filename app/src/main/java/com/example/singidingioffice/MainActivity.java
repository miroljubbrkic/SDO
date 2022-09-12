package com.example.singidingioffice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton todosBtn = findViewById(R.id.todoAdd);
        todosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTodos(v);
            }
        });

        ImageButton notesBtn = findViewById(R.id.notesBtn);
        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotes(v);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void openTodos(View v) {
        Intent i = new Intent(v.getContext(), TodosActivity.class);
        startActivity(i);
    }

    public void openNotes(View v) {
        Intent i = new Intent(v.getContext(), NotesActivity.class);
        startActivity(i);
    }




}