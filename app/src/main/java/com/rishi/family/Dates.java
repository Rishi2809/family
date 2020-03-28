package com.rishi.family;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;

public class Dates implements Parcelable, Serializable {
    public static final Creator<Dates> CREATOR = new Creator<Dates>() {
        @Override
        public Dates createFromParcel(Parcel in) {
            return new Dates(in);
        }

        @Override
        public Dates[] newArray(int size) {
            return new Dates[size];
        }
    };
       private static final long serialVersionUID = 87L;
    String id;
    String name;
    String occassion;
    Calendar dob;
    String remindme;
    String frequency;
    private String dootSBDay = "";

    public Dates() {

    }

    protected Dates(Parcel in) {
        id = in.readString();
        name = in.readString();
        occassion = in.readString();
        dootSBDay = in.readString();
        remindme = in.readString();
        frequency = in.readString();
        dob = (Calendar) in.readValue(getClass().getClassLoader());
    }

    public Calendar getDob() {
        return dob;
    }

    public void setDob(Calendar dob) {
        this.dob = dob;
    }

    public String getSBDay() {
        return dootSBDay;
    }

    public void setBDay(String dootSBDay) {
        this.dootSBDay = dootSBDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccassion() {
        return occassion;
    }

    public void setOccassion(String occassion) {
        this.occassion = occassion;
    }

    public String getRemindme() {
        return remindme;
    }

    public void setRemindme(String remindme) {
        this.remindme = remindme;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        //dest.writeString(dootImgUrl);
        dest.writeString(name);
        dest.writeString(dootSBDay);
        dest.writeString(occassion);
        dest.writeValue(dob);
        dest.writeValue(remindme);

    }
}
