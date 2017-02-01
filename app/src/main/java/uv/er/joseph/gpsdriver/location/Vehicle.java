package uv.er.joseph.gpsdriver.location;

import com.google.gson.annotations.SerializedName;

public class Vehicle {

    /*@SerializedName("id")
    int id;*/

    @SerializedName("latitude")
    String latitude;

    @SerializedName("longitude")
    String longitude;

    @SerializedName("vehicleno")
    Integer vehicleno;

    @SerializedName("dest")
    String dest;

    @SerializedName("nextstop")
    String nextStop;

    @SerializedName("late")
    String late;

    @SerializedName("uncertainty")
    int uncertainty;

    @SerializedName("stopsequence")
    int stopsequence;

    public Vehicle(int vehicleno, String latitude, String longitude, String dest, String nextstop, String late, int uncertainty, int stopsequence) {
        //this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.vehicleno = vehicleno;
        this.dest = dest;
        this.nextStop = nextstop;
        this.late = late;
        this.uncertainty = uncertainty;
        this.stopsequence = stopsequence;
    }
}