package com.sciencetogether.perecastor.mosquitomapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

/**
 * Created by Père Castor on 04/04/2016.
 */

public class ExperienceIdentifyActivity extends Activity {

    //Attribut Button
    Button mButtonFinish = null;
    Button mButtonQuizz = null;

    //Instance boolean to know if we come from an locate action
    boolean loopBoolean = LoopBoolean.getmInstance().getLoopBoolean();

    //Firebase Attribut
    public static final String FIREBASEIDENTIFICATION = "https://mosquitofinder.firebaseio.com/";
    private Firebase mFirebaseRef;
    private String IDFirebase;
    Intent iIdentify;

    //show if data work
    double loctest = 0;

    //Save on Firebase
    public void saveAllInFirebaseId(Intent intent){

        Firebase.setAndroidContext(this);

        mFirebaseRef = new Firebase(FIREBASEIDENTIFICATION);

        Firebase firebaseIdLauncher = mFirebaseRef.child("Identifications");
        Firebase newFirebaseIdLauncher = firebaseIdLauncher.push();

        IDFirebase = newFirebaseIdLauncher.getKey();

        newFirebaseIdLauncher.child("Identification").child("Antennae").setValue(intent.getIntExtra(IdentifyMainActivity.ANTENNAE, 0));
        newFirebaseIdLauncher.child("Identification").child("Mouthpiece").setValue(intent.getIntExtra(IdentifyMainActivity.MOUTHPIECE_ANTENNAE, 0));
        newFirebaseIdLauncher.child("Identification").child("Wings").setValue(intent.getIntExtra(IdentifyMainActivity.WINGS_ANTENNAE, 0));

        //Add the Id if it is a loop
        if(!LoopBoolean.getmInstance().getLoopBoolean()){
            newFirebaseIdLauncher.child("Identification").child("ID_of_Identification").setValue(LoopBoolean.getmInstance().getLoopId());
        }

    }

    //Button Listener

    //Finish Button
    public View.OnClickListener btnFinishIdentifyClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Firebase Save
            saveAllInFirebaseId(iIdentify);

            //Instance
            LoopBoolean.getmInstance().setLoopboolean(true);
            LoopBoolean.getmInstance().setLoopId("None so Error");

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };

    //Locate Button
    public View.OnClickListener btnQuizzClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Firebase Save
            saveAllInFirebaseId(iIdentify);

            //Instance
            LoopBoolean.getmInstance().setLoopboolean(false);
            LoopBoolean.getmInstance().setLoopId(IDFirebase);

            Intent localiseActivity = new Intent(getApplicationContext(), LocaliseActivity.class);
            startActivity(localiseActivity);
        }
    };

    //This two method permit the loop over identify and locate activity
    public void invisibility(){
        mButtonQuizz.setVisibility(View.INVISIBLE);
    }
    public void visibility(){
        mButtonQuizz.setVisibility(View.VISIBLE);
    }

    //OnCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sciencetogether.perecastor.mosquitomapper.R.layout.activity_experience);

        //set id
        mButtonFinish       = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.finish_button_id);
        mButtonQuizz        = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.change_button_id);

        //Intent, save data and ParseObject
        iIdentify = getIntent();

        //Button set Text
        mButtonQuizz.setText(com.sciencetogether.perecastor.mosquitomapper.R.string.localise);


        if (!loopBoolean){
            invisibility();
        }
        else{
            visibility();
        }

        //Set Click Listener
        mButtonFinish.setOnClickListener(btnFinishIdentifyClick);
        mButtonQuizz.setOnClickListener(btnQuizzClick);
    }
}
