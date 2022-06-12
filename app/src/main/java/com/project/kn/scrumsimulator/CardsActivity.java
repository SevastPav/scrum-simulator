package com.project.kn.scrumsimulator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.project.kn.scrumsimulator.boardview.BoardView;
import com.project.kn.scrumsimulator.boardview.SimpleBoardAdapter;
import com.project.kn.scrumsimulator.boardview.Task;
import com.project.kn.scrumsimulator.events.Card;
import com.project.kn.scrumsimulator.events.Event;
import com.project.kn.scrumsimulator.events.Problem;
import com.project.kn.scrumsimulator.events.Solution;

import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity {

    public static Button mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        mainButton = findViewById(R.id.button_to_board);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}