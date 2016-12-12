package com.sciencetogether.perecastor.mosquitomapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Père Castor on 04/04/2016.
 */
public class IdentifyMouthpieceActivity extends Activity {

    //widget
    Button mSolutionMouthpiece1 = null;
    Button mSolutionMouthpiece2 = null;
    Button mErrorMouthpiece     = null;

    //intent
    public final static String MOUTHPIECE = "mouthpiece";
    public final static String WINGS_MOUTHPIECE = "wings";
    int wings_int;
    int mouthpiece_int;

    public View.OnClickListener btnMouthpieceSolution1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mouthpiece_int = 1;

            //Start Intent
            Intent identifyMouthpieceActivity = new Intent(IdentifyMouthpieceActivity.this, IdentifyMainActivity.class);
            identifyMouthpieceActivity.putExtra(WINGS_MOUTHPIECE,wings_int);
            identifyMouthpieceActivity.putExtra(MOUTHPIECE,mouthpiece_int);
            startActivity(identifyMouthpieceActivity);
        }
    };

    public View.OnClickListener btnMouthpieceSolution2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mouthpiece_int = 2;

            //Start Intent
            Intent identifyMouthpieceActivity = new Intent(IdentifyMouthpieceActivity.this, IdentifyMainActivity.class);
            identifyMouthpieceActivity.putExtra(WINGS_MOUTHPIECE,wings_int);
            identifyMouthpieceActivity.putExtra(MOUTHPIECE,mouthpiece_int);
            startActivity(identifyMouthpieceActivity);
        }
    };

    public View.OnClickListener btnMouthpieceError = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mouthpiece_int = -1;

            //Start Intent
            Intent identifyMouthpieceActivity = new Intent(IdentifyMouthpieceActivity.this, IdentifyMainActivity.class);
            identifyMouthpieceActivity.putExtra(WINGS_MOUTHPIECE,wings_int);
            identifyMouthpieceActivity.putExtra(MOUTHPIECE,mouthpiece_int);
            startActivity(identifyMouthpieceActivity);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sciencetogether.perecastor.mosquitomapper.R.layout.activity_identify_mouthpiece);

        //Intent recuperation
        Intent i = getIntent();

        wings_int = i.getIntExtra(IdentifyWingsActivity.WINGS, 0);

        mSolutionMouthpiece1 = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.mouthpiece_button_1);
        mSolutionMouthpiece2 = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.mouthpiece_button_2);
        mErrorMouthpiece     = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.mouthpiece_button_error);

        mSolutionMouthpiece1.setOnClickListener(btnMouthpieceSolution1);
        mSolutionMouthpiece2.setOnClickListener(btnMouthpieceSolution2);
        mErrorMouthpiece.setOnClickListener(btnMouthpieceError);

    }
}
