package com.example.singidingioffice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.DBHelper.DBHelper;
import com.example.model.Note;

import java.util.ArrayList;
import java.util.LinkedList;

public class NotesActivity extends AppCompatActivity {

    LinearLayout mainNoteLayout;
    ArrayList<ConstraintLayout> children;
    LinkedList<Note> notes;
//    Cursor notes;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mainNoteLayout = findViewById(R.id.mainNoteLayout);
        children = new ArrayList<>();
        DB = new DBHelper(this);
        notes = DB.getNotes();

        ImageButton notesBtn = findViewById(R.id.todoAdd);
        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTodos(v);
            }
        });


        TextView title = findViewById(R.id.titleLabel);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainScreen(v);
            }
        });

        ImageButton addNote = findViewById(R.id.notesBtn);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNoteAdd(v);
            }
        });

        LayoutInflater noteInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        generateData(noteInflater,notes, mainNoteLayout);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotesActivity.this, MainActivity.class));
        finish();
    }

    public void openMainScreen(View v) {
        Intent i = new Intent(v.getContext(), MainActivity.class);
        startActivity(i);
    }

    public void openTodos(View v) {
        Intent i = new Intent(v.getContext(), TodosActivity.class);
        startActivity(i);
    }

    public void openNoteAdd(View v) {
        Intent i = new Intent(v.getContext(), NoteAddActivity.class);
        startActivity(i);
    }

    public void openNoteEdit(View v, Note n) {
        Intent i = new Intent(v.getContext(), NoteEditActivity.class);
        String id = n.getId();
        String title = n.getTitle();
        String content = n.getContetn();
        i.putExtra("id", id);
        i.putExtra("title", title);
        i.putExtra("content", content);
        startActivity(i);

    }

    public void generateData(LayoutInflater inflater, LinkedList<Note> lista, LinearLayout mainLayout) {
        mainLayout.removeAllViews();
        if (lista.size()>0){
            for (Note n : lista) {
                ConstraintLayout subLayout = (ConstraintLayout) inflater.inflate(R.layout.note_layout,mainLayout,false);
                children.add(subLayout);

                LinearLayout nc = subLayout.findViewById(R.id.noteCard);
                TextView title = subLayout.findViewById(R.id.noteTitleLabel);
                TextView content = subLayout.findViewById(R.id.noteContentLabel);

                title.setText(n.getTitle());
                content.setText(n.getContetn());

                nc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openNoteEdit(v, n);
                    }
                });

                mainLayout.addView(subLayout);
            }
        }

    }


}