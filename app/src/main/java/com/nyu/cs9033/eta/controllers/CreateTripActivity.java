package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateTripActivity extends Activity {

    private static final String TAG = "CreateTripActivity";
    private EditText mTripTitle;
    private ArrayList<Person> mFriends;
    private EditText mPhone;
    private EditText mPersonName;
    private EditText mCurrentLocation;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    //private DatePicker mPickDate;
    private String mTime;
    private String mDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_trip);
        Button mAddPeopleButton;
        Button mCreateTripButton;
        Button mResetButton;


        mFriends = new ArrayList<Person>();

        mTripTitle = (EditText) findViewById(R.id.tripTitle);
        mDatePicker = (DatePicker) findViewById(R.id.tripDate);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        mCurrentLocation = (EditText) findViewById(R.id.currentLocation);
        mPhone = (EditText) findViewById(R.id.phone);
        mPersonName = (EditText) findViewById(R.id.personName);
        mAddPeopleButton = (Button) findViewById(R.id.addFriendButton);

        mAddPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mPersonName.getText().toString();
                String phone = mPhone.getText().toString();
                String curloc = mCurrentLocation.getText().toString();
                Log.v(TAG, name);
                Log.v(TAG, phone);
                Log.v(TAG, curloc);
                mFriends.add(new Person(name, phone, curloc));
                mPersonName.setText("");
                mPhone.setText("");
                mCurrentLocation.setText("");
            }
        });
        // TODO - fill in here
        mCreateTripButton = (Button) findViewById(R.id.createTripButton);
        mCreateTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTrip();
            }
        });
        mResetButton = (Button) findViewById(R.id.resetTrip);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelTrip();
            }
        });
        mTimePicker.setIs24HourView(true);
    }

    /**
     * This method should be used to
     * instantiate a Trip model object.
     *
     * @return The Trip as represented
     * by the View.
     */
    public Trip createTrip() {

        // TODO - fill in here

        mTime = mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute();
        mDate = mDatePicker.getDayOfMonth() + ":" + mDatePicker.getMonth() + ":" + mDatePicker.getYear();
        String location = mTripTitle.getText().toString();
        Trip trip = new Trip(location, mDate, mTime, mFriends);
        Log.v(TAG, trip.getTripTitle());
        trip.addPeople(mFriends.toArray(new Person[mFriends.size()]));

        saveTrip(trip);

        return null;
    }

    /**
     * For HW2 you should treat this method as a
     * way of sending the Trip data back to the
     * main Activity.
     * <p/>
     * Note: If you call finish() here the Activity
     * will end and pass an Intent back to the
     * previous Activity using setResult().
     *
     * @return whether the Trip was successfully
     * saved.
     */
    public boolean saveTrip(Trip trip) {

        // TODO - fill in here
        if (trip != null) {
            Intent mainActivityIntent = new Intent();
            mainActivityIntent.putExtra("trip", trip);
            setResult(Activity.RESULT_OK, mainActivityIntent);
            finish();
        }
        return false;
    }

    /**
     * This method should be used when a
     * user wants to cancel the creation of
     * a Trip.
     * <p/>
     * Note: You most likely want to call this
     * if your activity dies during the process
     * of a trip creation or if a cancel/back
     * button event occurs. Should return to
     * the previous activity without a result
     * using finish() and setResult().
     */
    public void cancelTrip() {
        // TODO - fill in here
        //setResult(Activity.RESULT_CANCELED);
        mPersonName.setText("");
        mPhone.setText("");
        mFriends.clear();
        mCurrentLocation.setText("");
        mTripTitle.setText("");
        Toast t=Toast.makeText(this,"All Details of this trip have been discarded",Toast.LENGTH_LONG);
        t.show();
    }

//    // display current date
//    public void setCurrentDateOnView() {
//
//        mPickDate = (DatePicker) findViewById(R.id.tripDate);
//
//        final Calendar c = Calendar.getInstance();
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DAY_OF_MONTH);
//
//
//        // set current date into datepicker
//        dpResult.init(year, month, day, null);
//
//    }


}
