package com.example.valeriaserber.trainingapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valeriaserber.trainingapp.R;
import com.example.valeriaserber.trainingapp.TrainingApplication;
import com.example.valeriaserber.trainingapp.model.User;
import com.example.valeriaserber.trainingapp.utilities.RestError;
import com.example.valeriaserber.trainingapp.utilities.UserUtility;
import com.example.valeriaserber.trainingapp.utilities.Validator;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends Activity {

    private EditText mEmail;
    private EditText mPassword;
    private TextView mTermsConditions;
    private Button mLoginButton;
    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUi();
        setListeners();
        User session = UserUtility.getUserData(this);
        if (session != null) {
            startUserActivity();
        }
    }

    private void setUi() {
        mTermsConditions = (TextView) findViewById(R.id.activity_login_terms_text_view);
        mEmail = (EditText) findViewById(R.id.activity_login_email_text_edit);
        mPassword = (EditText) findViewById(R.id.activity_login_password_text_edit);
        mLoginButton = (Button) findViewById(R.id.activity_login_login_button);
        mSignUpButton = (Button) findViewById(R.id.activity_login_signup_button);
    }

    private void setListeners() {
        mTermsConditions.setMovementMethod(LinkMovementMethod.getInstance());
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                logInButton(view);
            }
        });
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signUpButton(view);
            }
        });
    }

    private void startUserActivity() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void logInButton(View view) {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        boolean requiredEmail = Validator.validateRequiredField(mEmail, getString(R.string.email_required));
        boolean requiredPassword = Validator.validateRequiredField(mPassword, getString(R.string.password_required));
        if (!requiredEmail && !requiredPassword) {
            showToast(getString(R.string.all_required));
        } else if (requiredEmail && Validator.validateEmail(mEmail, this)) {
            logIn(email, password);
        }
    }

    private void logIn(String email, String password){
        TrainingApplication.sUserService.logIn(email, password, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                UserUtility.saveUserData(user, getApplicationContext());
                startUserActivity();
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getResponse() == null) return;
                RestError body = (RestError) error.getBodyAs(RestError.class);
                switch (body.code) {
                    case 101:
                        showToast(getString(R.string.invalid_login));
                        return;
                    default:
                        showToast(getString(R.string.network_error));
                        return;
                }
            }
        });
    }

    private void showToast(CharSequence text){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void signUpButton(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}