package com.bettehem.skijudgingsteno;

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

    public void intents(){
        openTutorial = new Intent(this, Tutorial.class);
        openSkiJudging = new Intent(this, SkiJudging.class);
    }
	
	public void arrays(){
		eventTypes = getResources().getStringArray(R.array.events_list_array);
		competitorsUse = getResources().getStringArray(R.array.competitors_use_list_array);
	}

    public void sharedPreferences(){
        savingAndLoading = new SharedPreferencesSavingAndLoading();
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
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
	//In this case, the buttons call this method
    @Override
    public void onClick(View v) {
		
		//this switch case statement checks what the id of the item was that called this method,
		//and if it matches any of the cases, a corresponding action will be performed
        switch (v.getId()){
            case R.id.mainMenuSkiJudgingButton:
                startActivity(openSkiJudging);
                break;
            case R.id.mainMenuSnowboardJudgingButton:

                break;
            case R.id.mainmenuAppSettingsButton:

                break;
        }
    }
}
