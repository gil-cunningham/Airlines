package com.example.gilcunningham.airlines.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gil.cunningham on 9/19/2016.
 * Parcelable data for a airline
 * Provides Builder to construct Airline
 */

public class AirlineInfo implements Parcelable, Comparable<AirlineInfo> {

    private String mLogo = null;
    private String mName = null;
    private String mWebSite = null;
    private String mPhoneNum = null;
    private String mCode = null;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mLogo);
        parcel.writeString(mName);
        parcel.writeString(mWebSite);
        parcel.writeString(mPhoneNum);
        parcel.writeString(mCode);
    }

    public static final Creator<AirlineInfo> CREATOR = new Creator<AirlineInfo>() {

        public AirlineInfo createFromParcel(Parcel in) {
            return new AirlineInfo(in);
        }

        public AirlineInfo[] newArray(int size) {
            return new AirlineInfo[size];
        }
    };

    public AirlineInfo(Builder builder) {
        mLogo = builder.logo;
        mName = builder.name;
        mWebSite = builder.webSite;
        mPhoneNum = builder.phoneNum;
        mCode = builder.code;
    }

    public AirlineInfo(Parcel parcel) {
        mLogo = parcel.readString();
        mName = parcel.readString();
        mWebSite = parcel.readString();
        mPhoneNum = parcel.readString();
        mCode = parcel.readString();
    }

    public String getLogo() { return mLogo; }

    public String getName() {
        return mName;
    }

    public String getWebSite() {
        return mWebSite;
    }

    public String getPhoneNumber() { return mPhoneNum; }

    public String getCode() {
        return mCode;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public int compareTo(AirlineInfo airlineInfo) {
        return getName().compareToIgnoreCase(airlineInfo.getName());
    }

    public static final class Builder {

        private String logo = null;
        private String name = null;
        private String webSite = null;
        private String phoneNum = null;
        private String code = null;

        public AirlineInfo build() {
            return new AirlineInfo(this);
        }

        public Builder setLogo(String logo) {
            this.logo = logo;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setWebSite(String webSite) {
            this.webSite = webSite;
            return this;
        }

        public Builder setPhoneNumber(String phoneNum) {
            this.phoneNum = phoneNum;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }
    }
}


