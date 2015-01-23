package com.example.valeriaserber.trainingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import com.example.valeriaserber.trainingapp.model.SignUpObject;
import com.example.valeriaserber.trainingapp.utilities.RestError;
import com.example.valeriaserber.trainingapp.utilities.UserUtility;
import com.example.valeriaserber.trainingapp.utilities.Validator;


import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends ActionBarActivity{

    private TextView mTermsConditions;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mJoinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.topbar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setUi();
        setListeners();
    }

    public void setUi() {
        mEmail = (EditText) findViewById(R.id.activity_signup_email_text_edit);
        mPassword = (EditText) findViewById(R.id.activity_signup_password_text_edit);
        mConfirmPassword = (EditText) findViewById(R.id.activity_signup_confirm_pass_text_edit);
        mTermsConditions = (TextView) findViewById(R.id.activity_signup_terms_text_view);
        mJoinButton = (Button) findViewById(R.id.activity_signup_join_button);
    }

    public void setListeners() {
        mTermsConditions.setMovementMethod(LinkMovementMethod.getInstance());
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                joinButton(view);
            }
        });
    }

    public void joinButton(View view) {
        boolean requiredEmail = Validator.validateRequiredField(mEmail, getString(R.string.email_required));
        boolean requiredPassword = Validator.validateRequiredField(mPassword, getString(R.string.password_required));
        boolean requiredConfirmPassword = Validator.validateRequiredField(mConfirmPassword, getString(R.string.confirm_password_required));
        boolean valid = true;
        boolean all = requiredEmail && requiredPassword && requiredConfirmPassword;

        if (!requiredEmail && !requiredPassword && !requiredConfirmPassword) {
            showToast(getString(R.string.all_required));
            return;
        }
        if (requiredEmail) {
            valid = Validator.validateEmail(mEmail, TrainingApplication.getAppContext());
        }
        if (requiredPassword && requiredConfirmPassword) {
            boolean aux = Validator.validatePasswords(mPassword, mConfirmPassword, TrainingApplication.getAppContext());
            valid &= aux;
        }
        if (all && valid) {
            signUp(mEmail.getText().toString().trim(), mPassword.getText().toString().trim());
        }
    }

    private void startUserActivity() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    private void signUp(final String email, String password) {
        SignUpObject user = new SignUpObject(email, password);
        TrainingApplication.sUserService.signUp(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                user.setUsername(email);
                UserUtility.saveUserData(user, getApplicationContext());
                startUserActivity();
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getResponse() == null) return;
                RestError body = (RestError) error.getBodyAs(RestError.class);
                switch (body.code) {
                    case 202:
                        showToast(getString(R.string.email_exists));
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
}