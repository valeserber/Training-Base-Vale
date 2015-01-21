package com.example.valeriaserber.trainingapp.utilities;

import android.content.Context;
import android.text.Editable;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.valeriaserber.trainingapp.R;

import java.util.regex.Pattern;

public class Validator {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateRequiredField(EditText field, String error){
        field.setError(null);
        Editable text = field.getText();
        if ((text == null) || (text != null && text.toString().trim().isEmpty())) {
                field.setError(error);
                return false;
        }
        return true;
    }

    public static boolean validateEmail(EditText emailField, Context context){
        emailField.setError(null);
        if(!Pattern.matches(EMAIL_REGEX, emailField.getText().toString().trim())){
            emailField.setError(context.getString(R.string.email_invalid));
            return false;
        }
        return true;
    }

    public static boolean validatePasswords(EditText passwordField, EditText confirmPasswordField, Context context){
        String password = passwordField.getText().toString().trim();
        String confirmPassword = confirmPasswordField.getText().toString().trim();

        passwordField.setError(null);
        confirmPasswordField.setError(null);

        if(!password.equals(confirmPassword)){
            confirmPasswordField.setError(context.getString(R.string.equal_passwords));
            return false;
        }
        return true;
    }
}