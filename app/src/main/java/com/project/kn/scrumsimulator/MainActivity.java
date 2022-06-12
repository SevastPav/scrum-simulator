package com.project.kn.scrumsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.kn.scrumsimulator.boardview.BoardView;
import com.project.kn.scrumsimulator.boardview.Item;
import com.project.kn.scrumsimulator.boardview.SimpleBoardAdapter;
import com.project.kn.scrumsimulator.events.Card;
import com.project.kn.scrumsimulator.events.CardUtils;
import com.project.kn.scrumsimulator.events.Event;
import com.project.kn.scrumsimulator.events.Problem;
import com.project.kn.scrumsimulator.events.Solution;
import com.project.kn.scrumsimulator.sprint.Player;
import com.project.kn.scrumsimulator.sprint.SprintUtils;
import com.project.kn.scrumsimulator.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Random;

import lombok.Getter;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_old);
//        DatabaseConfig db = new DatabaseConfig();
//    }

    private static ArrayList<Card> cards;

    static {
        cards = new ArrayList<>();
        cards.add(new Solution("solution 1", "solution description"));
        cards.add(new Solution("solution 2", "solution description"));
        cards.add(new Problem("problem 1", "problem description"));
        cards.add(new Problem("problem 2", "problem description"));
        cards.add(new Event("event 2", "event description"));
        cards.add(new Event("event 2", "event description"));
    }

    ArrayList<Item> list = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    public static int NUMBER_OF_SPRINT_DAY = 0;
    public static int COUNT_OF_SPRINT_DAYS = 3;
    public static int NUMBER_OF_SPRINT = 0;
    public static int COUNT_OF_SPRINTS = 3;

    public static int ITEM_POS;
    public static int ITEM_I;

    public static Button workButton;

    Context context = this;

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
                CardUtils.show(context);

                SprintUtils.updateProgressBars();
                Item item = (Item) boardAdapter.columns.get(ITEM_POS).objects.get(ITEM_I);
                int randomHours = RandomUtils.getRandomIntBetween(1, 8);
                int newHours = item.hoursCount - randomHours;
                item.hoursCount = Math.max(newHours, 0);
                String msg = String.format("Списано %d часов с карточки %d : %d", randomHours, ITEM_POS, ITEM_I);
                Log.e("work",msg);
                TextView taskHours = boardAdapter.columns.get(ITEM_POS).views.get(ITEM_I).findViewById(R.id.task_hours);
                taskHours.setText(String.valueOf(item.hoursCount));

                if (item.hoursCount == 0) {
                    workButton.setEnabled(false);
                }

                //Выводим информацию о событии и добавляем в спринт
                CardUtils.generateCard(v);

                //TODO здесь будет список игроков и мы должны вытаскивать того, кто выбран в mainActivity
                players.get(0).isWorkToday = true;
                if (isAllPlayersWorked()) {
                    SprintUtils.incCountOfHoursInDay(randomHours);
                    SprintUtils.allPlayersWorked(v);
                }
            }
        });
    }

    public static ArrayList<Card> getCards() {

        return cards;
    }

    private boolean isAllPlayersWorked() {

        return players.stream().allMatch(p -> p.isWorkToday);
    }

    private boolean isSprintEnd() {

        return NUMBER_OF_SPRINT_DAY >= COUNT_OF_SPRINT_DAYS;
    }
}