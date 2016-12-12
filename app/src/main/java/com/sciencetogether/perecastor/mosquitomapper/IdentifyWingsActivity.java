package com.sciencetogether.perecastor.mosquitomapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Père Castor on 04/04/2016.
 */
public class IdentifyWingsActivity extends Activity {

    //widget
    Button mSolutionWings1 = null;
    Button mSolutionWings2 = null;
    Button mErrorWings     = null;

    //intent
    public final static String WINGS = "wings";
    int wings_int;

    public View.OnClickListener btnWingsSolution1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            wings_int = 1;

            //Start Intent
            Intent identifyWingsActivity = new Intent(IdentifyWingsActivity.this, IdentifyMouthpieceActivity.class);
            identifyWingsActivity.putExtra(WINGS,wings_int);
            startActivity(identifyWingsActivity);
        }
    };

    public View.OnClickListener btnWingsSolution2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            wings_int = 2;

            //Start Intent
            Intent identifyWingsActivity = new Intent(IdentifyWingsActivity.this, IdentifyMouthpieceActivity.class);
            identifyWingsActivity.putExtra(WINGS,wings_int);
            startActivity(identifyWingsActivity);
        }
    };

    public View.OnClickListener btnWingsError = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            wings_int = -1;

            //Start Intent
            Intent identifyWingsActivity = new Intent(IdentifyWingsActivity.this, IdentifyMouthpieceActivity.class);
            identifyWingsActivity.putExtra(WINGS,wings_int);
            startActivity(identifyWingsActivity);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sciencetogether.perecastor.mosquitomapper.R.layout.activity_identify_wings);

        //Intent recuperation
        Intent i = getIntent();


        mSolutionWings1 = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.wings_button_1);
        mSolutionWings2 = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.wings_button_2);
        mErrorWings     = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.wings_button_error);

        mSolutionWings1.setOnClickListener(btnWingsSolution1);
        mSolutionWings2.setOnClickListener(btnWingsSolution2);
        mErrorWings.setOnClickListener(btnWingsError);
    }
}
