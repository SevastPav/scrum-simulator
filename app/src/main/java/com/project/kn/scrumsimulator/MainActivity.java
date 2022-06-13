package com.project.kn.scrumsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.kn.scrumsimulator.boardview.BoardView;
import com.project.kn.scrumsimulator.boardview.Task;
import com.project.kn.scrumsimulator.boardview.SimpleBoardAdapter;
import com.project.kn.scrumsimulator.config.DatabaseConfig;
import com.project.kn.scrumsimulator.db.EventRepository;
import com.project.kn.scrumsimulator.db.ProblemRepository;
import com.project.kn.scrumsimulator.db.SolutionRepository;
import com.project.kn.scrumsimulator.db.TaskRepository;
import com.project.kn.scrumsimulator.entity.PlayerEntity;
import com.project.kn.scrumsimulator.entity.TaskEntity;
import com.project.kn.scrumsimulator.events.Card;
import com.project.kn.scrumsimulator.events.CardUtils;
import com.project.kn.scrumsimulator.events.Event;
import com.project.kn.scrumsimulator.events.Problem;
import com.project.kn.scrumsimulator.events.Solution;
import com.project.kn.scrumsimulator.sprint.Player;
import com.project.kn.scrumsimulator.sprint.SprintUtils;
import com.project.kn.scrumsimulator.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static PlayerEntity loginedPlayer;
    private static Player currentPlayer;
    private static ArrayList<Player> players;

    private static ArrayList<Card> backloglist;
    private static ArrayList<Card> cards;

    public static int TASK_POS;
    public static int TASK_I;

    public static Button workButton;
    public static BoardView boardView;
    private static TextView currentPlayerView;

    private final TaskRepository taskRepository;
    private final ProblemRepository problemRepository;
    private final SolutionRepository solutionRepository;
    private final EventRepository eventRepository;

    public MainActivity() {
        DatabaseConfig dbConfig = new DatabaseConfig();
        taskRepository = new TaskRepository(dbConfig);
        problemRepository = new ProblemRepository(dbConfig);
        solutionRepository = new SolutionRepository(dbConfig);
        eventRepository = new EventRepository(dbConfig);
    }

    private void initCards(int projectId) {

        backloglist = new ArrayList<>();
        cards = new ArrayList<>();
        try {
            CompletableFuture.supplyAsync(() -> taskRepository.findAllByProjectId(projectId)).get().forEach(t -> backloglist.add(Task.fromEntity(t)));
            CompletableFuture.supplyAsync(() -> solutionRepository.findAllByProjectId(projectId)).get().forEach(s -> cards.add(Solution.fromEntity(s)));
            CompletableFuture.supplyAsync(() -> problemRepository.findAllByProjectId(projectId)).get().forEach(p -> cards.add(Problem.fromEntity(p)));
            CompletableFuture.supplyAsync(() -> eventRepository.findAllByProjectId(projectId)).get().forEach(e -> cards.add(Event.fromEntity(e)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initColumns(ArrayList<SimpleBoardAdapter.SimpleColumn> data) {

        final ArrayList<Task> empty = new ArrayList<>();
        data.add(new SimpleBoardAdapter.SimpleColumn("Backlog",(ArrayList) backloglist));
        data.add(new SimpleBoardAdapter.SimpleColumn("TODO",(ArrayList)empty));
        data.add(new SimpleBoardAdapter.SimpleColumn("In progress",(ArrayList)empty));
        data.add(new SimpleBoardAdapter.SimpleColumn("Done",(ArrayList)empty));
    }

    private void initPlayers() {

        players = new ArrayList<>();
        players.add(new Player("Andrew"));
        players.add(new Player("Pavel"));
        players.add(new Player("Alex"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workButton = findViewById(R.id.button_to_work);
        currentPlayerView = findViewById(R.id.textViewCurrentPlayer);
        SprintUtils.setProgressBars(this);
        boardView = (BoardView)findViewById(R.id.boardView);
        boardView.SetColumnSnap(false);
        boardView.SetColumnSnap(true);

        final ArrayList<SimpleBoardAdapter.SimpleColumn> data = new ArrayList<>();
        Bundle arguments = getIntent().getExtras();
        int projectId = (int) arguments.get("projectId");
        initCards(projectId);
        initColumns(data);
        initPlayers();

        currentPlayer = players.get(0);
        currentPlayerView.setText(currentPlayer.name);

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
                Task item = (Task) boardAdapter.columns.get(TASK_POS).objects.get(TASK_I);
                int randomHours = RandomUtils.getRandomIntBetween(1, 8);
                int newHours = item.hoursCount - randomHours;
                item.hoursCount = Math.max(newHours, 0);
                @SuppressLint("DefaultLocale")
                String msg = String.format("Списано %d часов с карточки %d : %d", randomHours, TASK_POS, TASK_I);
                Log.e("work",msg);
                TextView taskHours = boardAdapter.columns.get(TASK_POS).views.get(TASK_I).findViewById(R.id.task_hours);
                taskHours.setText(String.valueOf(item.hoursCount));

                if (item.hoursCount == 0) {
                    workButton.setEnabled(false);
                }

                //Выводим информацию о событии и добавляем в спринт
                Card card = CardUtils.generateCard(v, item.id);
                if (card instanceof Problem) {
                    item.addProblem((Problem) card);
                    boardAdapter.columns.get(TASK_POS).views.get(TASK_I).findViewById(R.id.cardImageProblem).setVisibility(View.VISIBLE);
                }

                currentPlayer.isWorkToday = true;
                if (isAllPlayersWorked()) {
                    SprintUtils.incCountOfHoursInDay(randomHours);
                    SprintUtils.allPlayersWorked(v);
                }
                currentPlayer = getRandomUnworkedPlayer();
                currentPlayerView.setText(currentPlayer.name);
            }
        });
    }

    public static BoardView getBoardView() {
        return boardView;
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
}