package com.example.singidingioffice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DBHelper.DBHelper;
import com.example.model.Todo;

import java.util.ArrayList;
import java.util.LinkedList;

public class TodosActivity extends AppCompatActivity {

    LinearLayout mainTodoLayout;
    ArrayList<ConstraintLayout> children;
    LinkedList<Todo> todos;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        mainTodoLayout = findViewById(R.id.mainTodoLayout);
        children = new ArrayList<>();
//        todos = Api.getTodos();
        DB = new DBHelper(this);
        todos = DB.getTodos();

//        String addContent = (String) getIntent().getSerializableExtra("addContent");
//        String addImportanceLevel = (String) getIntent().getSerializableExtra("addImportanceLevel");
//        if(!(addContent == null) || !(addImportanceLevel == null)){
//            System.out.println("if petlja");
//            int size = todos.size();
//            int id = todos.get(size-1).getId();
//            Todo newTodo = new Todo(size, addContent,false, Integer.parseInt(addImportanceLevel));
//            todos.addLast(newTodo);
//        }

//        Integer editId = (Integer) getIntent().getSerializableExtra("editId");
//        String editContent = (String) getIntent().getSerializableExtra("editContent");
//        String editImportanceLevel = (String) getIntent().getSerializableExtra("editImportanceLevel");
//        if(!(editId == null) || !(editContent == null || !(editImportanceLevel == null))){
//            System.out.println("if petlja");
//            for(Todo t:todos){
//                if (t.getId() == editId) {
//                    t.setContetnt(editContent);
//                    t.setImportance_level(Integer.parseInt(editImportanceLevel));
//                }
//            }
//        }


        ImageButton notesBtn = findViewById(R.id.todoAdd);
        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTodoAdd(v);
            }
        });


        TextView title = findViewById(R.id.titleLabel);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainScreen(v);
            }
        });

        ImageButton addTodo = findViewById(R.id.notesBtn);
        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotes(v);
            }
        });


        LayoutInflater todoInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        generateData(todoInflater,todos, mainTodoLayout);



    }

//    public Todo searchTodosById(LinkedList<Todo> todos, int id){
//        for (Todo t:todos){
//            if (t.getId() == id){
//                return t;
//            }
//        }
//        return null;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TodosActivity.this, MainActivity.class));
        finish();
    }

    public void openMainScreen(View v) {
        Intent i = new Intent(v.getContext(), MainActivity.class);
        startActivity(i);
    }

    public void openNotes(View v) {
        Intent i = new Intent(v.getContext(), NotesActivity.class);
        startActivity(i);
    }

    public void openNoteAdd(View v) {
        Intent i = new Intent(v.getContext(), NoteAddActivity.class);
//        i.putExtra("todos", todos);
        startActivity(i);
    }

    public void openTodoAdd(View v) {
        Intent i = new Intent(v.getContext(), TodoAddActivity.class);
        startActivity(i);
    }

    public void openTodoEdit(View v, Todo t) {
        Intent i = new Intent(v.getContext(), TodoEditActivity.class);
        String id = t.getId();
        String content = t.getContetnt();
        Integer importanceLevel = t.getImportance_level();
        i.putExtra("id", id);
        i.putExtra("content", content);
        i.putExtra("importanceLevel", importanceLevel);
        startActivity(i);
    }

    public void setTextColor(CheckBox cb, Todo t) {
        switch (t.getImportance_level()){
            case 1:
                cb.setTextColor(Color.RED);
                break;
            case 2:
                cb.setTextColor(Color.YELLOW);
                break;
            case 3:
                cb.setTextColor(Color.BLUE);
                break;
            case 4:
                cb.setTextColor(Color.GRAY);
                break;

        }
    }

    public void generateData(LayoutInflater inflater, LinkedList<Todo> lista, LinearLayout mainLayout) {
        mainLayout.removeAllViews();
        for (Todo t : lista) {
            ConstraintLayout subLayout = (ConstraintLayout) inflater.inflate(R.layout.todo_layout,mainLayout,false);
            children.add(subLayout);
// IZMANA BTN
            ImageButton todoEditBtn = subLayout.findViewById(R.id.todoEditBtn);
            todoEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTodoEdit(v,t);
                }
            });
// BRISANJE BTN
            ImageButton todoDelBtn = subLayout.findViewById(R.id.todoDeleteBtn);
            todoDelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checkDeleteData = DB.deleteTodo(t.getId());

                    if(checkDeleteData) {
                        Toast toast = Toast.makeText(TodosActivity.this, "Todo deleted!", Toast.LENGTH_SHORT);
                        toast.show();
//                        mainLayout.removeView(children.get(todos.indexOf(t)));
//                        children.remove(todos.indexOf(t));
                        Intent i = new Intent(v.getContext(), TodosActivity.class);
                        startActivity(i);
                    } else {
                        Toast toast = Toast.makeText(TodosActivity.this, "Deleting todo failed!", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            });

            CheckBox cb = subLayout.findViewById(R.id.todoCheckBox);
            cb.setText(t.getContetnt());

            if (t.isChecked()) {
                cb.setChecked(t.isChecked());
                cb.setPaintFlags(cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                cb.setTextColor(Color.DKGRAY);
            } else if (!t.isChecked()) {
                cb.setChecked(t.isChecked());
                cb.setPaintFlags(cb.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                setTextColor(cb, t);
            }


            setTextColor(cb, t);

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                    if (cb.isChecked()) {
                        cb.setPaintFlags(cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        cb.setTextColor(Color.DKGRAY);
                        DB.updateTodo(t.getId(), t.getContetnt(), !t.isChecked(), t.getImportance_level());
                    }
                    if (!cb.isChecked()) {
                        cb.setPaintFlags(cb.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        setTextColor(cb, t);
                        DB.updateTodo(t.getId(), t.getContetnt(), !t.isChecked(), t.getImportance_level());
                    }
                }
            });

            mainLayout.addView(subLayout);
        }
    }
}