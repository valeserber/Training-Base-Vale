package com.example.valeriaserber.trainingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.valeriaserber.trainingapp.model.SessionObject;

public class UserUtility {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String OBJECT_ID = "ObjectId";
    public static final String SESSION_TOKEN = "SessionToken";
    public static final String USERNAME = "Username";

    public static void saveUserData(SessionObject user, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(OBJECT_ID, user.getObjectId());
        editor.putString(SESSION_TOKEN, user.getSessionToken());
        editor.putString(USERNAME, user.getUsername());
        editor.commit();
    }

    public static SessionObject getUserData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);

        String objectId = prefs.getString(OBJECT_ID, null);
        String sessionToken = prefs.getString(SESSION_TOKEN, null);
        String username = prefs.getString(USERNAME, null);

        if (objectId != null && sessionToken != null && username != null) {
            SessionObject user = new SessionObject(objectId, sessionToken, username);
            return user;
        }
        return null;
    }

    public static void removeUserData(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }
}