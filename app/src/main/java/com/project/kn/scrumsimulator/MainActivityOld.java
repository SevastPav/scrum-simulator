package com.project.kn.scrumsimulator;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.kn.scrumsimulator.config.DatabaseConfig;
import com.project.kn.scrumsimulator.db.PlayerRepository;

import java.util.concurrent.CompletableFuture;

public class MainActivityOld extends AppCompatActivity {

    public static EditText loginField, passwordField;
    public static Button loginButton, registerButton;

    private final PlayerRepository playerRepository;

    public MainActivityOld() {
        DatabaseConfig dbConfig = new DatabaseConfig();
        playerRepository = new PlayerRepository(dbConfig);
    }

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
                try {
                    boolean isSuccess = CompletableFuture.supplyAsync(() -> playerRepository.verifyLoginAndPassword(loginField.getText().toString(), passwordField.getText().toString())).get();

                    if (isSuccess) {
                        Intent intent = new Intent(v.getContext(), StartPage.class);
                        startActivity(intent);
                    } else {
                        openSiteDialogWithErrorMsg(v);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private static void openSiteDialogWithErrorMsg(View v) {

        String msg = "Ошибка в заполнении данных";
        final AlertDialog aboutDialog = new AlertDialog.Builder(v.getContext())
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();

        aboutDialog.show();
    }
}