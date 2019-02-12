package com.example.thomasd06.myfirstapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {
    private TextView signUpTextView;
    private EditText emailEditText, passwordEditText, nameEditText;
    private Button logInButton, signUpButton;
    private CheckBox isBarberCheckbox;
    private boolean isBarber;


    public static final String APPLICATION_ID = "F0453682-38B0-4EC8-FFE7-219A98811900";
    public static final String SECRET_KEY = "113E9372-EF68-AF3C-FF56-BF518A159700";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        Backendless.initApp(this, APPLICATION_ID, SECRET_KEY);

        signUpButton = findViewById(R.id.btn_sign_up);
        logInButton = findViewById(R.id.btn_log_in);
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        nameEditText = findViewById(R.id.et_name);
        signUpTextView = findViewById(R.id.tv_sign_up);
        isBarberCheckbox = findViewById(R.id.cb_is_barber);

        String loggedInUser = Backendless.UserService.loggedInUser();

        if (!loggedInUser.isEmpty()) {
            Backendless.UserService.findById(loggedInUser, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    Backendless.UserService.setCurrentUser(response);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("LoginActivity", fault.toString());

                }
            });


        }
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e("LoginActivity", fault.toString());
                    }


                });
            }
        });


        signUpButton = findViewById(R.id.btn_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity", "Signing up");
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String name = nameEditText.getText().toString();
                Log.d("LoginActivity", email);
                Log.d("LoginActivity", password);
                Log.d("LoginActivity", name);

                if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setProperty("name", name);
                    user.setProperty("isBarber", isBarberCheckbox.isChecked());


                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(LoginActivity.this, response.getEmail() + " was registered", Toast.LENGTH_SHORT).show();
                            setUpLogIn();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e("LoginActivity", fault.toString());
                        }
                    });
                }

                emailEditText.getText().clear();
                passwordEditText.getText().clear();
                nameEditText.getText().clear();

            }
        });


        signUpTextView = findViewById(R.id.tv_sign_up);
        signUpTextView.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (signUpTextView.getText().equals("Sign Up!")) {
                    setUpSignUp();
                } else {
                    setUpLogIn();
                }
            }
        });

    }


    private void setUpLogIn() {
        nameEditText.setVisibility(View.GONE);
        signUpButton.setVisibility(View.GONE);
        logInButton.setVisibility(View.VISIBLE);
        isBarberCheckbox.setVisibility(View.GONE);
        signUpTextView.setText("Sign Up!");
    }

    private void setUpSignUp() {
        nameEditText.setVisibility(View.VISIBLE);
        signUpButton.setVisibility(View.VISIBLE);
        logInButton.setVisibility(View.GONE);
        isBarberCheckbox.setVisibility(View.VISIBLE);
        signUpTextView.setText("Cancel Sign Up!");
    }


}


