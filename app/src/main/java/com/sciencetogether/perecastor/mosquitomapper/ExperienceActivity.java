package com.sciencetogether.perecastor.mosquitomapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

/**
 * Created by Père Castor on 27/12/2015.
 */

public class ExperienceActivity extends Activity {

    //Attribut button
    Button mButtonFinish = null;
    Button mButtonId = null;

    //Instance boolean to know if we come from an identify action
    boolean loopBoolean = LoopBoolean.getmInstance().getLoopBoolean();

    //Firebase attribut
    public static final String FIREBASEQUIZZ = "https://mosquitofinder.firebaseio.com/";
    private Firebase mFirebaseRef;
    private String IDFirebase;
    Intent iQuestionnaire;

    //Show if data work
    double loctest = 0;

    //Save on Firebase
    public void saveAllInFirebase(Intent intent){

        Firebase.setAndroidContext(this);

        mFirebaseRef = new Firebase(FIREBASEQUIZZ);

        Firebase firebaseQuizzLauncher = mFirebaseRef.child("Quizzs");
        Firebase newFirebaseQuizzLauncher = firebaseQuizzLauncher.push();

        IDFirebase = newFirebaseQuizzLauncher.getKey();

        newFirebaseQuizzLauncher.child("Quizz").child("Latitude").setValue(intent.getDoubleExtra(QuestionnaireActivity.LATITUDE_FINAL, 0));
        newFirebaseQuizzLauncher.child("Quizz").child("Longitude").setValue(intent.getDoubleExtra(QuestionnaireActivity.LONGITUDE_FINAL, 0));
        newFirebaseQuizzLauncher.child("Quizz").child("Mosquito").setValue(intent.getDoubleExtra(QuestionnaireActivity.MOSQUITO, 0));
        newFirebaseQuizzLauncher.child("Quizz").child("Size").setValue(intent.getDoubleExtra(QuestionnaireActivity.SIZE, 0));
        newFirebaseQuizzLauncher.child("Quizz").child("Daytime").setValue(intent.getIntExtra(QuestionnaireActivity.DAYTIME, 0));
        newFirebaseQuizzLauncher.child("Quizz").child("Temperature").setValue(intent.getIntExtra(QuestionnaireActivity.TEMPERATURE, 0));
        newFirebaseQuizzLauncher.child("Quizz").child("Bodywarm").setValue(intent.getIntExtra(QuestionnaireActivity.BODY_WATER, 0));
        newFirebaseQuizzLauncher.child("Quizz").child("Inside_or_Outside").setValue(intent.getIntExtra(QuestionnaireActivity.INSIDE, 0));
        newFirebaseQuizzLauncher.child("Quizz").child("Picture").setValue(intent.getIntExtra(QuestionnaireActivity.PICTURE, 0));
        newFirebaseQuizzLauncher.child("Quizz").child("PictureID").setValue(intent.getStringExtra(QuestionnaireActivity.PICTUREID));

        //Add the Id if it is a loop
        if(!LoopBoolean.getmInstance().getLoopBoolean()){
            newFirebaseQuizzLauncher.child("Quizz").child("ID_of_Identification").setValue(LoopBoolean.getmInstance().getLoopId());
        }

    }

    //Button Listener

    //Finish Button
    public View.OnClickListener btnFinishClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Firebase Save
            saveAllInFirebase(iQuestionnaire);

            //Instance
            LoopBoolean.getmInstance().setLoopboolean(true);
            LoopBoolean.getmInstance().setLoopId("None so Error");

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };

    //This two method permit the loop over identify and locate activity
    public void invisibility(){
        mButtonId.setVisibility(View.INVISIBLE);
    }
    public void visibility(){
        mButtonId.setVisibility(View.VISIBLE);
    }

    //Identify Button
    public View.OnClickListener btnIdClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Firebase Save
            saveAllInFirebase(iQuestionnaire);

            //Instance
            LoopBoolean.getmInstance().setLoopboolean(false);
            LoopBoolean.getmInstance().setLoopId(IDFirebase);

            Intent intent = new Intent(getApplicationContext(), IdentifyWingsActivity.class);
            startActivity(intent);
        }
    };

    //OnCreate
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(com.sciencetogether.perecastor.mosquitomapper.R.layout.activity_experience);

        //Set id
        mButtonFinish   = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.finish_button_id);
        mButtonId       = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.change_button_id);

        //Intent, take data to save on cloud
        iQuestionnaire = getIntent();

        //Button set Text
        mButtonId.setText(com.sciencetogether.perecastor.mosquitomapper.R.string.identify);


        if (!loopBoolean){
            invisibility();
        }
        else{
            visibility();
        }

        //set Click Listener
        mButtonFinish.setOnClickListener(btnFinishClick);
        mButtonId.setOnClickListener(btnIdClick);
    }
}
