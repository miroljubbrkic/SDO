package com.example.model;

public class Todo {
    private String id;
    private String contetnt;
    private boolean checked;
    private int importance_level;

    public Todo(String id, String contetnt, boolean checked, int importance_level) {
        this.id = id;
        this.contetnt = contetnt;
        this.checked = checked;
        this.importance_level = importance_level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContetnt() {
        return contetnt;
    }

    public void setContetnt(String contetnt) {
        this.contetnt = contetnt;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getImportance_level() {
        return importance_level;
    }

    public void setImportance_level(int importance_level) {
        this.importance_level = importance_level;
    }
}
