package com.project.kn.scrumsimulator;

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
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    private static EditText loginField, emailField, passwordField, submitPasswordField;
    public static Button registerButton;

    private final PlayerRepository playerRepository;

    public RegisterActivity() {
        DatabaseConfig dbConfig = new DatabaseConfig();
        playerRepository = new PlayerRepository(dbConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_form);

        loginField = findViewById(R.id.editTextTextPersonName);
        emailField = findViewById(R.id.editTextTextEmailAddress2);
        passwordField = findViewById(R.id.editTextTextPassword2);
        submitPasswordField = findViewById(R.id.editTextTextPassword3);

        registerButton = findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginField.getText().toString().isEmpty() ||
                        emailField.getText().toString().isEmpty() ||
                        passwordField.getText().toString().isEmpty() ||
                        submitPasswordField.getText().toString().isEmpty()) {
                    openSiteDialogWithMsg(v, "Не все поля заполнены", false);
                    return;
                }

                if (!emailField.getText().toString().contains("@")) {
                    openSiteDialogWithMsg(v, "Некорректный email", false);
                    return;
                }

                if (!passwordField.getText().toString().equals(submitPasswordField.getText().toString())) {
                    openSiteDialogWithMsg(v, "Пароли не совпадают", false);
                    return;
                }

                boolean result = false;
                try {
                    result = CompletableFuture.supplyAsync(() -> playerRepository.registerNewPlayer(loginField.getText().toString(), passwordField.getText().toString(), emailField.getText().toString())).get();
                    if (!result) {
                        openSiteDialogWithMsg(v, "Произошла ошибка", false);
                        return;
                    }
                    openSiteDialogWithMsg(v, "Вы успешно зарегистрировались", true);
                } catch (Exception e) {
                    openSiteDialogWithMsg(v, "Произошла ошибка", false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void openSiteDialogWithMsg(View v, String msg, boolean isSuccess) {

        final AlertDialog aboutDialog = new AlertDialog.Builder(v.getContext())
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isSuccess) {
                            Intent intent = new Intent(v.getContext(), MainActivityOld.class);
                            startActivity(intent);
                        }
                    }
                }).create();

        aboutDialog.show();
    }
}