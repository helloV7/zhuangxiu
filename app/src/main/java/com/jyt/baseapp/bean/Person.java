package com.jyt.baseapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by v7 on 2017/11/4.
 */

public class Person implements Parcelable {
    private String id;
    private String name;

    public Person() {
    }

    protected Person(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
