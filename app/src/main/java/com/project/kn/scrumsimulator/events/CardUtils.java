package com.project.kn.scrumsimulator.events;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.kn.scrumsimulator.CardsActivity;
import com.project.kn.scrumsimulator.MainActivity;
import com.project.kn.scrumsimulator.R;
import com.project.kn.scrumsimulator.sprint.SprintUtils;
import com.project.kn.scrumsimulator.utils.RandomUtils;

import java.util.ArrayList;

public class CardUtils {

    public static void generateCard(View v) {

        ArrayList<Card> cards = MainActivity.getCards();
        int i = RandomUtils.getRandomIntBetween(0, cards.size() - 1);
        Card card = cards.get(i);
        SprintUtils.addCard(card);

        openSiteDialogWithMsg(v, card);
    }

    private static void openSiteDialogWithMsg(View v, Card card) {

        Dialog dlg = new Dialog(v.getContext());
        dlg.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int resourceId = 0;
        if (card instanceof Problem) {
            resourceId = R.layout.problem_card;
        }
        if (card instanceof Solution) {
            resourceId = R.layout.solution_card;
        }
        if (card instanceof Event) {
            resourceId = (((Event) card).isPositive()) ? R.layout.event_good_card : R.layout.event_bad_card;
        }

        View cardView = View.inflate(v.getContext(), resourceId, null);
        TextView nameView = (TextView) cardView.findViewById(R.id.card_content);
        nameView.setText(card.getName());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                (LinearLayout.LayoutParams.WRAP_CONTENT), (LinearLayout.LayoutParams.WRAP_CONTENT));

        dlg.addContentView(cardView, lp);

        dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dlg) {

                dlg.dismiss();
                if (MainActivity.isAllPlayersWorked()) {
                    MainActivity.getPlayers().forEach(p -> p.isWorkToday = false);
                    Intent intent = new Intent(v.getContext(), CardsActivity.class);
                    intent.putExtra("cards", SprintUtils.getCards());
                    v.getContext().startActivity(intent, null);
                }
            }
        });

        dlg.show();
    }
}
