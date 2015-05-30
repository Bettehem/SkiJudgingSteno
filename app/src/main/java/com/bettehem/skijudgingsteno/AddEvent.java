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

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.*;


public class AddEvent extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //variables
    SharedPreferencesSavingAndLoading savingAndLoading;
    SavingAndLoadingProfiles savingAndLoadingProfiles;
    SavingAndLoadingEvents savingAndLoadingEvents;
    String eventType, profileName, competitorsUse, loadedEventTypeFromProfile, loadedCompetitorsUseFromProfile, eventLocation;
    Intent intent, goBack;
    TextView addingEventText, newEventAddInfoTextView;
    ViewFlipper addEventViewFlipper;
    Button addProfileInEventScreen, saveProfile, addEventLoadExistingProfileButton, saveEventButton, addEventCancelLoadExistingButton;
    boolean isUseExistingProfileButtonClicked = false;
    EditText profileNameEditText, eventEventLocationEditText, profileEventLocationEditText, addEventNewEventNameEditText;
    Spinner addNewProfileSelectEventTypeSpinner, addNewEventLoadExistingProfileSelectionSpinner, addNewProfileSelectWhatCompetitorsUseSpinner, addNewEventSelectEventTypeSpinner, addNewEventSelectWhatCompetitorsUseSpinner;
    String[] profileDetails;
    boolean isInvalidProfileName = false;
    boolean isInvalidEventName = false;

    //Called when the activity is launched/created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //calls the "variables" method
        variables();

        //calls the "startup" method
        startup();
    }

    //this method is for setting things for when the activity is opened.
    //in this case, this method checks if the user has created profiles and events before, and does
    //modifications to the view regarding that.
    public void startup() {
        //sets the savingAndLoading preferenceFilename to the default name that is used with profile details.
        //setting the savingAndloading preferencefilename is required every time when a method in that class is used,
        //so that needed details are saved to, and loaded from the right place, so that things don't get messed up.
        savingAndLoading.preferenceFilename = savingAndLoadingProfiles.profileDetailsFileName;

        //if the user has created profiles, this will be true
        if (savingAndLoading.loadBoolean(this, "has_created_profiles")) {

            //sets the displayed child to 1. It's good to notice that the ViewFlipper starts counting from 0.
            //It means that when this is set to 1, it will show the second child in the list. you can check
            //activity_add_event.xml to see what this means
            addEventViewFlipper.setDisplayedChild(1);

            //this shouldn't even need explaining, but basically this just sets the text in a TextView to whatever the
            //value is.
            addProfileInEventScreen.setText(getString(R.string.create_new_event_text));
        }

        //sets the savingAndLoading preferenceFilename to the original preferenceFilename
        //more details aout this can be found above.
        savingAndLoading.preferenceFilename = savingAndLoadingEvents.originalEventDetailsFilename;

        //if the user hasn't created events, this will be true
        if (!savingAndLoading.loadBoolean(this, "hasCreatedEvents")) {

            //same with every TextView, just setting the text to something new.
            addingEventText.setText(getString(R.string.creating_first_event_and_profile_text));


            //if the user has created events, the code above is skipped, and this will be executed
        } else {

            //sets the visibility of this TextView to gone.
            //what it will basically look like, when in the app, is that the TextView would be deleted.
            //the difference between invisible and gone is, that if this would be set to invisible, the TextView
            //would still take the space on the screen, you just cant see it. when it's set to gone, it will be invisible
            //and it wont take space on the screen either
            addingEventText.setVisibility(View.GONE);
        }

        //checks if the event type is Slopestyle that you are adding, and if it is this will be true
        if (eventType.contentEquals("Slopestyle")) {

            //sets the currently selected items in these spinners to 0. Spinners start counting from 0,
            //so this means that the first item in the spinners list is selected.
            addNewProfileSelectEventTypeSpinner.setSelection(0);
            addNewEventSelectEventTypeSpinner.setSelection(0);


            //if the event type is not Slopestyle(It will then be Half pipe), the code above will be skipped
            //and this will be executed
        } else {

            //sets the selection to 1. 1 is the second item in the spinner's list, since counting starts from 0.
            addNewProfileSelectEventTypeSpinner.setSelection(1);
            addNewEventSelectEventTypeSpinner.setSelection(1);
        }
    }

    //this method is for setting up all of the variables.
    //All different types of variables are been set up in different methods, to make them easier to find,
    //and makes the code easier to understand.
    public void variables() {
        intents();
        sharedPreferences();
        strings();
        textViews();
        viewFlippers();
        editTexts();
        spinners();
        profileSaverAndLoader();
        eventSaverAndLoader();
        buttons();
    }

    //Everything regarding intents are defined here.
    public void intents() {

        intent = getIntent();
        if (intent.getExtras().getString("whatClass").contentEquals("SkiSlopestyleEvent")) {
            goBack = new Intent(this, SkiSlopestyleEvent.class);
        }
    }

    public void strings() {
        eventType = intent.getExtras().getString("eventType");
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        competitorsUse = savingAndLoading.loadString(this, "competitorsUseCurrent");
    }

    public void sharedPreferences() {
        savingAndLoading = new SharedPreferencesSavingAndLoading();
    }

    public void textViews() {
        addingEventText = (TextView) findViewById(R.id.addingEventText);
        newEventAddInfoTextView = (TextView) findViewById(R.id.newEventAddInfoTextView);
    }

    public void buttons() {
        addProfileInEventScreen = (Button) findViewById(R.id.addProfileInEventScreenButton);
        saveProfile = (Button) findViewById(R.id.saveProfileButton);
        addEventLoadExistingProfileButton = (Button) findViewById(R.id.addEventLoadExistingProfileButton);
        saveEventButton = (Button) findViewById(R.id.saveEventButton);
        addEventCancelLoadExistingButton = (Button) findViewById(R.id.addEventCancelLoadExistingProfileButton);

        addProfileInEventScreen.setOnClickListener(this);
        saveProfile.setOnClickListener(this);
        addEventLoadExistingProfileButton.setOnClickListener(this);
        saveEventButton.setOnClickListener(this);
        addEventCancelLoadExistingButton.setOnClickListener(this);
    }

    public void viewFlippers() {
        addEventViewFlipper = (ViewFlipper) findViewById(R.id.addEventViewFlipper);
    }

    public void editTexts() {
        profileNameEditText = (EditText) findViewById(R.id.profileNameEditText);
        eventEventLocationEditText = (EditText) findViewById(R.id.eventEventLocationEditText);
        profileEventLocationEditText = (EditText) findViewById(R.id.profileEventLocationEditText);
        addEventNewEventNameEditText = (EditText) findViewById(R.id.addEventNewEventNameEditText);
    }

    public void spinners() {
        addNewProfileSelectEventTypeSpinner = (Spinner) findViewById(R.id.addNewProfileSelectEventTypeSpinner);
        addNewEventLoadExistingProfileSelectionSpinner = (Spinner) findViewById(R.id.addNewEventLoadExistingProfileSelectionSpinner);
        addNewProfileSelectWhatCompetitorsUseSpinner = (Spinner) findViewById(R.id.addNewProfileSelectWhatCompetitorsUseSpinner);
        addNewEventSelectEventTypeSpinner = (Spinner) findViewById(R.id.addNewEventSelectEventTypeSpinner);
        addNewEventSelectWhatCompetitorsUseSpinner = (Spinner) findViewById(R.id.addNewEventSelectWhatCompetitorsUseSpinner);


        addNewProfileSelectEventTypeSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        addNewProfileSelectEventTypeSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "eventTypes")
        ));

        addNewEventLoadExistingProfileSelectionSpinner.setOnItemSelectedListener(this);

        addNewProfileSelectWhatCompetitorsUseSpinner.setOnItemSelectedListener(this);
        addNewProfileSelectWhatCompetitorsUseSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "competitorsUse")
        ));

        addNewEventSelectEventTypeSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        addNewEventSelectEventTypeSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "eventTypes")
        ));

        addNewEventSelectWhatCompetitorsUseSpinner.setOnItemSelectedListener(this);
        addNewEventSelectWhatCompetitorsUseSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "competitorsUse")
        ));

    }

    public void profileSaverAndLoader() {
        savingAndLoadingProfiles = new SavingAndLoadingProfiles();
    }

    public void eventSaverAndLoader() {
        savingAndLoadingEvents = new SavingAndLoadingEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addProfileInEventScreenButton:

                switch (addEventViewFlipper.getDisplayedChild()) {
                    case 0:
                        addEventViewFlipper.setVisibility(View.VISIBLE);
                        addEventViewFlipper.setDisplayedChild(0);
                        addProfileInEventScreen.setVisibility(View.GONE);
                        break;

                    case 1:
                        addEventViewFlipper.setVisibility(View.VISIBLE);
                        addEventViewFlipper.setDisplayedChild(1);
                        addProfileInEventScreen.setVisibility(View.GONE);
                        break;
                }

                break;

            case R.id.saveProfileButton:
                profileName = profileNameEditText.getText().toString();
                eventLocation = profileEventLocationEditText.getText().toString();
                isInvalidProfileName = savingAndLoadingProfiles.addProfile(this, profileName, eventType, competitorsUse, eventLocation);
                if (!isInvalidProfileName) {
                    savingAndLoading.preferenceFilename = savingAndLoadingProfiles.profileDetailsFileName;
                    savingAndLoading.saveBoolean(this, "has_created_profiles", true);


                    addEventViewFlipper.setVisibility(View.GONE);
                    addEventViewFlipper.setDisplayedChild(1);


                    addProfileInEventScreen.setText("Create new event");
                    addProfileInEventScreen.setVisibility(View.VISIBLE);
                    addingEventText.setText(getString(R.string.create_new_event_text));
                }
                break;

            case R.id.addEventLoadExistingProfileButton:
                if (!isUseExistingProfileButtonClicked) {
                    savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
                    addNewEventLoadExistingProfileSelectionSpinner.setAdapter(new ArrayAdapter<>(
                            this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "profile_list")
                    ));
                    addEventLoadExistingProfileButton.setVisibility(View.GONE);
                    addNewEventLoadExistingProfileSelectionSpinner.setVisibility(View.VISIBLE);
                    addEventCancelLoadExistingButton.setVisibility(View.VISIBLE);
                    isUseExistingProfileButtonClicked = true;
                }
                break;

            case R.id.saveEventButton:
                savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
                String[] eventList = savingAndLoading.loadStringArray(this, "eventTypes");
                String[] whatCompetitorsUse = savingAndLoading.loadStringArray(this, "competitorsUse");
                isInvalidEventName = savingAndLoadingEvents.saveEvent(this, addEventNewEventNameEditText.getText().toString(), eventList[addNewEventSelectEventTypeSpinner.getSelectedItemPosition()], whatCompetitorsUse[addNewEventSelectWhatCompetitorsUseSpinner.getSelectedItemPosition()], eventEventLocationEditText.getText().toString());
                if (!isInvalidEventName) {
                    savingAndLoading.preferenceFilename = savingAndLoadingEvents.eventDetailsFileName;
                    savingAndLoading.saveBoolean(this, "hasCreatedEvents", true);
                    startActivity(goBack);
                    finish();
                }
                break;

            case R.id.addEventCancelLoadExistingProfileButton:
                FragmentManager manager = getFragmentManager();
                DynamicConfirmationDialog confirmationDialog = new DynamicConfirmationDialog();
                confirmationDialog.setDynamicDialogAction(this, getPackageName() + ".AddEvent", "dynamicConfirmationDialogActionFinished");
				confirmationDialog.showDynamicDialog(manager, "confirmationDialog", "testi", "juu", "ei", false);
                break;
        }

    }

    public void dynamicConfirmationDialogActionFinished(boolean isAnswerPositive){
        //Toast.makeText(this, "isAnswerPositive is: " + isAnswerPositive, Toast.LENGTH_SHORT).show();
		if (isAnswerPositive){
            addEventLoadExistingProfileButton.setVisibility(View.VISIBLE);
            addNewEventLoadExistingProfileSelectionSpinner.setVisibility(View.GONE);
            addEventCancelLoadExistingButton.setVisibility(View.GONE);
            addNewEventSelectEventTypeSpinner.setSelection(0);
            addNewEventSelectWhatCompetitorsUseSpinner.setSelection(0);
            eventEventLocationEditText.setText("");
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {
        switch (p1.getId()) {
            case R.id.addNewProfileSelectEventTypeSpinner:
                switch (p3) {
                    case 0:

                        break;
                    case 1:
                        addNewProfileSelectEventTypeSpinner.setSelection(0);
                        Toast.makeText(this, getString(R.string.new_profile_slopestyle_only), Toast.LENGTH_LONG).show();
                        break;
                }
                break;

            case R.id.addNewProfileSelectWhatCompetitorsUseSpinner:
                switch (p3) {
                    case 0:

                        break;
                    case 1:
                        addNewProfileSelectWhatCompetitorsUseSpinner.setSelection(0);
                        Toast.makeText(this, getString(R.string.new_profile_skis_only), Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        addNewProfileSelectWhatCompetitorsUseSpinner.setSelection(0);
                        Toast.makeText(this, getString(R.string.new_profile_skis_only), Toast.LENGTH_LONG).show();
                        break;
                }
                break;


            case R.id.addNewEventSelectEventTypeSpinner:
                switch (p3) {
                    case 0:

                        break;
                    case 1:
                        addNewEventSelectEventTypeSpinner.setSelection(0);
                        Toast.makeText(this, getString(R.string.new_event_slopestyle_only), Toast.LENGTH_LONG).show();
                        break;
                }
                break;

            case R.id.addNewEventSelectWhatCompetitorsUseSpinner:
                switch (p3) {
                    case 0:

                        break;
                    case 1:
                        addNewEventSelectWhatCompetitorsUseSpinner.setSelection(0);
                        Toast.makeText(this, getString(R.string.new_event_skis_only), Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        addNewEventSelectWhatCompetitorsUseSpinner.setSelection(0);
                        Toast.makeText(this, getString(R.string.new_event_skis_only), Toast.LENGTH_LONG).show();
                        break;
                }
                break;

            case R.id.addNewEventLoadExistingProfileSelectionSpinner:
                profileDetails = savingAndLoadingProfiles.loadProfile(this, savingAndLoading.loadStringArray(this, "profile_list")[p3]);

                loadedEventTypeFromProfile = profileDetails[0];
                if (loadedEventTypeFromProfile.contentEquals("Slopestyle")) {
                    addNewEventSelectEventTypeSpinner.setSelection(0);
                } else if (loadedEventTypeFromProfile.contentEquals("Half Pipe")) {
                    addNewEventSelectEventTypeSpinner.setSelection(1);
                }

                loadedCompetitorsUseFromProfile = profileDetails[1];
                if (loadedCompetitorsUseFromProfile.contentEquals(getString(R.string.competitorsUseSkis))) {
                    addNewEventSelectWhatCompetitorsUseSpinner.setSelection(0);
                } else if (loadedCompetitorsUseFromProfile.contentEquals(getString(R.string.competitorsUseSnowboards))) {
                    addNewEventSelectWhatCompetitorsUseSpinner.setSelection(1);
                } else if (loadedCompetitorsUseFromProfile.contentEquals(getString(R.string.competitorsUseBoth))) {
                    addNewEventSelectWhatCompetitorsUseSpinner.setSelection(2);
                }

                eventEventLocationEditText.setText(profileDetails[2]);


                break;
        }
    }

    //This method is called when nothing is selected, but it's useless in this case, where this will never happen.
    @Override
    public void onNothingSelected(AdapterView<?> p1) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(goBack);
    }

}
