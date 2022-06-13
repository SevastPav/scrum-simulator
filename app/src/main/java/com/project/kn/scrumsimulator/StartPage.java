package com.project.kn.scrumsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    public StartPage() {
        DatabaseConfig dbConfig = new DatabaseConfig();
        projectRepository = new ProjectRepository(dbConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        startButton = findViewById(R.id.start_game);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Optional<ProjectEntity> projectOpt = CompletableFuture.supplyAsync(projectRepository::findAll).get().stream().sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2)).findAny();
                    ProjectEntity project = projectOpt.get();
                    //TODO вывести название и описание проекта на экран
                    String name = project.getName();
                    String description = project.getDescription();
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("projectId", project.getId());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}