package com.example.perecastor.mosquitofinder10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Père Castor on 04/04/2016.
 */
public class ExperienceIdentifyActivity extends Activity {

    Button mButtonSave = null;
    Button mButtonFinish = null;
    Button mButtonQuizz = null;

    boolean loopBoolean = LoopBoolean.getmInstance().getLoopBoolean();

    ParseObject MosquitoData;

    public static final String FIREBASEIDENTIFICATION = "https://mosquitofinder.firebaseio.com/";
    private Firebase mFirebaseRef;

    double loctest = 0;

    public ParseObject saveAllIdentifyInParseByLocalisation(Intent intent){
        ParseObject data = new ParseObject("MosquitoIdentifyData");

        data.put("antennae",intent.getIntExtra(IdentifyMainActivity.ANTENNAE, 0));
        data.put("mouthpiece",intent.getIntExtra(IdentifyMainActivity.MOUTHPIECE_ANTENNAE, 0));
        data.put("wings",intent.getIntExtra(IdentifyMainActivity.WINGS_ANTENNAE, 0));

        return  data;
    };

    public void saveAllInFirebaseId(Intent intent){

        Firebase.setAndroidContext(this);

        mFirebaseRef = new Firebase(FIREBASEIDENTIFICATION);

        Firebase firebaseIdLauncher = mFirebaseRef.child("Identification");

        firebaseIdLauncher.child("Identification").child("Antennae").setValue(intent.getIntExtra(IdentifyMainActivity.ANTENNAE, 0));
        firebaseIdLauncher.child("Identification").child("Mouthpiece").setValue(intent.getIntExtra(IdentifyMainActivity.MOUTHPIECE_ANTENNAE, 0));
        firebaseIdLauncher.child("Identification").child("Wings").setValue(intent.getIntExtra(IdentifyMainActivity.WINGS_ANTENNAE, 0));

    }

    //Button Listener

    public View.OnClickListener btnSaveIdentifyClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MosquitoData.saveInBackground();

            Toast.makeText(getBaseContext(),
                    "You save data on the cloud",
                    Toast.LENGTH_LONG).show();
        }
    };

    public View.OnClickListener btnFinishIdentifyClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LoopBoolean.getmInstance().setLoopboolean(true);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };

    public View.OnClickListener btnQuizzClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LoopBoolean.getmInstance().setLoopboolean(false);

            Intent localiseActivity = new Intent(getApplicationContext(), LocaliseActivity.class);
            startActivity(localiseActivity);
        }
    };

    public void invisibility(){
        mButtonQuizz.setVisibility(View.INVISIBLE);
    }

    public void visibility(){
        mButtonQuizz.setVisibility(View.VISIBLE);
    }

    //Creation

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        //set id
        mButtonSave         = (Button) findViewById(R.id.save_button_id);
        mButtonFinish       = (Button) findViewById(R.id.finish_button_id);
        mButtonQuizz        = (Button) findViewById(R.id.change_button_id);

        //Intent, save data and ParseObject
        Intent iIdentify = getIntent();

        //test the succes of the identification
        loctest = iIdentify.getIntExtra(IdentifyMainActivity.ANTENNAE, 0);
        if(loctest != 0)
        {
            Toast.makeText(getBaseContext(),
                    "success",
                    Toast.LENGTH_LONG).show();
        }

        //Save ParseData
        MosquitoData = saveAllIdentifyInParseByLocalisation(iIdentify);

        //Button set Text
        mButtonQuizz.setText("Locate");

        saveAllInFirebaseId(iIdentify);

//        Toast.makeText(getBaseContext(),
//                Boolean.toString(loopBoolean),
//                Toast.LENGTH_LONG).show();

        if (!loopBoolean){

            //mButtonQuizz.setVisibility(View.INVISIBLE);
            invisibility();

        }
        else{

            //mButtonQuizz.setText(View.VISIBLE);
            visibility();

        }


        mButtonSave.setOnClickListener(btnSaveIdentifyClick);
        mButtonFinish.setOnClickListener(btnFinishIdentifyClick);
        mButtonQuizz.setOnClickListener(btnQuizzClick);
    }
}
