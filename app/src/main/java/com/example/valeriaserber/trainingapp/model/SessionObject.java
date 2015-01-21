package com.example.valeriaserber.trainingapp.model;

public class SessionObject {

    private String objectId;
    private String sessionToken;
    private String username;

    public String getObjectId() {
        return objectId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getUsername() {
        return username;
    }

    public SessionObject(String objectId, String sessionToken, String username) {
        this.objectId = objectId;
        this.sessionToken = sessionToken;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
