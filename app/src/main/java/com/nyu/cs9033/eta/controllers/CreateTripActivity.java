package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
    private static final int PICK_CONTACT = 1;
    private static final int PICK_LOCATION = 2;
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
    private EditText mLocation;
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
        mLocation = (EditText)findViewById(R.id.locationName);
        mAddPeopleButton = (Button) findViewById(R.id.addFriendButton);

        mAddPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String name = mPersonName.getText().toString();
//                String phone = mPhone.getText().toString();
//                String curloc = mCurrentLocation.getText().toString();
//                Log.v(TAG, name);
//                Log.v(TAG, phone);
//                Log.v(TAG, curloc);
//                mFriends.add(new Person(name, phone, curloc));
                mAddPeopleButtonPressed();
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
        Toast t = Toast.makeText(this, "All Details of this trip have been discarded", Toast.LENGTH_LONG);
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

    private void mAddPeopleButtonPressed() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                String personName = retrieveContactName(uri);
                mPersonName.setText(personName);
                String phone = retrieveContactPhone(uri);
                mPhone.setText(phone);
                mFriends.add(new Person(personName, phone, null));
            }

        }
    }


    private String retrieveContactName(Uri uri) {
        Log.v("name", "Inside getContactName");
        String contactName = null;
        Log.v("name","Inside getContactName"+" "+contactName);
        Cursor cursor = getContentResolver().query(uri,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                null, null, null);
        if(cursor.moveToFirst()){
            contactName = cursor.getString(0);
            Log.v("name","Inside moveTofist"+" "+contactName);
        }
        Log.v("name","before cursor.close()"+" "+contactName);
        cursor.close();

        Log.d("name", "Retrieved Contact Info"+contactName);
        Log.d("name", "Retrieved Contact Info" + contactName);

        return contactName;
    }
    private String retrieveContactPhone(Uri uri) {

        String contactNumber = null;
        String contactID=null;
        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uri,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);
        return contactNumber;
    }
    public void SearchLocation(View view){
        Uri location = Uri.parse("location://com.example.nyu.hw3api");
        Intent locationintent = new Intent(Intent.ACTION_VIEW,location);
        //locationintent.putExtra("searchVal", mLocation.getText().toString());//search string eg: new York:: Chinese works fine
        String convertedString = parseSearchString(mLocation.getText().toString());
        locationintent.putExtra("searchVal",convertedString);
        startActivityForResult(locationintent, PICK_LOCATION);
     }

    private String parseSearchString(String searchString){
        String[] whiteList = new String [] {",","at","near","in"};
        for(String word:whiteList){
            searchString = searchString.replaceAll("(?i)\\b"+word+"\\b", "+");
        }
        StringBuilder finalLoc = new StringBuilder();
        String[] seperated = searchString.split("\\s+");

        for(int i = 1; i<seperated.length;i++){
            finalLoc.append(seperated[i]+",");
        }
        if(finalLoc.length()>0)
        {
            if(finalLoc.charAt(finalLoc.length()-1) == ',')
                finalLoc.deleteCharAt(finalLoc.length()-1);
        }else{
            seperated = seperated[0].split(",");
            for(int i = 1; i<seperated.length;i++){
                finalLoc.append(seperated[i]+",");
            }
            if(finalLoc.length()>0 && finalLoc.charAt(finalLoc.length()-1)==',')
                finalLoc.deleteCharAt(finalLoc.length()-1);
        }
        finalLoc = finalLoc.length()>0? finalLoc : finalLoc.append("NY");
        return finalLoc.toString()+"::"+seperated[0];
    }

}