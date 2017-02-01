package uv.er.joseph.gpsdriver.location;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uv.er.joseph.gpsdriver.R;


public class MainActivityShapeMaker extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    /* Receiver s*/
    MyReceiver myReceiver;
    /* Receiver e*/

    private Activity activity;

    GoogleApiClient mGoogleApiClient;
    TextView mLatitudeText, mLongitudeText, mIdText, mTrainnoText, mDestText, mNextStopText, mLateText;
    EditText mEditPeople;

    /* Receiver s*/
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            /* Receiver data s */
            String latitude = arg1.getStringExtra("DATAPASSED");
            String longitude = arg1.getStringExtra("DATAPASSED2");
            /* Receiver data e */


            mLatitudeText = (TextView) findViewById(R.id.txtLatitude);
            mLongitudeText = (TextView) findViewById(R.id.txtLongitude);


            mLatitudeText.setText(String.valueOf(latitude));
            mLongitudeText.setText(String.valueOf(longitude));



        }
    }
    /* Receiver e*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_maker);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }



        findViewById(R.id.btnShape).setOnClickListener(new View.OnClickListener() {
            int count_points = 0;

            @Override
            public void onClick(View arg0) {
                String latitude = mLatitudeText.getText().toString();
                String longitude = mLongitudeText.getText().toString();
                count_points = count_points + 1;
                appendLog("01_shp" + "," + latitude + "," + longitude + "," + count_points, Constants.LOCATION_FILE_SHAPE);
            }

            public void appendLog(String text, String filename) {
                File logFile = new File(filename);
                if (!logFile.exists()) {
                    try {
                        logFile.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                try {
                    //BufferedWriter for performance, true to set append to file flag
                    BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                    buf.append(text);
                    buf.newLine();
                    buf.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            int count_stops = 0;

            @Override
            public void onClick(View arg0) {
                String latitude = mLatitudeText.getText().toString();
                String longitude = mLongitudeText.getText().toString();
                count_stops = count_stops + 1;
                appendLog("PARADA_" + count_stops + "," + "," + "," + latitude + "," + longitude + "," + "PARADA_" + count_stops, Constants.LOCATION_FILE_STOP);
            }

            public void appendLog(String text, String filename) {
                File logFile = new File(filename);
                if (!logFile.exists()) {
                    try {
                        logFile.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                try {
                    //BufferedWriter for performance, true to set append to file flag
                    BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                    buf.append(text);
                    buf.newLine();
                    buf.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btnPeople).setOnClickListener(new View.OnClickListener() {
            int count_people = 0;

            @Override
            public void onClick(View arg0) {
                String latitude = mLatitudeText.getText().toString();
                String longitude = mLongitudeText.getText().toString();
                mEditPeople = (EditText) findViewById(R.id.editPeople);

                String count_people = mEditPeople.getText().toString();
                appendLog(count_people + "," + latitude + "," + longitude, Constants.LOCATION_FILE_PEOPLE);
            }

            public void appendLog(String text, String filename) {
                File logFile = new File(filename);
                if (!logFile.exists()) {
                    try {
                        logFile.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                try {
                    //BufferedWriter for performance, true to set append to file flag
                    BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                    buf.append(text);
                    buf.newLine();
                    buf.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }


    protected void onStart() {
        mGoogleApiClient.connect();
        //super.onStart();

        /* Receiver s*/
        // TODO Auto-generated method stub
        //Register BroadcastReceiver
        //to receive event from our service

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BackgroundLocationService.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);

        //Start our own service
        Intent intent = new Intent(MainActivityShapeMaker.this,
                BackgroundLocationService.class);
        startService(intent);
        /* Receiver e*/

        super.onStart();

    }

    protected void onStop() {
        mGoogleApiClient.disconnect();

        /* Receiver s*/
        // TODO Auto-generated method stub
        unregisterReceiver(myReceiver);
        /* Receiver e*/

        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            Context context = getApplicationContext();
            //Toast.makeText(context, "Latitud " + String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Context context = getApplicationContext();
        Toast.makeText(context, "Suspendido", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Context context = getApplicationContext();
        Toast.makeText(context, "Falló", Toast.LENGTH_LONG).show();
    }

    public void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        //PendingResult<LocationSettingsResult> result =
        LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
    }

    public void onAttach(Activity activity) {
        this.activity = activity;
    }

}