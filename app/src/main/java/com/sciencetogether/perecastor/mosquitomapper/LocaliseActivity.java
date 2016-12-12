package com.sciencetogether.perecastor.mosquitomapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocaliseActivity extends Activity {

    //widget attribut of the class
    TextView mlabelLatitude  = null;
    TextView mlabelLongitude = null;
    Button mButtonNext       = null;
    Button mButtonReturn     = null;

    //attribut to location
    double sLat = 0;
    double sLon = 0;

    //attribut to intent
    public final static String LATITUDE   = "Latitude";
    public final static String LONGITUDE  = "Longitude";

    //location Manager and Listener
    private LocationManager  locationManager   = null;
    private LocationListener locationListener  = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            //active in location changement
            if (location != null) {
                //we need to get a double of location and transform it
                double pLatitude = location.getLatitude();
                double pLongitude = location.getLongitude();

                sLat = pLatitude;
                sLon = pLongitude;

                //Location attribution
                String stringLat = Double.toString(sLat);
                String stringLon = Double.toString(sLon);
                mlabelLatitude.setText(stringLat);
                mlabelLongitude.setText(stringLon);

            }

            //error
            if (location == null){
                Toast.makeText(getBaseContext(),"Location is null", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    //button next, go to questionnaire activity with location information
    public View.OnClickListener btnNextClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            sLat = 0;  //because bug when no localisation
            sLon = 0;

            //Take the string in the Label
            if (mlabelLongitude != null && mlabelLatitude != null) {

                String stringLat = mlabelLatitude.getText().toString();
                sLat = Double.parseDouble(stringLat);
                String stringLon = mlabelLongitude.getText().toString();
                sLon = Double.parseDouble(stringLon);

            }

            Intent QuestionnaireActivity = new Intent(LocaliseActivity.this, QuestionnaireActivity.class);
            QuestionnaireActivity.putExtra(LONGITUDE, sLat);
            QuestionnaireActivity.putExtra(LATITUDE, sLon);
            startActivity(QuestionnaireActivity);

        }
    };

    //button return, only to a return to the last activity (main)
    public  View.OnClickListener btnReturnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(com.sciencetogether.perecastor.mosquitomapper.R.layout.activity_localise);

        //Button
        //set the attribut id
        mButtonNext     = (Button)  findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.loc_next_button_id);
        mButtonReturn   = (Button)  findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.loc_return_button_id);

        //button setClick
        mButtonNext.setOnClickListener(btnNextClick);
        mButtonReturn.setOnClickListener(btnReturnClick);

        //Text
        //set the attribut id
        mlabelLongitude = (TextView)findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.localise_longitude_label);
        mlabelLatitude  = (TextView)findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.localise_latitude_label);

        mlabelLatitude.setText("0");
        mlabelLongitude.setText("0");

        //Intent recuperation
        Intent i = getIntent();


        //Location
        //if it take a to long time to show, we will show the Toast
        //Toast.makeText(getBaseContext(), "We launch the Location Service", Toast.LENGTH_LONG).show();

        //Creation of a location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
                0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000,
                0, locationListener);
    }
}
