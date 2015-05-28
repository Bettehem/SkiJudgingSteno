package com.bettehem.skijudgingsteno;


//imports. Depending on the IDE that is used, imports are, or aren't added automatically when needed.
//Android Studio, Eclipse and AIDE suggests imports automatically, and with a simple tap, or click, an import can be added
import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;


public class MainMenu extends ActionBarActivity implements View.OnClickListener{

	//Variables
    SharedPreferencesSavingAndLoading savingAndLoading;
    boolean isTutorialCompleted;
    Button mainMenuSkiJudgingButton, mainMenuSnowboardJudgingButton, mainMenuSettingsButton;
    Intent openTutorial, openSkiJudging, openSnowboardJudging, openSettings;
	String[] eventTypes, competitorsUse;

	//Called when the activity is launched/created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		//sets the view to a layout
        setContentView(R.layout.activity_main_menu);
		
		//calls the "variables" method
        variables();
    }

	//this method is for setting up all of the variables.
	//All different types of variables are been set up in different methods, to make them easier to find,
	//and makes the code easier to understand.
    public void variables(){
        intents();
		arrays();
        sharedPreferences();
        buttons();
    }

    //Everything regarding intents are defined here.
    public void intents(){

        //each intent variable is "linked" to a class.
        //in this case, these intents will be used when an activity needs starting
        //for example, below, when a button is clicked, it will execute a line like this:
        //startActivity(INTENT);
        //and in place of INTENT, one of the intent variables below, will be used.
        openTutorial = new Intent(this, Tutorial.class);
        openSkiJudging = new Intent(this, SkiJudging.class);
    }

    //Everything regarding arrays are defined here.
	public void arrays(){

        //arrays in array.xml are defined here, so that they can be used as variables in java.
		eventTypes = getResources().getStringArray(R.array.events_list_array);
		competitorsUse = getResources().getStringArray(R.array.competitors_use_list_array);
	}

    //Everything regarding SharedPreferences are defined here.
    public void sharedPreferences(){

        //savingAndLoading is initialized here, if that makes any sense.
        savingAndLoading = new SharedPreferencesSavingAndLoading();

        //savingAndLoading preferenceFilename is set to the original PreferenceFilename.
        //this is somewhat explained in the AddEvent.java, in the startup method.
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;

        //does what it says. Saves a string array. when a value is saved using the SharedPreferencesSavingAndLoading.java
        //class, you need to do a few things. After typing in "savingAndLoading.", without the quotes of course,
        //you need to tell, what kind of value you want to save. So in this case, that would be a string array.
        //so we are at "savingAndLoading.saveStringArray" now. After that, You need to add brackets, if the IDE you are using
        //doesn't add them automatically. Also, remember to add ";" to the end of the line to avoid errors.
        //in the brackets, three values are needed. First is the context. this, in simple mans language means what class you are in.
        //the easiest way to handle this is by just typing "this", as has been done below, since it refers to THIS current class.
        //Second
		savingAndLoading.saveStringArray(this, "eventTypes", eventTypes);
		savingAndLoading.saveStringArray(this, "competitorsUse", competitorsUse);


        savingAndLoading.preferenceFilename = "Events";

        if (!savingAndLoading.loadBoolean(this, "hasCreatedEvents")) {
            savingAndLoading.saveBoolean(this, "hasCreatedEvents", false);
        }
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        isTutorialCompleted = savingAndLoading.loadBoolean(this, "isTutorialCompleted");
        if (!isTutorialCompleted){
            startActivity(openTutorial);
        }
    }

    //Everything regarding buttons are defined here.
    public void buttons(){
        mainMenuSkiJudgingButton = (Button) findViewById(R.id.mainMenuSkiJudgingButton);
        mainMenuSnowboardJudgingButton = (Button) findViewById(R.id.mainMenuSnowboardJudgingButton);
        mainMenuSettingsButton = (Button) findViewById(R.id.mainmenuAppSettingsButton);

        mainMenuSkiJudgingButton.setOnClickListener(this);
        mainMenuSnowboardJudgingButton.setOnClickListener(this);
        mainMenuSettingsButton.setOnClickListener(this);
    }


	
	//The two following methods are for the options menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	
	//When something with an OnClickListener is clicked, this method is called.
	//In this case, the buttons call this method when they are clicked
    @Override
    public void onClick(View v) {
		
		//this switch case statement checks what the id of the item was that called this method,
		//and if it matches any of the cases, a corresponding action will be performed
        switch (v.getId()){

            //if the button that says Ski Juding is pressed this case will be true.
            case R.id.mainMenuSkiJudgingButton:
                //starts an activity using an intent that has been defined in the intents method
                startActivity(openSkiJudging);
                break;

            //if the button that says Snowboard Juding is pressed this case will be true.
            case R.id.mainMenuSnowboardJudgingButton:

                break;

            //if the button that says Settings is pressed this case will be true.
            case R.id.mainmenuAppSettingsButton:

                break;
        }
    }
}
