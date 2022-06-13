package com.project.kn.scrumsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.kn.scrumsimulator.config.DatabaseConfig;
import com.project.kn.scrumsimulator.db.PlayerRepository;
import com.project.kn.scrumsimulator.db.ProjectRepository;
import com.project.kn.scrumsimulator.entity.ProjectEntity;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class StartPage extends AppCompatActivity {

    public static Button startButton;

    private final ProjectRepository projectRepository;

    private static int projectId = 1;

    public StartPage() {
        DatabaseConfig dbConfig = new DatabaseConfig();
        projectRepository = new ProjectRepository(dbConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        startButton = findViewById(R.id.start_game);

        TextView textView = findViewById(R.id.about_project);

        try {
            Optional<ProjectEntity> projectOpt = CompletableFuture.supplyAsync(projectRepository::findAll).get().stream().sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2)).findAny();
            ProjectEntity project = projectOpt.get();
            //TODO вывести название и описание проекта на экран
            String name = project.getName();
            String description = project.getDescription();
            projectId = project.getId();
            textView.setText(description);

        } catch (Exception e) {
            e.printStackTrace();
        }
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("projectId", projectId);
                startActivity(intent);
            }
        });
    }
}