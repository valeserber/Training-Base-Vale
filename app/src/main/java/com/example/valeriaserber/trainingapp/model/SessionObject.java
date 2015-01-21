package com.example.valeriaserber.trainingapp.model;

import java.io.Serializable;

public class SessionObject implements Serializable{

    private String objectId;
    private String sessionToken;
    private String username;
    private String name;
    private String location;
    private String description;
    private String picture;

    public String getObjectId() {
        return objectId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public SessionObject(String objectId, String sessionToken, String username, String name,
                         String location, String description, String picture) {
        this.objectId = objectId;
        this.sessionToken = sessionToken;
        this.username = username;
        this.name = name;
        this.location = location;
        this.description = description;
        this.picture = picture;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}