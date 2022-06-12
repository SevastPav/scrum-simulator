package com.project.kn.scrumsimulator.boardview;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.kn.scrumsimulator.R;
import com.project.kn.scrumsimulator.events.Card;
import com.project.kn.scrumsimulator.events.Event;
import com.project.kn.scrumsimulator.events.Problem;
import com.project.kn.scrumsimulator.events.Solution;

import java.util.ArrayList;

public class SimpleBoardAdapter extends BoardAdapter{
    int header_resource;
    int task_resource;
    int problem_resource;
    int solution_resource;
    int positive_event_resource;
    int negative_event_resource;

    public SimpleBoardAdapter instance;

    public SimpleBoardAdapter(Context context, ArrayList<SimpleColumn> data) {
        super(context);
        instance = this;
        this.columns = (ArrayList)data;
        this.header_resource = R.layout.column_header;
        this.task_resource = R.layout.column_item;
        this.problem_resource = R.layout.problem_card;
        this.solution_resource = R.layout.solution_card;
        this.positive_event_resource = R.layout.event_good_card;
        this.negative_event_resource = R.layout.event_bad_card;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public int getItemCount(int column_position) {
        return columns.get(column_position).objects.size();
    }

    @Override
    public Object createHeaderObject(int column_position) {
        return columns.get(column_position).header_object;
    }

    @Override
    public Object createFooterObject(int column_position) {
        //return "Footer "+ String.valueOf(column_position);
        return "";
    }

    @Override
    public Object createItemObject(int column_position, int item_position) {
        return columns.get(column_position).objects.get(item_position);
    }

    @Override
    public boolean isColumnLocked(int column_position) {
        return true;
    }

    @Override
    public boolean isItemLocked(int column_position) {
        return false;
    }

//    @Override
//    public View createItemView(Context context,Object header_object,Object item_object,int column_position, int item_position) {
//        View item = View.inflate(context, item_resource, null);
//        TextView textView = (TextView)item.findViewById(R.id.card_content);
//        textView.setText(columns.get(column_position).objects.get(item_position).toString());
//        return item;
//    }

    @Override
    public View createItemView(Context context,Object header_object,Object item_object,int column_position, int item_position) {

        Card card = (Card) columns.get(column_position).objects.get(item_position);
        if (card instanceof Task) {
            View taskView = View.inflate(context, task_resource, null);
            Task task = (Task) card;
            TextView nameView = (TextView) taskView.findViewById(R.id.card_content);
            nameView.setText(task.getName());
            TextView taskHoursView = (TextView) taskView.findViewById(R.id.task_hours);
            taskHoursView.setText(String.valueOf(task.hoursCount));
            TextView taskPriorityView = (TextView) taskView.findViewById(R.id.task_priority);
            taskPriorityView.setText(String.valueOf(task.priority));
            return taskView;
        }
        if (card instanceof Problem) {
            View problemView = View.inflate(context, problem_resource, null);
            Problem problem = (Problem) card;
            TextView nameView = (TextView) problemView.findViewById(R.id.card_content);
            nameView.setText(problem.getName());
            Button assignButton = (Button) problemView.findViewById(R.id.button_to_assign_problem);
            assignButton.setVisibility(View.VISIBLE);
            return problemView;
        }
        if (card instanceof Solution) {
            View solutionView = View.inflate(context, solution_resource, null);
            Solution solution = (Solution) card;
            TextView nameView = (TextView) solutionView.findViewById(R.id.card_content);
            nameView.setText(solution.getName());
            Button assignButton = (Button) solutionView.findViewById(R.id.button_to_assign_solution);
            assignButton.setVisibility(View.VISIBLE);
            return solutionView;
        }
        if (card instanceof Event) {
            Event event = (Event) card;
            int resource = event.isPositive() ?positive_event_resource :negative_event_resource;
            View eventView = View.inflate(context, resource, null);
            TextView nameView = (TextView) eventView.findViewById(R.id.card_content);
            nameView.setText(event.getName());
            return eventView;
        }

        return null;
    }

    @Override
    public int maxItemCount(int column_position) {
        return 4;
    }

    @Override
    public void createColumns() {
        super.createColumns();
    }

    @Override
    public View createHeaderView(Context context,Object header_object,int column_position) {
        View column = View.inflate(context, header_resource, null);
        TextView textView = (TextView)column.findViewById(R.id.textView);
        textView.setText(columns.get(column_position).header_object.toString());
        return column;
    }

    @Override
    public View createFooterView(Context context, Object footer_object, int column_position) {
        View footer = View.inflate(context, header_resource, null);
        TextView textView = (TextView)footer.findViewById(R.id.textView);
        textView.setText((String)footer_object);
        return footer;
    }

    public static class SimpleColumn extends Column{
        public String title;
        public SimpleColumn(String title, ArrayList<Object> items){
            super();
            this.title = title;
            this.header_object = new Task(title);
            this.objects = items;
        }
    }

}
