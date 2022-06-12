package com.project.kn.scrumsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.kn.scrumsimulator.boardview.BoardView;
import com.project.kn.scrumsimulator.boardview.Task;
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

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Card> cards;

    static {
        cards = new ArrayList<>();
        cards.add(new Solution("solution 1", "solution description"));
        cards.add(new Solution("solution 2", "solution description"));
        cards.add(new Problem("problem 1", "problem description"));
        cards.add(new Problem("problem 2", "problem description"));
        cards.add(new Event("event 2", "event description", true));
        cards.add(new Event("event 2", "event description", false));
    }

    ArrayList<Card> list = new ArrayList<>();
    private static Player currentPlayer;
    private static ArrayList<Player> players = new ArrayList<>();
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
        list.add(new Task("Пользователи могут безопасно обмениваться e-mail со списком заранее определенных получателей", "Description 1", 1, 24));
        list.add(new Task("Пользователи могут безопасно пересылать большие файлы", "Description 1", 2, 21));
        list.add(new Task("Пользователи могут установить ограничение по времени для чтения писем", "Description 1", 3, 27));
        list.add(new Task("Пользователи могут отправлять письма любым получателям", "Description 1", 4, 30));
        list.add(new Task("Администратор компании может отслеживать письма", "Description 1", 5, 16));
        list.add(new Task("Каждая организация может устанавливать политику безопасности и определить группы получателей", "Description 1", 6, 24));
        list.add(new Task("Пользователи могут эффективно управлять своими письмами", "Description 1", 7, 43));
        list.add(new Task("Пользователи и администратормы могут безопасно делать резервные копии", "Description 1", 8, 23));
        list.add(new Task("Пользователи и администраторы могут полностью удалять письма", "Description 1", 9, 36));
        list.add(new Task("Ползователи могут работать со своей почтой с мобильных телефонов", "Description 1", 10, 68));
        list.add(new Task("Пользователи могут безопасно обмениваться короткими сообщениями с любыми получателями", "Description 1", 11, 28));
        list.add(new Task("Пользователи не хотят получать спам-письма", "Description 1", 12, 24));

        final ArrayList<Task> empty = new ArrayList<>();
        data.add(new SimpleBoardAdapter.SimpleColumn("Backlog",(ArrayList)list));
        data.add(new SimpleBoardAdapter.SimpleColumn("TODO",(ArrayList)empty));
        data.add(new SimpleBoardAdapter.SimpleColumn("In progress",(ArrayList)empty));
        data.add(new SimpleBoardAdapter.SimpleColumn("Done",(ArrayList)empty));

        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));

        currentPlayer = players.get(0);

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
                Task item = (Task) boardAdapter.columns.get(ITEM_POS).objects.get(ITEM_I);
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

                currentPlayer.isWorkToday = true;
                if (isAllPlayersWorked()) {
                    SprintUtils.incCountOfHoursInDay(randomHours);
                    SprintUtils.allPlayersWorked(v);
                }
                currentPlayer = getRandomUnworkedPlayer();
            }
        });
    }

    public static ArrayList<Card> getCards() {

        return cards;
    }

    public static Player getRandomUnworkedPlayer() {

        return players.stream().filter(p -> !p.isWorkToday).findFirst().orElse(players.get(0));
    }

    public static ArrayList<Player> getPlayers() {

        return players;
    }

    public static boolean isAllPlayersWorked() {

        return players.stream().allMatch(p -> p.isWorkToday);
    }

    private boolean isSprintEnd() {

        return NUMBER_OF_SPRINT_DAY >= COUNT_OF_SPRINT_DAYS;
    }
}