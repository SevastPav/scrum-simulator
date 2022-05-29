package com.project.kn.scrumsimulator.boardview;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.kn.scrumsimulator.R;

import java.util.ArrayList;

public class SimpleBoardAdapter extends BoardAdapter{
    int header_resource;
    int item_resource;
    public SimpleBoardAdapter instance;

    public SimpleBoardAdapter(Context context, ArrayList<SimpleColumn> data) {
        super(context);
        instance = this;
        this.columns = (ArrayList)data;
        this.header_resource = R.layout.column_header;
        this.item_resource = R.layout.column_item;
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
        View itemView = View.inflate(context, item_resource, null);
        Item item = (Item) columns.get(column_position).objects.get(item_position);
        TextView nameView = (TextView) itemView.findViewById(R.id.card_content);
        nameView.setText(item.name);
        TextView taskHoursView = (TextView) itemView.findViewById(R.id.task_hours);
        taskHoursView.setText(String.valueOf(item.hoursCount));
        TextView taskPriorityView = (TextView) itemView.findViewById(R.id.task_priority);
        taskPriorityView.setText(String.valueOf(item.priority));
        return itemView;
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
            this.header_object = new Item(title);
            this.objects = items;
        }
    }

}
