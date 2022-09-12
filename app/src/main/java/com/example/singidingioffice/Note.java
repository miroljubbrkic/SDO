package com.example.singidingioffice;

public class Note {
    private String id, title, contetn;

    public Note(String id, String title, String contetn) {
        this.id = id;
        this.title = title;
        this.contetn = contetn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContetn() {
        return contetn;
    }

    public void setContetn(String contetn) {
        this.contetn = contetn;
    }
}
