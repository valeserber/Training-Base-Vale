package com.example.valeriaserber.trainingapp.model;

public class News {

    private int id;
    private String title;
    private int userId;
    private String text;
    private String picture;

    public News() {
    }

    public News(int id, String title, int userId, String text, String picture) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.text = text;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public String getPicture() {
        return picture;
    }
}