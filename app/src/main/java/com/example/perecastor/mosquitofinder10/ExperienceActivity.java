package com.example.perecastor.mosquitofinder10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perecastor.mosquitofinder10.R;
import com.firebase.client.Firebase;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Père Castor on 27/12/2015.
 */
public class ExperienceActivity extends Activity {

    Button mButtonSave = null;
    Button mButtonFinish = null;
    Button mButtonId = null;

    ParseObject mosquitoData;

    boolean loopBoolean = LoopBoolean.getmInstance().getLoopBoolean();

    public static final String FIREBASEQUIZZ = "https://mosquitofinder.firebaseio.com/";
    private Firebase mFirebaseRef;

    double loctest = 0;

    public ParseObject saveAllInParseByLocalisation(Intent intent){
        ParseObject data = new ParseObject("MosquitoFindData");

        data.put("Latitude", intent.getDoubleExtra(QuestionnaireActivity.LATITUDE_FINAL,0));
        data.put("Longitude", intent.getDoubleExtra(QuestionnaireActivity.LONGITUDE_FINAL, 0));
        data.put("Mosquito", intent.getIntExtra(QuestionnaireActivity.MOSQUITO, 0));
        data.put("Size", intent.getIntExtra(QuestionnaireActivity.SIZE, 0));
        data.put("Daytime", intent.getIntExtra(QuestionnaireActivity.DAYTIME, 0));
        data.put("Temperature", intent.getIntExtra(QuestionnaireActivity.TEMPERATURE, 0));
        data.put("BodyWarm", intent.getIntExtra(QuestionnaireActivity.BODY_WATER, 0));
        data.put("Inside_Outside", intent.getIntExtra(QuestionnaireActivity.INSIDE, 0));
        data.put("Picture", intent.getIntExtra(QuestionnaireActivity.PICTURE, 0));
        data.put("Identify", 0);

        return  data;
    };

    public void saveAllInFirebase(Intent intent){

        Firebase.setAndroidContext(this);

        mFirebaseRef = new Firebase(FIREBASEQUIZZ);

        Firebase firebaseQuizzLauncher = mFirebaseRef.child("Quizz");

        firebaseQuizzLauncher.child("Quizz").child("Latitude").setValue(intent.getDoubleExtra(QuestionnaireActivity.LATITUDE_FINAL, 0));
        firebaseQuizzLauncher.child("Quizz").child("Longitude").setValue(intent.getDoubleExtra(QuestionnaireActivity.LONGITUDE_FINAL, 0));
        firebaseQuizzLauncher.child("Quizz").child("Mosquito").setValue(intent.getDoubleExtra(QuestionnaireActivity.MOSQUITO, 0));
        firebaseQuizzLauncher.child("Quizz").child("Size").setValue(intent.getDoubleExtra(QuestionnaireActivity.SIZE, 0));
        firebaseQuizzLauncher.child("Quizz").child("Daytime").setValue(intent.getIntExtra(QuestionnaireActivity.DAYTIME, 0));
        firebaseQuizzLauncher.child("Quizz").child("Temperature").setValue(intent.getIntExtra(QuestionnaireActivity.TEMPERATURE, 0));
        firebaseQuizzLauncher.child("Quizz").child("Bodywarm").setValue(intent.getIntExtra(QuestionnaireActivity.BODY_WATER, 0));
        firebaseQuizzLauncher.child("Quizz").child("Inside_Outside").setValue(intent.getIntExtra(QuestionnaireActivity.INSIDE, 0));
        firebaseQuizzLauncher.child("Quizz").child("Picture").setValue(intent.getIntExtra(QuestionnaireActivity.PICTURE, 0));

    }

    public View.OnClickListener btnSaveClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mosquitoData.saveInBackground();


            Toast.makeText(getBaseContext(),
                    "You save data on the cloud",
                    Toast.LENGTH_LONG).show();
        }
    };

    public View.OnClickListener btnFinishClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LoopBoolean.getmInstance().setLoopboolean(true);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };
    public void invisibility(){
        mButtonId.setVisibility(View.INVISIBLE);
    }

    public void visibility(){
        mButtonId.setVisibility(View.VISIBLE);
    }
    public View.OnClickListener btnIdClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LoopBoolean.getmInstance().setLoopboolean(false);

            Intent intent = new Intent(getApplicationContext(), IdentifyWingsActivity.class);
            startActivity(intent);
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        //set id
        mButtonSave     = (Button) findViewById(R.id.save_button_id);
        mButtonFinish   = (Button) findViewById(R.id.finish_button_id);
        mButtonId       = (Button) findViewById(R.id.change_button_id);

        //Intent, save data and ParseObject
        Intent iQuestionnaire = getIntent();

        saveAllInFirebase(iQuestionnaire);

        loctest = iQuestionnaire.getDoubleExtra(QuestionnaireActivity.LATITUDE_FINAL, 0);
        if(loctest != 0)
        {
            Toast.makeText(getBaseContext(),
                    "Test successful",
                    Toast.LENGTH_LONG).show();
        }

        //Save ParseData
        mosquitoData = saveAllInParseByLocalisation(iQuestionnaire);

        //Button set Text
        mButtonId.setText("Identification");

        Toast.makeText(getBaseContext(),
                Boolean.toString(loopBoolean),
                Toast.LENGTH_LONG).show();

        if (!loopBoolean){
            invisibility();
        }
        else{
            visibility();
        }

        //set Click Listener
        mButtonSave.setOnClickListener(btnSaveClick);
        mButtonFinish.setOnClickListener(btnFinishClick);
        mButtonId.setOnClickListener(btnIdClick);
    }
}
