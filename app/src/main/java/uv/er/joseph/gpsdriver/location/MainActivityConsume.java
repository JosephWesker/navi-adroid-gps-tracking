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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uv.er.joseph.gpsdriver.R;

public class MainActivityConsume extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    /* Receiver s*/
    MyReceiver myReceiver;
    /* Receiver e*/

    private Activity activity;

    GoogleApiClient mGoogleApiClient;
    TextView
            mLatitudeText,
            mLongitudeText,
            //mIdText,
            mVehiclenoText,
            mDestText,
            mNextStopText,
            mLateText,
            mUncertaintyText,
            mStopSequenceText;
    EditText mTextMessage;

    /* Receiver s*/
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            /* Receiver data s */
            String latitude = arg1.getStringExtra("DATAPASSED");
            String longitude = arg1.getStringExtra("DATAPASSED2");
            /* Receiver data e */
            //int idText = Integer.parseInt(mIdText.getText().toString());
            Integer vehiclenoText = Integer.parseInt(mVehiclenoText.getText().toString());
            String destText = mDestText.getText().toString();
            String nextStopText = mNextStopText.getText().toString();
            String lateText = mLateText.getText().toString();
            int uncertaintyText =  Integer.parseInt(mUncertaintyText.getText().toString());
            int stopSequenceText =  Integer.parseInt(mStopSequenceText.getText().toString());

            mLatitudeText = (TextView) findViewById(R.id.textViewLatitude);
            mLongitudeText = (TextView) findViewById(R.id.textViewLongitude);
            /*mvVehiclenoText = (TextView) findViewById(R.id.textview3);
            mServiceText = (TextView) findViewById(R.id.textview4);*/

            mLatitudeText.setText(String.valueOf(latitude));
            mLongitudeText.setText(String.valueOf(longitude));

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://busappdata.herokuapp.com/v1.0/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final VehicleService service = retrofit.create(VehicleService.class);
            //final TextView textView = (TextView) findViewById(R.id.textView);

            Vehicle vehicle = new Vehicle(vehiclenoText, latitude, longitude, destText, nextStopText, lateText, uncertaintyText, stopSequenceText);
            Call<Vehicle> createCall = service.update(vehiclenoText, vehicle);

            createCall.enqueue(new Callback<Vehicle>() {
                @Override
                public void onResponse(Call<Vehicle> _, Response<Vehicle> resp) {
                    //Transport newTransport = resp.body();
                    //textView.setText("Created Transport with ISBN: " + newTransport.id); // by now maybe I can't show the id because it's created automatically on server, but if it is unique maybe I can return it.
                }

                @Override
                public void onFailure(Call<Vehicle> _, Throwable t) {
                    //t.printStackTrace();
                    //mTextMessage = (EditText) findViewById(R.id.textMessage);
                    //mTextMessage.setText(t.getMessage());
                }
            });

        }
    }
    /* Receiver e*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        /* Button settings s */
        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(arg0.getContext(), MainActivitySettings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                startActivity(intent);
            }
        });
        /* Button settings e */

        /* Put extra s */
        //mIdText = (TextView) findViewById(R.id.textViewId);
        mVehiclenoText = (TextView) findViewById(R.id.textViewVehicleno);
        mDestText = (TextView) findViewById(R.id.textViewDest);
        mNextStopText = (TextView) findViewById(R.id.textViewNextStop);
        mLateText = (TextView) findViewById(R.id.textViewLate);
        mUncertaintyText = (TextView) findViewById(R.id.textViewUncertainty);
        mStopSequenceText = (TextView) findViewById(R.id.textViewStopSequence);
        int
                //id,
                late,
                uncertainty,
                stopSequence;
        String
                vehicleno,
                dest,
                nextStop;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                //id = 0;
                vehicleno = null;
                dest = null;
                nextStop = null;
                late = 0;
                uncertainty = 0;
                stopSequence = 0;
            } else {
                //id = extras.getInt("id");
                vehicleno = extras.getString("vehicleno");
                dest = extras.getString("dest");
                nextStop = extras.getString("nextStop");
                late = extras.getInt("late");
                uncertainty = extras.getInt("uncertainty");
                stopSequence = extras.getInt("stopSequence");
            }
        } else {
            //id = (int) savedInstanceState.getSerializable("id");
            vehicleno = (String) savedInstanceState.getSerializable("vehicleno");
            dest = (String) savedInstanceState.getSerializable("dest");
            nextStop = (String) savedInstanceState.getSerializable("nextStop");
            late = (int) savedInstanceState.getSerializable("late");
            uncertainty = (int) savedInstanceState.getSerializable("uncertainty");
            stopSequence = (int) savedInstanceState.getSerializable("stopSequence");
        }
       // mIdText.setText(String.valueOf(id));
        mVehiclenoText.setText(String.valueOf(vehicleno));
        mDestText.setText(String.valueOf(dest));
        mNextStopText.setText(String.valueOf(nextStop));
        mLateText.setText(String.valueOf(late));
        mUncertaintyText.setText(String.valueOf(uncertainty));
        mStopSequenceText.setText(String.valueOf(stopSequence));
        /* Put extra e */

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
        Intent intent = new Intent(MainActivityConsume.this, BackgroundLocationService.class);
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