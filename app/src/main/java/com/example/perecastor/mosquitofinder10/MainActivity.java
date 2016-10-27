package com.example.perecastor.mosquitofinder10;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends Activity {


    //widget attribut of the class
    Button mLocalise = null;
    Button mIdentify = null;
    Button mInformation = null;

    static boolean parseLauncher = true;
    static boolean firebaseLauncher = true;

    public static final String FIREBASE = "https://mosquitofinder.firebaseio.com/";
    private Firebase mFirebaseRef;

    //method to click on the locatebutton
    public View.OnClickListener btnLocaliseClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            //Start the new Intent to create the Localise Activity
            Intent localiseActivity = new Intent(MainActivity.this, LocaliseActivity.class);
            startActivity(localiseActivity);
        }
    };

    public View.OnClickListener btnIdentifyClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Start Identify activities
            Intent identifyActivity = new Intent(MainActivity.this, IdentifyWingsActivity.class);
            startActivity(identifyActivity);
        }
    };

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

        //Firebase Launcher
        if (firebaseLauncher){

            Firebase.setAndroidContext(this);

            mFirebaseRef = new Firebase(FIREBASE);

            //here we test the Database
//            Firebase testFirebaseLauncher = mFirebaseRef.child("testFb");
//            testFirebaseLauncher.push().child("testFb").setValue("testSucceeded");

            firebaseLauncher = false;
        }

        //Parse Launcher
        if(parseLauncher) {
            // [Optional] Power your app with Local Datastore. For more info, go to
            // https://parse.com/docs/android/guide#local-datastore
            Parse.enableLocalDatastore(getApplicationContext());
            String applicationID = this.getString(R.string.APPLICATION_ID);
            String keyClient = this.getString(R.string.CLIENT_KEY);
            Parse.initialize(this, applicationID, keyClient);
            ParseUser.enableAutomaticUser();
            ParseACL defaultACL = new ParseACL();
            ParseACL.setDefaultACL(defaultACL, true);

            parseLauncher = false;
        }

    }
}