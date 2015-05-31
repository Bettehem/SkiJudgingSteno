package com.bettehem.skijudgingsteno;

/*
    Copyright 2015 Chris Mustola
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/.
 */

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
    SavingAndLoadingEvents savingAndLoadingEvents;
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
        eventsSavingAndLoading();
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
        openSettings = new Intent(this, Settings.class);
    }

    //Everything regarding arrays are defined here.
	public void arrays(){

        //arrays in array.xml are defined here, so that they can be used as variables in java.
		eventTypes = getResources().getStringArray(R.array.events_list_array);
		competitorsUse = getResources().getStringArray(R.array.competitors_use_list_array);
	}

    //Everything regarding savingAndLoadingEvents are defined here.
    public void eventsSavingAndLoading(){

        //savingAndLoadingEvents is initialized here. Or well, let's be simple.
        //Just add this, even you don't know what it means. It just works.
        savingAndLoadingEvents = new SavingAndLoadingEvents();
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
        //in the brackets, three values are needed and they are separated with commas. First is the context. this, in simple mans language means what class you are in.
        //the easiest way to handle this is by just typing "this", as has been done below, since it refers to THIS current class.
        //Second is the name for the value. so if you later look in to the SharedPreferences file(if you can read it on your device),
        //in this case eventTypes is the name for the variable that you will see.
        //It's generally good to use a name that is the same, or similar to the variable name in the third parameter, if exists.
        //In to the third parameter, you have to enter a value. in this case, a string array.
        //In this case, a string array that was defined earlier with the name eventTypes is used.
		savingAndLoading.saveStringArray(this, "eventTypes", eventTypes);

        //This is basically the same as below, but with different values.
		savingAndLoading.saveStringArray(this, "competitorsUse", competitorsUse);


        //sets the preferenceFilename to the original eventDetailsFilename.
        savingAndLoading.preferenceFilename = savingAndLoadingEvents.originalEventDetailsFilename;

        //if the user hasn't created events before, this will be true
        if (!savingAndLoading.loadBoolean(this, "hasCreatedEvents")) {

            //A value will be saved stating that the user hasn't created events before.
            savingAndLoading.saveBoolean(this, "hasCreatedEvents", false);
        }

        //sets the preferenceFilename to the original preferenceFilename.
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;

        //if the user hasn't completed the tutorial, this will be true
        if (!savingAndLoading.loadBoolean(this, "isTutorialCompleted")){

            //this will start the tutorial with the help of an intent that was defined earlier
            startActivity(openTutorial);
        }
    }

    //Everything regarding buttons are defined here.
    public void buttons(){

        //here the buttons are "linked" to the ones in the .xml files, in this case, activity_main_menu.xml
        //in the layout file, the buttons have been given IDs, and those are used here.
        mainMenuSkiJudgingButton = (Button) findViewById(R.id.mainMenuSkiJudgingButton);
        mainMenuSnowboardJudgingButton = (Button) findViewById(R.id.mainMenuSnowboardJudgingButton);
        mainMenuSettingsButton = (Button) findViewById(R.id.mainmenuAppSettingsButton);

        //Here the OnClickListeners are been set to the buttons. if "this" is used as an argument,
        //the OnClickListener has to be implemented.
        //This means that every time a button with an OnClickListener is clicked, it will call a method
        //with the name "onClick". It can be found below.
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

    //this is the second method for the options menu.
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
                startActivity(openSettings);
                break;
        }
    }
}
