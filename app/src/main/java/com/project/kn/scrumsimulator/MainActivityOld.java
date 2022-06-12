package com.project.kn.scrumsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.kn.scrumsimulator.config.DatabaseConfig;

public class MainActivityOld extends AppCompatActivity {

    public static EditText loginField, passwordField;
    public static Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        loginField = findViewById(R.id.login);
        passwordField = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.button_login);
        registerButton = findViewById(R.id.button_to_register);

        DatabaseConfig db = new DatabaseConfig();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StartPage.class);
                startActivity(intent);
            }
        });
    }
}