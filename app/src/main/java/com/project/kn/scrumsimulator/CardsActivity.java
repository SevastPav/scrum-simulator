package com.project.kn.scrumsimulator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.project.kn.scrumsimulator.boardview.BoardAdapter;
import com.project.kn.scrumsimulator.boardview.BoardView;
import com.project.kn.scrumsimulator.boardview.SimpleBoardAdapter;
import com.project.kn.scrumsimulator.boardview.Task;
import com.project.kn.scrumsimulator.events.Card;
import com.project.kn.scrumsimulator.events.CardType;
import com.project.kn.scrumsimulator.events.Event;
import com.project.kn.scrumsimulator.events.Problem;
import com.project.kn.scrumsimulator.events.Solution;
import com.project.kn.scrumsimulator.sprint.SprintUtils;

import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity {

    public static Button mainButton;

    public static int PROBLEM_POS = -1;
    public static int PROBLEM_I = -1;
    public static int SOLUTION_POS = -1;
    public static int SOLUTION_I = -1;

    public static Button solveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        solveButton = findViewById(R.id.button_to_solve);
        mainButton = findViewById(R.id.button_to_board);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SprintUtils.dropCardsWithType(CardType.EVENT);
                finish();
            }
        });

        final BoardView boardView = (BoardView)findViewById(R.id.boardView);
        boardView.SetColumnSnap(false);
        boardView.SetColumnSnap(false);

        Bundle arguments = getIntent().getExtras();
        ArrayList<Card> cards = (ArrayList<Card>) arguments.get("cards");

        final SimpleBoardAdapter boardAdapter = new SimpleBoardAdapter(this, createData(cards));
        boardView.setAdapter(boardAdapter);
        boardView.setOnDoneListener(new BoardView.DoneListener() {
            @Override
            public void onDone() {
                Log.e("scroll","done");
            }
        });

        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Problem problem = (Problem) boardAdapter.columns.get(PROBLEM_POS).objects.get(PROBLEM_I);
                Solution solution = (Solution) boardAdapter.columns.get(SOLUTION_POS).objects.get(SOLUTION_I);

                if (problem.getGroup() == solution.getGroup()) {
                    openSiteDialogWithMsg(v, true);
                    boardAdapter.removeItem(PROBLEM_POS, PROBLEM_I);
                    boardAdapter.removeItem(SOLUTION_POS, SOLUTION_I);
                    PROBLEM_POS = -1;
                    PROBLEM_I = -1;
                    SOLUTION_POS = -1;
                    SOLUTION_I = -1;
                    for(int i =0; i<MainActivity.getBoardView().boardAdapter.columns.size(); i++) {
                        BoardAdapter.Column column = MainActivity.getBoardView().boardAdapter.columns.get(i);
                        for (int j=0; j<column.objects.size(); j++) {
                            Task task = (Task) column.objects.get(j);
                            if (task.id == problem.getTaskId()) {
                                task.removeProblem(problem);
                                if (!task.isBlocked()) {
                                    View view = column.views.get(j);
                                    view.findViewById(R.id.cardImageProblem).setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    }
                    SprintUtils.dropProblemCard(problem);
                } else {
                    openSiteDialogWithMsg(v, false);
                }
            }
        });
    }

    private ArrayList<SimpleBoardAdapter.SimpleColumn> createData(ArrayList<Card> cards) {

        ArrayList<Problem> problemList = new ArrayList<>();
        ArrayList<Solution> solutionList = new ArrayList<>();
        ArrayList<Event> eventList = new ArrayList<>();

        for (Card card:cards) {
            if (card instanceof Problem) {
                problemList.add((Problem) card);
            }
            if (card instanceof Solution) {
                solutionList.add((Solution) card);
            }
            if (card instanceof Event) {
                eventList.add((Event) card);
            }
        }

        final ArrayList<SimpleBoardAdapter.SimpleColumn> data = new ArrayList<>();
        data.add(new SimpleBoardAdapter.SimpleColumn("Problem",(ArrayList)problemList));
        data.add(new SimpleBoardAdapter.SimpleColumn("Solution",(ArrayList)solutionList));
        data.add(new SimpleBoardAdapter.SimpleColumn("Event",(ArrayList)eventList));

        return data;
    }

    private static void openSiteDialogWithMsg(View v, boolean isSolutionCorrect) {

        String msg = "Вы выбрали верное решение для проблемы!";
        if (!isSolutionCorrect) {
            msg = "Вы выбрали неверное решение для проблемы";
        }
        final AlertDialog aboutDialog = new AlertDialog.Builder(v.getContext())
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();

        aboutDialog.show();
    }

    public static boolean isSolveButtonActive() {

        return PROBLEM_POS >= 0 && PROBLEM_I >= 0 && SOLUTION_POS >= 0 && SOLUTION_I >= 0;
    }
}