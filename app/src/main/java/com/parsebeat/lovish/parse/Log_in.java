package com.parsebeat.lovish.parse;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Lovish-THINKPAD on 15-01-2016.
 */
public class Log_in extends AppCompatActivity {
    protected EditText email;
    protected EditText password;
    protected Button login;
    protected Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.email_sign_in_button);
        signup = (Button) findViewById(R.id.signupButton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Log_in.this, Sign_up.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid = email.getText().toString();
                String passwd = password.getText().toString();

                emailid.trim();
                passwd.trim();
                if (emailid.isEmpty() || passwd.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Log_in.this);
                    builder.setMessage("pls Re check email & passwrod")
                            .setTitle("Login Error")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    setProgressBarIndeterminateVisibility(true);

                    ParseUser.logInInBackground(emailid, passwd, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            setProgressBarIndeterminateVisibility(false);

                            if (e == null) {
                                // Success!
                                Intent intent = new Intent(Log_in.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                // Fail
                                AlertDialog.Builder builder = new AlertDialog.Builder(Log_in.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle("Check email & password")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                        });
                    }
                }}
        );
    }
}
