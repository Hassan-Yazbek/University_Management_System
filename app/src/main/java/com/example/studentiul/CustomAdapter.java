package com.example.studentiul;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    private Context context;
    private int currentPosition = -1; // Track current item position with yellow background
    private Handler handler;

    public CustomAdapter(Context context, List<String> data) {
        super(context, android.R.layout.simple_list_item_1, data);
        this.context = context;
        handler = new Handler();
        startPeriodicBackgroundChange();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set text color to blue
        holder.textView.setTextColor(Color.parseColor("#AA711E"));

        // Set yellow background if current position matches this position
        if (position == currentPosition) {
            holder.textView.setBackgroundColor(Color.parseColor("#0E2307"));
        } else {
            holder.textView.setBackgroundColor(Color.TRANSPARENT); // Reset background
        }

        holder.textView.setText(getItem(position));

        return convertView;
    }

    private void startPeriodicBackgroundChange() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Update current position to next item in the list
                currentPosition = (currentPosition + 1) % getCount();
                notifyDataSetChanged(); // Refresh ListView to apply background color changes

                handler.postDelayed(this, 1000); // Repeat every 3 seconds (3000 milliseconds)
            }
        }, 1000); // Initial delay of 3 seconds
    }

    private static class ViewHolder {
        TextView textView;
    }
}
