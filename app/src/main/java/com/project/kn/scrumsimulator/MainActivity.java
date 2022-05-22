package com.project.kn.scrumsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.project.kn.scrumsimulator.boardview.BoardView;
import com.project.kn.scrumsimulator.boardview.Item;
import com.project.kn.scrumsimulator.boardview.SimpleBoardAdapter;
import com.project.kn.scrumsimulator.config.DatabaseConfig;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_old);
//        DatabaseConfig db = new DatabaseConfig();
//    }

    ArrayList<Item> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BoardView boardView = (BoardView)findViewById(R.id.boardView);
        boardView.SetColumnSnap(false);
        boardView.SetColumnSnap(true);
        final ArrayList<SimpleBoardAdapter.SimpleColumn> data = new ArrayList<>();
        list.add(new Item("I am a very long list that is not the same size as the others. I am a multiline"));
        list.add(new Item("Item 1"));
        final ArrayList<Item> empty = new ArrayList<>();
        final ArrayList<Item> test = new ArrayList<>();
        test.add(new Item("Item 1"));
        test.add(new Item("Item 1"));
        test.add(new Item("Item 1"));
        test.add(new Item("Item 1"));
        data.add(new SimpleBoardAdapter.SimpleColumn("Column 1",(ArrayList)list));
        data.add(new SimpleBoardAdapter.SimpleColumn("Column 2",(ArrayList)test));
        data.add(new SimpleBoardAdapter.SimpleColumn("Column 3",(ArrayList)empty));
        data.add(new SimpleBoardAdapter.SimpleColumn("Column 4",(ArrayList)empty));
        data.add(new SimpleBoardAdapter.SimpleColumn("Column 5",(ArrayList)empty));
        data.add(new SimpleBoardAdapter.SimpleColumn("Column 6",(ArrayList)empty));
        data.add(new SimpleBoardAdapter.SimpleColumn("Column 7",(ArrayList)empty));
        final SimpleBoardAdapter boardAdapter = new SimpleBoardAdapter(this,data);
        boardView.setAdapter(boardAdapter);
        boardView.setOnDoneListener(new BoardView.DoneListener() {
            @Override
            public void onDone() {
                Log.e("scroll","done");
            }
        });
    }
}