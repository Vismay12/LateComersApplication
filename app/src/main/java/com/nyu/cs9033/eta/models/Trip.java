package com.nyu.cs9033.eta.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

public class Trip implements Parcelable {
	
	// Member fields should exist here, what else do you need for a trip?
	// Please add additional fields
    private String mTripTitle;
    private Person[] mFriends;
    private String mDate;
    private String mTime;
    private String mLocation;

	/**
	 * Parcelable creator. Do not modify this function.
	 */
	public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
		public Trip createFromParcel(Parcel p) {
			return new Trip(p);
		}

		public Trip[] newArray(int size) {
			return new Trip[size];
		}
	};
	
	/**
	 * Create a Trip model object from a Parcel. This
	 * function is called via the Parcelable creator.
	 * 
	 * @param p The Parcel used to populate the
	 * Model fields.
	 */
	public Trip(Parcel p) {
		
		// TODO - fill in here
        mTripTitle=p.readString();
//        mTripTitle = p.readString();
        mDate = p.readString();
        mTime = p.readString();
        int noOfFriends = p.readInt();
        mFriends = new Person[noOfFriends];

        p.readTypedArray(mFriends, Person.CREATOR);
	}



	public Trip(String tripTitle,  String date,String time, ArrayList<Person> friends) {
		mTripTitle=tripTitle;
        mDate=date;
        mTime=time;
        mFriends = new Person[1];
		// TODO - fill in here, please note you must have more arguments here
	}
    public final String getTripTitle(){
        return mTripTitle;
    }
	/**
	 * Serialize Trip object by using writeToParcel. 
	 * This function is automatically called by the
	 * system when the object is serialized.
	 * 
	 * @param dest Parcel object that gets written on 
	 * serialization. Use functions to write out the
	 * object stored via your member variables. 
	 * 
	 * @param flags Additional flags about how the object 
	 * should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE.
	 * In our case, you should be just passing 0.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTripTitle);
        dest.writeString(mDate);
        dest.writeString(mTime);
        dest.writeInt(mFriends.length);
        dest.writeTypedArray(mFriends, 0);
		// TODO - fill in here 
	}
	
	/**
	 * Feel free to add additional functions as necessary below.
	 */
	
	/**
	 * Do not implement
	 */
	@Override
	public int describeContents() {
		// Do not implement!
		return 0;
	}
    public final String getLocation(){
        return mTripTitle;
    }
    public final String getTime(){
        return mTime;
    }
    public final Person[] getPeople(){
        return mFriends;
    }
    public String getDate(){
        return mDate;
    }
    public void addPeople(Person[] p){
        if(mFriends.length == 1 && mFriends[0] == null){
            mFriends = p;
        }
        else
        {
            Person[] temp_copy = new Person[mFriends.length+p.length];
            System.arraycopy(mFriends, 0, temp_copy, 0, mFriends.length);
            System.arraycopy(p, 0, temp_copy, mFriends.length,p.length);
            mFriends = temp_copy;
        }
    }
}
