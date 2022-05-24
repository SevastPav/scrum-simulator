package com.project.kn.scrumsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ItemFormActivity extends AppCompatActivity {

    public static EditText taskName, taskDescription;
    public static Button saveButton, closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_form);

        taskName = findViewById(R.id.editTextTextTaskName);
        taskDescription = findViewById(R.id.editTextTextTaskDescription);
        saveButton = findViewById(R.id.button_to_save);
        closeButton = findViewById(R.id.button_to_close);


        Bundle arguments = getIntent().getExtras();
        taskName.setText(arguments.get("taskName").toString());
        taskDescription.setText(arguments.get("taskDescription").toString());

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}