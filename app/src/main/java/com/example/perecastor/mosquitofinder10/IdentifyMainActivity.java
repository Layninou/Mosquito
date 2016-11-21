package com.example.perecastor.mosquitofinder10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Père Castor on 27/12/2015.
 */
public class IdentifyMainActivity extends Activity {

    //Widget
    Button mSolution1 = null;
    Button mSolution2 = null;
    Button mError     = null;

    //Intent
    public final static String ANTENNAE = "antennae";
    public final static String MOUTHPIECE_ANTENNAE = "mouthpiece";
    public final static String WINGS_ANTENNAE = "wings";
    int wings_int;
    int mouthpiece_int;
    int antennae_int;

    public View.OnClickListener btnSolution1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            antennae_int = 1;

            //Start Intent
            Intent identifyAntennaeActivity = new Intent(IdentifyMainActivity.this, ExperienceIdentifyActivity.class);
            identifyAntennaeActivity.putExtra(ANTENNAE, antennae_int);
            identifyAntennaeActivity.putExtra(MOUTHPIECE_ANTENNAE, mouthpiece_int);
            identifyAntennaeActivity.putExtra(WINGS_ANTENNAE,wings_int);
            startActivity(identifyAntennaeActivity);
        }
    };

    public View.OnClickListener btnSolution2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            antennae_int = 2;

            //Start Intent
            Intent identifyAntennaeActivity = new Intent(IdentifyMainActivity.this, ExperienceIdentifyActivity.class);
            identifyAntennaeActivity.putExtra(ANTENNAE, antennae_int);
            identifyAntennaeActivity.putExtra(MOUTHPIECE_ANTENNAE, mouthpiece_int);
            identifyAntennaeActivity.putExtra(WINGS_ANTENNAE,wings_int);
            startActivity(identifyAntennaeActivity);
        }
    };

    public View.OnClickListener btnError = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            antennae_int = -1;

            //Start Intent
            Intent identifyAntennaeActivity = new Intent(IdentifyMainActivity.this, ExperienceIdentifyActivity.class);
            identifyAntennaeActivity.putExtra(ANTENNAE, antennae_int);
            identifyAntennaeActivity.putExtra(MOUTHPIECE_ANTENNAE, mouthpiece_int);
            identifyAntennaeActivity.putExtra(WINGS_ANTENNAE,wings_int);
            startActivity(identifyAntennaeActivity);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_main);

        //Intent recuperation
        Intent i = getIntent();

        wings_int = i.getIntExtra(IdentifyMouthpieceActivity.WINGS_MOUTHPIECE, 0);
        mouthpiece_int = i.getIntExtra(IdentifyMouthpieceActivity.MOUTHPIECE, 0);

        mSolution1 = (Button) findViewById(R.id.antennae_button_1);
        mSolution2 = (Button) findViewById(R.id.antennae_button_2);
        mError     = (Button) findViewById(R.id.antennae_button_error);

        mSolution1.setOnClickListener(btnSolution1);
        mSolution2.setOnClickListener(btnSolution2);
        mError.setOnClickListener(btnError);
    }
}
