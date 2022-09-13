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

public class TodoAddActivity extends AppCompatActivity {

    EditText content;
    Spinner importanceLevel;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_add);

        content = findViewById(R.id.noteAddTitle);
        importanceLevel = findViewById(R.id.todoAddImportanceLevel);
        DB = new DBHelper(this);

        ImageButton todoAddBack = findViewById(R.id.todoAddBack);
        todoAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });

        ImageButton todocheck = findViewById(R.id.todoAddCheck);
        todocheck.setOnClickListener(new View.OnClickListener() {
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


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(TodoAddActivity.this, TodosActivity.class));
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
        String id = DBHelper.createID();

        boolean checkInsertData = DB.insertTodo(id, contentText, false, impLvl);

        if(checkInsertData) {
            Toast toast = Toast.makeText(TodoAddActivity.this, "Todo added!", Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(v.getContext(), TodosActivity.class);

            startActivity(i);
        } else {
            Toast toast = Toast.makeText(TodoAddActivity.this, "Adding todo failed!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}