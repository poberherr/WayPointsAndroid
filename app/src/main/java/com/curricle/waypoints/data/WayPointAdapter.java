package com.curricle.waypoints.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.curricle.waypoints.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by conrad on 16/01/15.
 */
public class WayPointAdapter extends ArrayAdapter<WayPoint> {

    ArrayList<WayPoint> mWayPoints;
    Context context;

    LayoutInflater inflater;
    SimpleDateFormat format;

    public WayPointAdapter(Context context, ArrayList<WayPoint> data) {
        super(context, R.layout.waypoint_list_cell);
        this.mWayPoints = data;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    }

    @Override
    public int getCount() {
        if (mWayPoints != null) {
            return mWayPoints.size();
        } else
            return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View rowView = convertView;
        final WayPoint wayPoint = mWayPoints.get(position);
        if (rowView == null) {
            ViewHolder holder = new ViewHolder();
            rowView = inflater.inflate(R.layout.waypoint_list_cell, parent, false);
            holder.latLabel = (TextView) rowView.findViewById(R.id.latLabel);
            holder.longLabel = (TextView) rowView.findViewById(R.id.longLabel);
            holder.messageLabel = (TextView) rowView.findViewById(R.id.messageLabel);
            holder.dateLabel = (TextView) rowView.findViewById(R.id.dateLabel);
            rowView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.latLabel.setText("lat:" + wayPoint.latitude);
        holder.longLabel.setText("long:" + wayPoint.longitude);
        holder.messageLabel.setText(wayPoint.message);
        holder.dateLabel.setText(format.format(new Date(wayPoint.timestamp)));

        return rowView;
    }

    static class ViewHolder {
        private TextView messageLabel;
        private TextView latLabel;
        private TextView longLabel;
        private TextView dateLabel;
    }

}
