package com.project.kn.scrumsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.kn.scrumsimulator.config.DatabaseConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseConfig db = new DatabaseConfig();
    }
}