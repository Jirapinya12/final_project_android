package jirapinya58070014.kmitl.unify.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyTrip implements Parcelable {
    private String id_Trip;
    private String name;
    private String description;
    private String beginDate;
    private String endDate;
    private String time;
    private String location;
    private String coordinates;
    private String imagePath;
    private String longitude;
    private String latitude;
    private String id_UserOwner;
    private String address;

    public MyTrip() {
    }

    private MyTrip(Parcel in) {
        id_Trip = in.readString();
        name = in.readString();
        description = in.readString();
        beginDate = in.readString();
        endDate = in.readString();
        time = in.readString();
        location = in.readString();
        coordinates = in.readString();
        imagePath = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        id_UserOwner = in.readString();
        address = in.readString();
    }

    public static final Creator<MyTrip> CREATOR = new Creator<MyTrip>() {
        @Override
        public MyTrip createFromParcel(Parcel in) {
            return new MyTrip(in);
        }

        @Override
        public MyTrip[] newArray(int size) {
            return new MyTrip[size];
        }
    };

    public String getId_Trip() {
        return id_Trip;
    }

    public void setId_Trip(String id_Trip) {
        this.id_Trip = id_Trip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getId_UserOwner() {
        return id_UserOwner;
    }

    public void setId_UserOwner(String id_UserOwner) {
        this.id_UserOwner = id_UserOwner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_Trip);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(beginDate);
        parcel.writeString(endDate);
        parcel.writeString(time);
        parcel.writeString(location);
        parcel.writeString(coordinates);
        parcel.writeString(imagePath);
        parcel.writeString(longitude);
        parcel.writeString(latitude);
        parcel.writeString(id_UserOwner);
        parcel.writeString(address);
    }
}