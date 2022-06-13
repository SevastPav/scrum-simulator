package com.project.kn.scrumsimulator.sprint;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import com.project.kn.scrumsimulator.CardsActivity;
import com.project.kn.scrumsimulator.MainActivity;
import com.project.kn.scrumsimulator.R;
import com.project.kn.scrumsimulator.StartPage;
import com.project.kn.scrumsimulator.events.Card;
import com.project.kn.scrumsimulator.events.CardType;
import com.project.kn.scrumsimulator.events.Problem;
import com.project.kn.scrumsimulator.events.Solution;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SprintUtils {

    public static int NUMBER_OF_SPRINT_DAY = 1;
    public static int COUNT_OF_SPRINT_DAYS = 7;
    public static int NUMBER_OF_SPRINT = 1;
    public static int COUNT_OF_SPRINTS = 3;
    public static int COUNT_OF_HOURS_IN_SPRINT = 0;
    public static int COUNT_OF_HOURS_IN_ALL_SPRINTS = 0;
    public static int COUNT_OF_FINISHED_TASKS = 0;

    public static ProgressBar progressBarDay, progressBarSprint;

    private static ArrayList<Card> cards = new ArrayList<>();

    public static void incCountOfFinishedTasks() {
        COUNT_OF_FINISHED_TASKS += 1;
    }

    public static void addCard(Card card) {

        cards.add(card);
    }

    public static void dropCards() {

        cards.clear();
    }

    public static void dropProblemCard(Problem card) {

        cards.removeIf(p -> p instanceof Problem && (((Problem) p).getId()).equals(card.getId()));
    }

    public static void dropSolutionCard(Solution card) {

        cards.removeIf(p -> p instanceof Solution && (((Solution) p).getId()).equals(card.getId()));
    }

    public static void dropCardsWithType(CardType type) {

        cards = cards.stream().filter(card -> !card.getCardType().equals(type)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Card> getCards() {

        return cards;
    }

    public static void allPlayersWorked(View v) {

        Log.e("day", "day is end");
        incSprintDay();
        if (isSprintEnd()){
            String msg = "Спринт закончен. Общее количество продуктивных часов = " + COUNT_OF_HOURS_IN_SPRINT;
            Log.e("sprint", msg);
            incSprintNumber();
            if (isSprintsEnd()){
                msg = "Поздравляем, все спринты завершены! Общее количество продуктивных часов = " + COUNT_OF_HOURS_IN_ALL_SPRINTS + "; Количество решенных задач = " + COUNT_OF_FINISHED_TASKS;
                Log.e("sprint", msg);
                openSiteDialogWithMsg(v, msg, true);
            } else {
                Log.e("sprint", msg);
                openSiteDialogWithMsg(v, msg, false);
            }
        }
    }

    public static void incCountOfHoursInDay(int hours) {
        COUNT_OF_HOURS_IN_SPRINT += hours;
    }

    public static void incSprintDay() {

        NUMBER_OF_SPRINT_DAY++;
    }

    public static boolean isSprintEnd() {

        return NUMBER_OF_SPRINT_DAY > COUNT_OF_SPRINT_DAYS;
    }

    public static void incSprintNumber() {

        NUMBER_OF_SPRINT_DAY = 1;
        NUMBER_OF_SPRINT++;
        COUNT_OF_HOURS_IN_ALL_SPRINTS += COUNT_OF_HOURS_IN_SPRINT;
        COUNT_OF_HOURS_IN_SPRINT = 0;
    }

    public static boolean isSprintsEnd() {

        return NUMBER_OF_SPRINT > COUNT_OF_SPRINTS;
    }

    private static void newGame() {
        NUMBER_OF_SPRINT_DAY = 1;
        NUMBER_OF_SPRINT = 1;
        COUNT_OF_HOURS_IN_SPRINT = 0;
        COUNT_OF_HOURS_IN_ALL_SPRINTS = 0;
    }

    private static void openSiteDialogWithMsg(View v, String msg, boolean isSprintsEnded) {

        final AlertDialog aboutDialog = new AlertDialog.Builder(v.getContext())
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!isSprintsEnded) {
                            return;
                        }
                        newGame();
                        Intent intent = new Intent(v.getContext(), StartPage.class);
                        startActivity(v.getContext(), intent, null);
                    }
                }).create();

        aboutDialog.show();
    }

    public static void setProgressBars(MainActivity mainActivity) {

        progressBarDay = (ProgressBar)mainActivity.findViewById(R.id.progressBarDay);
        progressBarSprint = (ProgressBar)mainActivity.findViewById(R.id.progressBarSprint);
        progressBarDay.setMax(COUNT_OF_SPRINT_DAYS);
        progressBarSprint.setMax(COUNT_OF_SPRINTS);
    }

    public static void updateProgressBars() {

        progressBarDay.setProgress(NUMBER_OF_SPRINT_DAY);
        progressBarSprint.setProgress(NUMBER_OF_SPRINT);
    }
}
