package com.example.studentapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomEventAdapter extends ArrayAdapter<Student> {
    private final Context context;
    private final ArrayList<Student> values;
    public CustomEventAdapter(@NonNull Context context, @NonNull ArrayList<Student> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowevent, parent, false);
        TextView eventName = rowView.findViewById(R.id.tvName);
        TextView eventPlaceName = rowView.findViewById(R.id.tvPlace);
        TextView eventDateTime = rowView.findViewById(R.id.tvDate);
        eventName.setText(values.get(position).name);
        eventDateTime.setText(values.get(position).datetime);
        eventPlaceName.setText(values.get(position).place);
        return rowView;
    }
}