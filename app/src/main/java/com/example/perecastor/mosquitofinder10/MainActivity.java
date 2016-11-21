package com.example.perecastor.mosquitofinder10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {


    //widget attribut of the class
    Button mLocalise = null;
    Button mIdentify = null;
    Button mInformation = null;

    //method to click on the locatebutton
    public View.OnClickListener btnLocaliseClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            //Start the new Intent to create the Localise Activity
            Intent localiseActivity = new Intent(MainActivity.this, LocaliseActivity.class);
            startActivity(localiseActivity);
        }
    };

    //method to click on identify button
    public View.OnClickListener btnIdentifyClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Start Identify activities
            Intent identifyActivity = new Intent(MainActivity.this, IdentifyWingsActivity.class);
            startActivity(identifyActivity);
        }
    };

    //method to clic on info button
    public View.OnClickListener btnInformationClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            //Start Info activity
            Intent InfoActivity = new Intent(MainActivity.this, InformationActivity.class);
            startActivity(InfoActivity);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocalise    = (Button) findViewById(R.id.localise_button);
        mIdentify    = (Button) findViewById(R.id.identify_button);
        mInformation = (Button) findViewById(R.id.information_button);

        mLocalise.setOnClickListener(btnLocaliseClick);
        mIdentify.setOnClickListener(btnIdentifyClick);
        mInformation.setOnClickListener(btnInformationClick);

    }
}