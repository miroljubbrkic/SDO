package com.example.singidingioffice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.UUID;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserNotes(id TEXT primary key, title TEXT, content TEXT)");
        DB.execSQL("create Table UserTodos(id TEXT primary key, contetnt TEXT, checked TEXT, importance_level INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UserNotes");
        DB.execSQL("drop Table if exists UserTodos");
    }

    public static String createID(){
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public Boolean insertNote(String id, String title, String content) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("content", content);

        long result = DB.insert("UserNotes", null, contentValues);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertTodo(String id, String contetnt, Boolean checked, int importance_level) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("contetnt", contetnt);
        contentValues.put("checked", checked);
        contentValues.put("importance_level", importance_level);

        long result = DB.insert("UserTodos", null, contentValues);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }



    public Boolean updateNote(String id, String title, String content) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        Cursor cursor = DB.rawQuery("Select * from UserNotes where id = ?", new String[] {id});

        if (cursor.getCount() > 0) {
            long result = DB.update("UserNotes", contentValues, "id=?", new String[] {id});

            if(result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean updateTodo(String id, String contetnt, Boolean checked, int importance_level) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contetnt", contetnt);
        contentValues.put("checked", checked);
        contentValues.put("importance_level", importance_level);
        Cursor cursor = DB.rawQuery("Select * from UserTodos where id = ?", new String[] {id});

        if (cursor.getCount() > 0) {
            long result = DB.update("UserTodos", contentValues, "id=?", new String[] {id});

            if(result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteNote(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserNotes where id = ?", new String[] {id});

        if (cursor.getCount() > 0) {
            long result = DB.delete("UserNotes", "id=?", new String[] {id});

            if(result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteTodo(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserTodos where id = ?", new String[] {id});

        if (cursor.getCount() > 0) {
            long result = DB.delete("UserTodos", "id=?", new String[] {id});

            if(result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    public LinkedList<Note> getNotes() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserNotes", null);
        LinkedList<Note> notes = new LinkedList<>();

        int iId = cursor.getColumnIndex("id");
        int iTitle = cursor.getColumnIndex("title");
        int iContent = cursor.getColumnIndex("content");

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String id = cursor.getString(iId);
            String title = cursor.getString(iTitle);
            String contnt = cursor.getString(iContent);
            Note temp = new Note(id, title, contnt);

            notes.add(temp);
        }
        return notes;

    }

    public boolean getBoolean(int columnIndex, Cursor cursor) {
        if (cursor.isNull(columnIndex) || cursor.getShort(columnIndex) == 0) {
            return false;
        } else {
            return true;
        }
    }

    public LinkedList<Todo> getTodos() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserTodos", null);
        LinkedList<Todo> todos = new LinkedList<>();

        int iId = cursor.getColumnIndex("id");
        int iContetnt = cursor.getColumnIndex("contetnt");
        int iChecked = cursor.getColumnIndex("checked");
        int iImportance_level = cursor.getColumnIndex("importance_level");

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String id = cursor.getString(iId);
            String contetnt = cursor.getString(iContetnt);
            Boolean checked = getBoolean(iChecked, cursor);
            Integer importance_level = cursor.getInt(iImportance_level);
            Todo temp = new Todo(id, contetnt, checked, importance_level);

            todos.add(temp);
        }
        return todos;

    }



}



