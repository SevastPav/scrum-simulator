package com.project.kn.scrumsimulator.sprint;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

public class SprintUtils {

    public static int NUMBER_OF_SPRINT_DAY = 0;
    public static int COUNT_OF_SPRINT_DAYS = 3;
    public static int NUMBER_OF_SPRINT = 0;
    public static int COUNT_OF_SPRINTS = 3;
    public static int COUNT_OF_HOURS_IN_SPRINT = 0;
    public static int COUNT_OF_HOURS_IN_ALL_SPRINTS = 0;

    public static void allPlayersWorked(View v) {

        Log.e("day", "day is end");
        incSprintDay();
        if (isSprintEnd()){
            String msg = "sprint end. Count of hours = " + COUNT_OF_HOURS_IN_SPRINT;
            Log.e("sprint", msg);
            openSiteDialogWithMsg(v, msg);
            incSprintNumber();
            if (isSprintsEnd()){
                msg = "all sprints end. Count of hours = " + COUNT_OF_HOURS_IN_ALL_SPRINTS;
                Log.e("sprint", msg);
                openSiteDialogWithMsg(v, msg);
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

        return NUMBER_OF_SPRINT_DAY >= COUNT_OF_SPRINT_DAYS;
    }

    public static void incSprintNumber() {

        NUMBER_OF_SPRINT_DAY = 0;
        NUMBER_OF_SPRINT++;
        COUNT_OF_HOURS_IN_ALL_SPRINTS += COUNT_OF_HOURS_IN_SPRINT;
        COUNT_OF_HOURS_IN_SPRINT = 0;
    }

    public static boolean isSprintsEnd() {

        return NUMBER_OF_SPRINT >= COUNT_OF_SPRINTS;
    }

    private static void openSiteDialogWithMsg(View v, String msg) {

        final AlertDialog aboutDialog = new AlertDialog.Builder(v.getContext())
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).create();

        aboutDialog.show();
    }
}
