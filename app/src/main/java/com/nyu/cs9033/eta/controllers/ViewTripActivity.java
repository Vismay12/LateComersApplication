package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ViewTripActivity extends Activity {

    private static final String TAG = "ViewTripActivity";
    private TextView tripLocation;
    private TextView tripTime;
    private TextView tripDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_trip);
        tripLocation = (TextView) findViewById(R.id.tripLocName);
        tripDate = (TextView) findViewById(R.id.tripDateView);
        tripTime = (TextView) findViewById(R.id.tripTimeView);
        // TODO - fill in here

        Trip trip = getTrip(getIntent());
        viewTrip(trip);
        Person[] people = trip.getPeople();
        PersonAdapterView personAdapter = new PersonAdapterView(this, people);
        ListView listView = (ListView) findViewById(R.id.friendsListView);
        listView.setAdapter(personAdapter);
    }

    /**
     * Create a Trip object via the recent trip that
     * was passed to TripViewer via an Intent.
     *
     * @param i The Intent that contains
     *          the most recent trip data.
     * @return The Trip that was most recently
     * passed to TripViewer, or null if there
     * is none.
     */
    public Trip getTrip(Intent i) {

        // TODO - fill in here
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            Trip trip = (Trip) bundle.get("trip");
            return trip;
        }
        return null;
    }

    /**
     * Populate the View using a Trip model.
     *
     * @param trip The Trip model used to
     *             populate the View.
     */
    public void viewTrip(Trip trip) {

        // TODO - fill in here
        Person[] invited_people = trip.getPeople();
        String location = trip.getLocation();
        String trip_date = trip.getDate();
        String trip_time = trip.getTime();
        this.tripDate.setText(trip_date);
        this.tripTime.setText(trip_time);
        this.tripLocation.setText(location);

    }
}
