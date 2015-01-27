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
    public static final String LOCATION = "Location";
    public static final String DESCRIPTION = "Description";
    public static final String NAME = "Name";
    public static final String PICTURE = "Picture";
    public static final String COVER = "Cover";

    public static void saveUserData(SessionObject user, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(OBJECT_ID, user.getObjectId());
        editor.putString(SESSION_TOKEN, user.getSessionToken());
        editor.putString(USERNAME, user.getUsername());
        editor.putString(NAME, user.getName());
        editor.putString(LOCATION, user.getLocation());
        editor.putString(DESCRIPTION, user.getDescription());
        editor.putString(PICTURE, user.getPicture());
        editor.putString(COVER, user.getCover());
        editor.commit();
    }

    public static SessionObject getUserData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);

        String objectId = prefs.getString(OBJECT_ID, null);
        String sessionToken = prefs.getString(SESSION_TOKEN, null);
        String username = prefs.getString(USERNAME, null);
        String name = prefs.getString(NAME, null);
        String location = prefs.getString(LOCATION, null);
        String description = prefs.getString(DESCRIPTION, null);
        String picture = prefs.getString(PICTURE, null);
        String cover = prefs.getString(COVER, null);

        if (objectId != null && sessionToken != null && username != null) {
            SessionObject user = new SessionObject(objectId, sessionToken, username, name, location, description, picture, cover);
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
