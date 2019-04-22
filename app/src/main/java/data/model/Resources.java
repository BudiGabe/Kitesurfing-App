package data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Resources implements Parcelable {

    public static final Creator<Resources> CREATOR = new Creator<Resources>() {
        @Override
        public Resources createFromParcel(Parcel in) {
            return new Resources(in);
        }

        @Override
        public Resources[] newArray(int size) {
            return new Resources[size];
        }
    };

    private String country;
    private String windProb;

    //Constructor
    public Resources(String country, String windProb){
        this.country = country;
        this.windProb = windProb;
    }

    public String getCountry() {
        return country;
    }

    public String getWindProb() {
        return windProb;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setWindProb(String windProb) {
        this.windProb = windProb;
    }

    //Parceling part
    public Resources(Parcel in) {
        country = in.readString();
        windProb = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeString(windProb);
    }
}
