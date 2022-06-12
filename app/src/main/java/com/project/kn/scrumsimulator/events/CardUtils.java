package com.project.kn.scrumsimulator.events;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.project.kn.scrumsimulator.MainActivity;
import com.project.kn.scrumsimulator.R;
import com.project.kn.scrumsimulator.boardview.Item;
import com.project.kn.scrumsimulator.sprint.SprintUtils;
import com.project.kn.scrumsimulator.utils.RandomUtils;

import java.util.ArrayList;

public class CardUtils {

    static int card_resource = R.layout.column_item;

    public static void show(Context context) {

        Dialog dlg = new Dialog(context);
//        dlg.setContentView(R.layout.dialog_layout);
        dlg.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        View cardView = View.inflate(context, card_resource, null);
        TextView nameView = (TextView) cardView.findViewById(R.id.card_content);
        nameView.setText("test");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                (LinearLayout.LayoutParams.WRAP_CONTENT), (LinearLayout.LayoutParams.WRAP_CONTENT));

        dlg.addContentView(cardView, lp);

        dlg.show();
        dlg.dismiss();

//        RelativeLayout relative = new RelativeLayout(context);
//        relative.setLayoutParams(lp);
//
//        TextView tv = new TextView(context);
//        tv.setLayoutParams(lp);
//
//        EditText edittv = new EditText(context);
//        edittv.setLayoutParams(lp);
//
//        relative.addView(tv);
//        relative.addView(edittv);
    }

    public void showCards(Context context) {

        ArrayList<View> eventViews = new ArrayList<>();
        ArrayList<View> solutionViews = new ArrayList<>();
        ArrayList<View> problemViews = new ArrayList<>();
        ArrayList<Card> cards = SprintUtils.getCards();
        for (Card card:cards) {
            View cardView = View.inflate(context, card_resource, null);
            TextView nameView = (TextView) cardView.findViewById(R.id.card_content);
            nameView.setText(card.getName());
            switch (card.getCardType()) {
                case EVENT:
                    eventViews.add(cardView);
                    break;
                case PROBLEM:
                    problemViews.add(cardView);
                    break;
                case SOLUTION:
                    solutionViews.add(cardView);
                    break;
            }
        }
    }

    public static void generateCard(View v) {

        ArrayList<Card> cards = MainActivity.getCards();
        int i = RandomUtils.getRandomIntBetween(0, cards.size() - 1);
        Card card = cards.get(i);
        SprintUtils.addCard(card);

        openSiteDialogWithMsg(v, card);
    }

    private static void openSiteDialogWithMsg(View v, Card card) {

        String msg = String.format("New card with type %s! Name = %s, Description = %s",
                card.getCardType().name(),
                card.getName(),
                card.getDescription());

        final AlertDialog aboutDialog = new AlertDialog.Builder(v.getContext())
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();

        aboutDialog.show();
    }
}
