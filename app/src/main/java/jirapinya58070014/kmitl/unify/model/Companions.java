package jirapinya58070014.kmitl.unify.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Companions implements Parcelable {
    private String name;
    private String imagePath;
    private String id_User;
    private String id_Trip;
    private String status;
    private String id_Companions;

    public Companions() {
    }

    private Companions(Parcel in) {
        name = in.readString();
        imagePath = in.readString();
        id_User = in.readString();
        id_Trip = in.readString();
        status = in.readString();
        id_Companions = in.readString();
    }

    public static final Creator<Companions> CREATOR = new Creator<Companions>() {
        @Override
        public Companions createFromParcel(Parcel in) {
            return new Companions(in);
        }

        @Override
        public Companions[] newArray(int size) {
            return new Companions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getId_Trip() {
        return id_Trip;
    }

    public void setId_Trip(String id_Trip) {
        this.id_Trip = id_Trip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_Companions() {
        return id_Companions;
    }

    public void setId_Companions(String id_Companions) {
        this.id_Companions = id_Companions;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(imagePath);
        parcel.writeString(id_User);
        parcel.writeString(id_Trip);
        parcel.writeString(status);
        parcel.writeString(id_Companions);
    }
}