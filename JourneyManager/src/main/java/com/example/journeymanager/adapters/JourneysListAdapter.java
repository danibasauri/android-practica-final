package com.example.journeymanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.journeymanager.R;
import com.example.journeymanager.objects.Journey;

import java.util.HashMap;

public class JourneysListAdapter extends BaseAdapter {
    private HashMap<Integer, Journey> journeys = new HashMap<Integer, Journey>();
    private final Activity activity;
    private static LayoutInflater inflater = null;

    public JourneysListAdapter(Activity a, HashMap<Integer, Journey> d) {
        activity = a;
        journeys = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return journeys.size();
    }

    public Object getItem(int position) {
        return journeys.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.journey_row, null);
            holder = new ViewHolder();
            holder.location = (TextView) convertView.findViewById(R.id.location);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.location.setText(journeys.get(position).getLocation());
        holder.date.setText(journeys.get(position).getDate());
        return convertView;
    }


    public static class ViewHolder {
        TextView location;
        TextView date;
    }

}
