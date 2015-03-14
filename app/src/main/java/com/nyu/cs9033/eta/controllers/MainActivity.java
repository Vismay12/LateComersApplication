package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final int viewTripRequestCode = 1;
    private static final String TAG = "MainActivity";
    Button createTripButton;
    ArrayList<String> mTripTitles;
    private ArrayList<Trip> mTrips;
    ArrayAdapter<String> adapter = null;
    TextView viewTripButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTrips = new ArrayList<Trip>();

        createTripButton = (Button) findViewById(R.id.createTripButton);
        viewTripButton = (TextView) findViewById(R.id.ViewTripActivityButton);
        viewTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorMessage();
            }
        });
        mTripTitles = new ArrayList<String>();
        createTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateTripActivity();
            }
        });
        // TODO - fill in here
        ListView tripTitlesView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTripTitles);
        tripTitlesView.setAdapter(adapter);
        tripTitlesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startViewTripActivity(mTrips.get(i));
            }
        });
    }

    public void errorMessage() {
        Toast t = Toast.makeText(getApplicationContext(), "Create Activity and select from list below", Toast.LENGTH_LONG);
        t.show();

    }

    /**
     * This method should start the
     * Activity responsible for creating
     * a Trip.
     */
    public void startCreateTripActivity() {

        // TODO - fill in here
        Intent createTripIntent = new Intent(this, CreateTripActivity.class);
        startActivityForResult(createTripIntent, 1);
    }

    /**
     * This method should start the
     * Activity responsible for viewing
     * a Trip.
     */
    public void startViewTripActivity(Trip trip) {

        // TODO - fill in here
        if (trip != null) {
            Intent intent = new Intent(this, ViewTripActivity.class);
            intent.putExtra("trip", trip);
            startActivityForResult(intent, viewTripRequestCode);
        }
    }

    /**
     * Receive result from CreateTripActivity here.
     * Can be used to save instance of Trip object
     * which can be viewed in the ViewTripActivity.
     * <p/>
     * Note: This method will be called when a Trip
     * object is returned to the main activity.
     * Remember that the Trip will not be returned as
     * a Trip object; it will be in the persisted
     * Parcelable form. The actual Trip object should
     * be created and saved in a variable for future
     * use, i.e. to view the trip.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO - fill in here
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                Trip trip = bundle.getParcelable("trip");

                mTripTitles.add(trip.getTripTitle());
                mTrips.add(trip);
                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(this, "Trip Cancelled", Toast.LENGTH_LONG);
            }
        }
    }
}
