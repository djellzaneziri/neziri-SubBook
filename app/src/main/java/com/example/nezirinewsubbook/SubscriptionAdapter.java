package com.example.nezirinewsubbook;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SubscriptionAdapter extends ArrayAdapter<Subscription> {

    public SubscriptionAdapter(Activity context, ArrayList<Subscription> names) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.

        super(context, 0, names);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items_3, parent, false);
        }

        //Get the current position of the subscription in the listview and update accordingly
        Subscription currentSubscription = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.listName);

        nameTextView.setText(currentSubscription.getName());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.listDate);

        dateTextView.setText("Date: "+ currentSubscription.getDate());

        TextView chargeTextView = (TextView) listItemView.findViewById(R.id.listCharge);

        chargeTextView.setText("Charge: "+ currentSubscription.getCharge());

        TextView commentTextView = (TextView) listItemView.findViewById(R.id.listComment);

        commentTextView.setText("Comment: "+ currentSubscription.getComment());

        return listItemView;
    }
}

