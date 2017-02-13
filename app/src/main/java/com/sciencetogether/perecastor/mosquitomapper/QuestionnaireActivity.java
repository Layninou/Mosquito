package com.sciencetogether.perecastor.mosquitomapper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class QuestionnaireActivity extends Activity {

    //widget attribut
    Button mButtonReturnQuest = null;
    Button mButtonAccept = null;
    RadioGroup mRadioQuestionA = null;
    RadioGroup mRadioQuestionB = null;
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
    public final static String LATITUDE_FINAL = "Latitude";
    public final static String LONGITUDE_FINAL = "Longitude";
    public final static String MOSQUITO = "Mosquito";
    public final static String SIZE = "Size";
    public final static String DAYTIME = "Time";
    public final static String TEMPERATURE = "Temperature";
    public final static String BODY_WATER = "Body water";
    public final static String INSIDE = "Inside_Outside";
    public final static String PICTURE = "Picture";
    public final static String PICTUREID = "Picture_ID";

    //Firebase
    public static final String FIREBASESTORAGE = "gs://mosquitofinder.appspot.com";
    public static final String FIREBASEPICTURE = "https://mosquitofinder.firebaseio.com/";
    private Firebase mFirebaseRef;
    public String uidFirebase;
    public String sortYearChild;
    public String sortMonthChild;

    //Image Attribut
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap Pics = null;
    boolean picstake = false;

    /*
    *
    *
    * Cam Method
    *
    * */

    //Check if there is a camera on the mobile
    public boolean hasCamera() {

        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //Launch the camera as an intent with view
    public void launchCamera(View view) {

        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camIntent, REQUEST_IMAGE_CAPTURE);
    }

    //Launch the camera as an intent without view
    public void launchCamera() {

        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camIntent, REQUEST_IMAGE_CAPTURE);
    }

    //Store in Firebase
    public void SavePicsInFirebase() {

        //test firebase
        Firebase.setAndroidContext(this);

        mFirebaseRef = new Firebase(FIREBASEPICTURE);

        Firebase firebasePicsLauncher = mFirebaseRef.child("Pictures");
        Firebase newFirebasePicsLauncher = firebasePicsLauncher.push();

        uidFirebase = newFirebasePicsLauncher.getKey();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Pics.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

        newFirebasePicsLauncher.child("Picture").setValue(base64Image);

    }


    //We want to return the image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //take the photo
            Bundle extras = data.getExtras();
            Pics = (Bitmap) extras.get("data");

            //Get the country
            //ATTENTION this isn't the country by location but by name use
            String localePlace = getApplicationContext().getResources().getConfiguration().locale.getCountry();

            //Save in firebase database and prevent the picture is take
            picstake = true;
            SavePicsInFirebase();

            //Send to the Storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://mosquitofinder.appspot.com");
            StorageReference thisStorage =
                    storageRef.child(localePlace).child(sortYearChild).child(sortMonthChild).child(uidFirebase).child("mosquitoes.jpg");

            //Transformation in lower quality image
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Pics.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            byte[] dataPics = baos.toByteArray();

            //Upload
            UploadTask uploadTask = thisStorage.putBytes(dataPics);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Handle Unsuccesful uploads
                    Toast.makeText(getBaseContext(),
                            "error, can't send Byte data to the storage",
                            Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //if we need the url, we keep this line
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });

        }
    }

    /*
    *
    * End Camera Method
    *
    */

    /*
    *
    *
    * Radio Button Method
    *
    * */

    //How many mosquito
    public void questionASolution() {
        if (mRadioQuestionA.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_a_1) {
            mosquitoFound = 1;
        }
        if (mRadioQuestionA.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_a_2) {
            mosquitoFound = 2;
        }
        if (mRadioQuestionA.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_a_3) {
            mosquitoFound = 3;
        }
        if (mRadioQuestionA.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_a_4) {
            mosquitoFound = 4;
        }
    }

    //Size
    public void questionBSolution() {
        if (mRadioQuestionB.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_b_1) {
            size = 1;
        }
        if (mRadioQuestionB.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_b_2) {
            size = 2;
        }
        if (mRadioQuestionB.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_b_3) {
            size = 3;
        }
    }

    //Hours
    public void questionCSolution() {
        Calendar rightNow = Calendar.getInstance();
        dayTime = rightNow.get(Calendar.HOUR_OF_DAY);
    }

    //Temperature
    public void questionDSolution() {
        if (mRadioQuestionD.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_d_1) {
            temperature = 1;
        }
        if (mRadioQuestionD.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_d_2) {
            temperature = 2;
        }
        if(mRadioQuestionD.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_d_3){
            temperature = 3;
        }
        if(mRadioQuestionD.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_d_4){
            temperature = 4;
        }
    }

    //Body Water
    public void questionESolution() {
        if (mRadioQuestionE.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_e_1) {
            bodyWater = 1;
        }
        if (mRadioQuestionE.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_e_2) {
            bodyWater = 2;
        }
    }

    //Inside or Outside
    public void questionFSolution() {
        if(mRadioQuestionF.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_f_1) {
            inside = 1;
        }
        if(mRadioQuestionF.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_f_2) {
            inside = 2;
        }
    }

    //Picture
    public void questionGSolution() {
        if (mRadioQuestionG.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_g_1) {
            picture = 1;
        }
        if (mRadioQuestionG.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_g_2) {
            picture = 2;
        }
    }

    /*
    *
    * End Radio Button Method
    *
    */

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
            experienceActivity.putExtra(LATITUDE_FINAL, sLat);
            experienceActivity.putExtra(LONGITUDE_FINAL, sLon);
            experienceActivity.putExtra(MOSQUITO, mosquitoFound);
            experienceActivity.putExtra(SIZE, size);
            experienceActivity.putExtra(DAYTIME, dayTime);
            experienceActivity.putExtra(TEMPERATURE, temperature);
            experienceActivity.putExtra(BODY_WATER, bodyWater);
            experienceActivity.putExtra(INSIDE, inside);
            experienceActivity.putExtra(PICTURE, picture);

            if(picstake) {
                //here send the data to create the link between the picture, and after the identification.
                experienceActivity.putExtra(PICTUREID, uidFirebase);
            }
            else{
                experienceActivity.putExtra(PICTUREID, "None");
            }

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

    //Lauch the camera if photo wanna be take (lauch only once)
    public RadioGroup.OnCheckedChangeListener QuestGChanged = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (!picstake) {
                if (mRadioQuestionG.getCheckedRadioButtonId() == com.sciencetogether.perecastor.mosquitomapper.R.id.radio_question_g_1) {
                    launchCamera();
                }
            }
        }
    };

    /*
    *
    *
    * OnCreate Method
    *
    * */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sciencetogether.perecastor.mosquitomapper.R.layout.activity_questionnaire);

        //set the attribut id
        mButtonReturnQuest = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.question_return_id);
        mButtonAccept = (Button) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.question_next_id);
        mRadioQuestionA = (RadioGroup) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.group_question_a);
        mRadioQuestionB = (RadioGroup) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.group_question_b);
        mRadioQuestionD = (RadioGroup) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.group_question_d);
        mRadioQuestionE = (RadioGroup) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.group_question_e);
        mRadioQuestionF = (RadioGroup) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.group_question_f);
        mRadioQuestionG = (RadioGroup) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.group_question_g);
        mQuestionC = (TextView) findViewById(com.sciencetogether.perecastor.mosquitomapper.R.id.group_question_c);

        //Intent recuperation
        Intent i = getIntent();

        sLat = i.getDoubleExtra(LocaliseActivity.LATITUDE, 0);
        sLon = i.getDoubleExtra(LocaliseActivity.LONGITUDE, 0);

        if (sLat == 0 && sLon == 0) {
            Toast.makeText(getBaseContext(),
                    "It seems an error occur in Localisation",
                    Toast.LENGTH_LONG).show();
        }

        //ClockTest
        Calendar rightNow = Calendar.getInstance();
        int h = rightNow.get(Calendar.HOUR_OF_DAY);
        int y = rightNow.get(Calendar.YEAR);
        int mo = rightNow.get(Calendar.MONTH);
        String now = Integer.toString(h) + "H";
        mQuestionC.setText(now);
        sortYearChild = Integer.toString(y);
        sortMonthChild = Integer.toString(mo);

        //Set Click Listener
        mButtonReturnQuest.setOnClickListener(btnQuestReturnClick);
        mButtonAccept.setOnClickListener(btnQuestAcceptClick);
        mRadioQuestionG.setOnCheckedChangeListener(QuestGChanged);

    }

}
