package com.project.kn.scrumsimulator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.kn.scrumsimulator.boardview.BoardItem;
import com.project.kn.scrumsimulator.boardview.BoardView;
import com.project.kn.scrumsimulator.boardview.Item;
import com.project.kn.scrumsimulator.boardview.SimpleBoardAdapter;
import com.project.kn.scrumsimulator.config.DatabaseConfig;
import com.project.kn.scrumsimulator.sprint.Player;
import com.project.kn.scrumsimulator.sprint.SprintUtils;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_old);
//        DatabaseConfig db = new DatabaseConfig();
//    }

    ArrayList<Item> list = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    public static int NUMBER_OF_SPRINT_DAY = 0;
    public static int COUNT_OF_SPRINT_DAYS = 3;
    public static int NUMBER_OF_SPRINT = 0;
    public static int COUNT_OF_SPRINTS = 3;

    public static int ITEM_POS;
    public static int ITEM_I;

    public static Button workButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workButton = findViewById(R.id.button_to_work);
        SprintUtils.setProgressBars(this);
        final BoardView boardView = (BoardView)findViewById(R.id.boardView);
        boardView.SetColumnSnap(false);
        boardView.SetColumnSnap(true);
        final ArrayList<SimpleBoardAdapter.SimpleColumn> data = new ArrayList<>();
        list.add(new Item("Создать каркас приложения", "Description 1", 1, 10));
        list.add(new Item("Подключить базу данных", "Description 1", 2, 2));
        list.add(new Item("Создать таблицы в базе данных", "Description 1", 3, 5));
        list.add(new Item("Добавить интеграцию с сервисами", "Description 1", 4, 7));
        list.add(new Item("Добавить документацию", "Description 1", 5, 2));
        final ArrayList<Item> empty = new ArrayList<>();
        data.add(new SimpleBoardAdapter.SimpleColumn("Backlog",(ArrayList)list));
        data.add(new SimpleBoardAdapter.SimpleColumn("TODO",(ArrayList)empty));
        data.add(new SimpleBoardAdapter.SimpleColumn("In progress",(ArrayList)empty));
        data.add(new SimpleBoardAdapter.SimpleColumn("Done",(ArrayList)empty));

        players.add(new Player("player 1"));

        final SimpleBoardAdapter boardAdapter = new SimpleBoardAdapter(this,data);
        boardView.setAdapter(boardAdapter);
        boardView.setOnDoneListener(new BoardView.DoneListener() {
            @Override
            public void onDone() {
                Log.e("scroll","done");
            }
        });
        workButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SprintUtils.updateProgressBars();
                Random random = new Random();
                Item item = (Item) boardAdapter.columns.get(ITEM_POS).objects.get(ITEM_I);
                int randomHours = random.nextInt(8 - 1 + 1) + 1;
                int newHours = item.hoursCount - randomHours;
                item.hoursCount = Math.max(newHours, 0);
                String msg = String.format("Списано %d часов с карточки %d : %d", randomHours, ITEM_POS, ITEM_I);
                Log.e("work",msg);
                TextView taskHours = boardAdapter.columns.get(ITEM_POS).views.get(ITEM_I).findViewById(R.id.task_hours);
                taskHours.setText(String.valueOf(item.hoursCount));

                if (item.hoursCount == 0) {
                    workButton.setEnabled(false);
                }

                //TODO здесь будет список игроков и мы должны вытаскивать того, кто выбран в mainActivity
                players.get(0).isWorkToday = true;
                if (isAllPlayersWorked()) {
                    SprintUtils.incCountOfHoursInDay(randomHours);
                    SprintUtils.allPlayersWorked(v);
                }
            }
        });
    }

    private boolean isAllPlayersWorked() {

        return players.stream().allMatch(p -> p.isWorkToday);
    }

    private boolean isSprintEnd() {

        return NUMBER_OF_SPRINT_DAY >= COUNT_OF_SPRINT_DAYS;
    }
}