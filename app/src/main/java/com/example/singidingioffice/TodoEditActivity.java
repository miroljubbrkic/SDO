package com.example.singidingioffice;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DBHelper.DBHelper;

import java.util.ArrayList;

public class TodoEditActivity extends AppCompatActivity {

    String id;
    EditText content;
    Spinner importanceLevel;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_edit);

        content = findViewById(R.id.todoEditContent);
        importanceLevel = findViewById(R.id.todoEditImportanceLevel);
        DB = new DBHelper(this);

        id = (String) getIntent().getSerializableExtra("id");
        String oldContent = (String) getIntent().getSerializableExtra("content");
        Integer oldImportanceLevel = (Integer) getIntent().getSerializableExtra("importanceLevel");

        content.setText(oldContent);

        ImageButton todoEditBack = findViewById(R.id.todoAdd);
        todoEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });

        ImageButton todoEditcheck = findViewById(R.id.todoEditCheck);
        todoEditcheck.setOnClickListener(new View.OnClickListener() {
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

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        importanceLevel.setAdapter(arrayAdapter);
        importanceLevel.setSelection(oldImportanceLevel-1);
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
        String contentText = content.getText().toString();
        String importanceLevelText = importanceLevel.getSelectedItem().toString();
        Integer impLvl = Integer.parseInt(importanceLevelText);

        boolean checkUpdateData = DB.updateTodo(id, contentText, false, impLvl);

        if(checkUpdateData) {
            Toast toast = Toast.makeText(TodoEditActivity.this, "Todo updated!", Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(v.getContext(), TodosActivity.class);

            startActivity(i);
        } else {
            Toast toast = Toast.makeText(TodoEditActivity.this, "Updating todo failed!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}