package com.example.perecastor.mosquitofinder10;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perecastor.mosquitofinder10.R;

import java.util.Calendar;

public class QuestionnaireActivity extends Activity {

    //widget attribut
    Button mButtonReturnQuest = null;
    Button mButtonAccept = null;
    RadioGroup mRadioQuestionA = null;
    RadioGroup mRadioQuestionB = null;
    RadioGroup mRadioQuestionC = null;
    RadioGroup mRadioQuestionD = null;
    RadioGroup mRadioQuestionE = null;
    RadioGroup mRadioQuestionF = null;
    RadioGroup mRadioQuestionG = null;
    TextView mQuestionC = null;

    //Attribut
    double sLat;
    double sLon;
    int mosquitoFound;
    int size;
    int dayTime;
    int temperature;
    int bodyWater;
    int inside;
    int picture;

    //Intent Attribut
    public final static String LATITUDE_FINAL   = "Latitude";
    public final static String LONGITUDE_FINAL  = "Longitude";
    public final static String MOSQUITO   = "Mosquito";
    public final static String SIZE   = "Size";
    public final static String DAYTIME  = "Time";
    public final static String TEMPERATURE   = "Temperature";
    public final static String BODY_WATER  = "Body water";
    public final static String INSIDE = "inside_outside";
    public final static String PICTURE   = "picture";

    //Image Attribut
    static  final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap Pics = null;
    byte[] dbPics = null;
    boolean picstake = false;

    /*
    *
    *
    * Cam Method
    *
    * */

    //Check if there is a camera on the mobile
    public boolean hasCamera(){

        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //Launch the camera as an intent
    public void launchCamera(View view){

        Intent  camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void launchCamera(){

        Intent  camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camIntent, REQUEST_IMAGE_CAPTURE);
    }


    //We want to return the image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            //take the photo
            Bundle extras = data.getExtras();
            Pics = (Bitmap) extras.get("data");
            //Test DBB
            dbPics = DbBitmapUtility.getBytes(Pics);
            picstake = true;
        }
    }

    /*
    *
    *
    * Radio Method
    *
    * */

    //How many mosquito
    public void questionASolution()
    {
        if(mRadioQuestionA.getCheckedRadioButtonId() == R.id.radio_question_a_1){
            mosquitoFound = 1;
        }
        if(mRadioQuestionA.getCheckedRadioButtonId() == R.id.radio_question_a_2){
            mosquitoFound = 2;
        }
        if(mRadioQuestionA.getCheckedRadioButtonId() == R.id.radio_question_a_3){
            mosquitoFound = 3;
        }
        if(mRadioQuestionA.getCheckedRadioButtonId() == R.id.radio_question_a_4){
            mosquitoFound = 4;
        }
    }
    //Size
    public void questionBSolution()
    {
        if(mRadioQuestionB.getCheckedRadioButtonId() == R.id.radio_question_b_1){
            size = 1;
        }
        if(mRadioQuestionB.getCheckedRadioButtonId() == R.id.radio_question_b_2){
            size = 2;
        }
        if(mRadioQuestionB.getCheckedRadioButtonId() == R.id.radio_question_b_3) {
            size = 3;
        }
    }
    //Hours
    public void questionCSolution()
    {
        Calendar rightNow = Calendar.getInstance();
        dayTime = rightNow.get(Calendar.HOUR_OF_DAY);
    }
    //Temperature
    public void questionDSolution()
    {
        if(mRadioQuestionD.getCheckedRadioButtonId() == R.id.radio_question_d_1){
            temperature = 1;
        }
        if(mRadioQuestionD.getCheckedRadioButtonId() == R.id.radio_question_d_2){
            temperature = 2;
        }
        if(mRadioQuestionD.getCheckedRadioButtonId() == R.id.radio_question_d_3){
            temperature = 3;
        }
        if(mRadioQuestionD.getCheckedRadioButtonId() == R.id.radio_question_d_4){
            temperature = 4;
        }
    }
    //Body Water
    public void questionESolution()
    {
        if(mRadioQuestionE.getCheckedRadioButtonId() == R.id.radio_question_e_1) {
            bodyWater = 1;
        }
        if(mRadioQuestionE.getCheckedRadioButtonId() == R.id.radio_question_e_2) {
            bodyWater = 2;
        }
    }
    //Inside
    public void questionFSolution()
    {
        if(mRadioQuestionF.getCheckedRadioButtonId() == R.id.radio_question_f_1) {
            inside = 1;
        }
        if(mRadioQuestionF.getCheckedRadioButtonId() == R.id.radio_question_f_2) {
            inside = 2;
        }
    }
    //Picture
    public void questionGSolution()
    {
        if(mRadioQuestionG.getCheckedRadioButtonId() == R.id.radio_question_g_1) {
            picture = 1;
        }
        if(mRadioQuestionG.getCheckedRadioButtonId() == R.id.radio_question_g_2) {
            picture = 2;
        }
    }

    /*
    *
    *
    * Button Method
    *
    * */

    //accept button method, record all information
    public View.OnClickListener btnQuestAcceptClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Radio Check, if null = 0
            questionASolution();
            questionBSolution();
            questionCSolution();
            questionDSolution();
            questionESolution();
            questionFSolution();
            questionGSolution();

            //Pass the data
            Intent experienceActivity = new Intent(QuestionnaireActivity.this, ExperienceActivity.class);
            experienceActivity.putExtra(LATITUDE_FINAL,sLat);
            experienceActivity.putExtra(LONGITUDE_FINAL,sLon);
            experienceActivity.putExtra(MOSQUITO,mosquitoFound);
            experienceActivity.putExtra(SIZE,size);
            experienceActivity.putExtra(DAYTIME,dayTime);
            experienceActivity.putExtra(TEMPERATURE,temperature);
            experienceActivity.putExtra(BODY_WATER,bodyWater);
            experienceActivity.putExtra(INSIDE, inside);
            experienceActivity.putExtra(PICTURE, picture);



            //Launch the Camera
            //if(picture == 1){
            //    launchCamera(v);
            //}


            startActivity(experienceActivity);

        }
    };

    //return button method, go to locate with information
    public View.OnClickListener btnQuestReturnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    //
    public RadioGroup.OnCheckedChangeListener QuestGChanged = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (!picstake) {
                if (mRadioQuestionG.getCheckedRadioButtonId() == R.id.radio_question_g_1) {
                    launchCamera();
                }
            }
        }
    };

    /*
    *
    *
    * Create Method
    *
    * */

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        //set the attribut id
        mButtonReturnQuest = (Button) findViewById(R.id.question_return_id);
        mButtonAccept      = (Button) findViewById(R.id.question_next_id);
        mRadioQuestionA    = (RadioGroup) findViewById(R.id.group_question_a);
        mRadioQuestionB    = (RadioGroup) findViewById(R.id.group_question_b);
        //mRadioQuestionC    = (RadioGroup) findViewById(R.id.group_question_c);
        mRadioQuestionD    = (RadioGroup) findViewById(R.id.group_question_d);
        mRadioQuestionE    = (RadioGroup) findViewById(R.id.group_question_e);
        mRadioQuestionF    = (RadioGroup) findViewById(R.id.group_question_f);
        mRadioQuestionG    = (RadioGroup) findViewById(R.id.group_question_g);
        mQuestionC         = (TextView) findViewById(R.id.group_question_c);

        //Intent recuperation
        Intent i = getIntent();

        sLat = i.getDoubleExtra(LocaliseActivity.LATITUDE, 0);
        sLon = i.getDoubleExtra(LocaliseActivity.LONGITUDE, 0);

        if(sLat == 0 && sLon == 0){
            Toast.makeText(getBaseContext(),
                    "It seems an error occur in Localisation",
                    Toast.LENGTH_LONG).show();
        }

        //ClockTest
        Calendar rightNow = Calendar.getInstance();
        int h = rightNow.get(Calendar.HOUR_OF_DAY);
        String now = Integer.toString(h) + "H";
        mQuestionC.setText(now);

        //Set Click Listener
        mButtonReturnQuest.setOnClickListener(btnQuestReturnClick);
        mButtonAccept.setOnClickListener(btnQuestAcceptClick);
        mRadioQuestionG.setOnCheckedChangeListener(QuestGChanged);

    }
}
