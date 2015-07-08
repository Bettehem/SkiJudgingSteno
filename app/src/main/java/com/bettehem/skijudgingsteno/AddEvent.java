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
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;


public class AddEvent extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DynamicConfirmationDialog.PerformDynamicDialogAction, AddProfiles.AddingProfiles {
    //variables
    private SharedPreferencesSavingAndLoading savingAndLoading;
    private SavingAndLoadingProfiles savingAndLoadingProfiles;
    private SavingAndLoadingEvents savingAndLoadingEvents;
    private String eventType, competitorsUse, loadedEventTypeFromProfile, loadedCompetitorsUseFromProfile;
    private Intent intent, goBack;
    private TextView addingEventText, newEventAddInfoTextView;
    private Button addProfileInEventScreen, addEventLoadExistingProfileButton, saveEventButton, addEventCancelLoadExistingButton;
    private boolean isUseExistingProfileButtonClicked = false;
    private EditText eventEventLocationEditText, addEventNewEventNameEditText;
    private Spinner addNewEventLoadExistingProfileSelectionSpinner, addNewEventSelectEventTypeSpinner, addNewEventSelectWhatCompetitorsUseSpinner;
    private String[] profileDetails;
    private boolean isInvalidEventName = false;
    private FrameLayout addProfileContainer;
    private ViewStub addEventEventCreationLayout;
    private boolean canAddEvents, isInAddEventScreen;
    private FragmentManager manager = getFragmentManager();
    private AddProfiles addProfiles = new AddProfiles();

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

        addProfileContainer.setVisibility(View.GONE);

        //sets the savingAndLoading preferenceFilename to the default name that is used with profile details.
        //setting the savingAndLoading preferenceFilename is required every time when a method in that class is used,
        //so that needed details are saved to, and loaded from the right place, so that things don't get messed up.
        savingAndLoading.preferenceFilename = savingAndLoadingProfiles.profileDetailsFileName;

        //if the user has created profiles, this will be true
        if (savingAndLoading.loadBoolean(this, "has_created_profiles")) {

            //sets the displayed child to 1. It's good to notice that the ViewFlipper starts counting from 0.
            //It means that when this is set to 1, it will show the second child in the list. you can check
            //activity_add_event.xml to see what this means
            //addEventViewFlipper.setDisplayedChild(1);

            //this shouldn't even need explaining, but basically this just sets the text in a TextView to whatever the
            //value is.
            addProfileInEventScreen.setText(getString(R.string.create_new_event_text));

            //sets canAddEvents to true, so that the user will be able to add events
            canAddEvents = true;

            //changes the visibility of certain items in the layout
            addingEventText.setVisibility(View.VISIBLE);
            addProfileInEventScreen.setVisibility(View.GONE);
            addEventEventCreationLayout.setVisibility(View.VISIBLE);


        //if the user hasn't created profiles, this will be executed.
        }else{
            //sets canAddEvents to false, so that the user will be able to add events
            canAddEvents = false;

            //changes the visibility of certain items in the layout
            addingEventText.setVisibility(View.VISIBLE);
            addProfileInEventScreen.setVisibility(View.VISIBLE);
            addEventEventCreationLayout.setVisibility(View.GONE);

        }

        //sets the savingAndLoading preferenceFilename to the original preferenceFilename
        //more details about this can be found above.
        savingAndLoading.preferenceFilename = savingAndLoadingEvents.originalEventDetailsFilename;

        //if the user hasn't created events, this will be true
        if (!savingAndLoading.loadBoolean(this, "hasCreatedEvents")) {

            //sets canAddEvents to false, so that the user will be able to add events
            canAddEvents = false;

            //sets the preferenceFilename to the originalProfileDetailsFileName.
            savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
            //if the user has created profiles, but hasn't yet created an event, this will be true
            if (savingAndLoading.loadBoolean(this, "has_created_profiles")){

                //same with every TextView, just setting the text to something new.
                addingEventText.setText(getString(R.string.creating_first_event_text));
            }else{

                //same with every TextView, just setting the text to something new.
                addingEventText.setText(getString(R.string.creating_first_event_and_profile_text));
            }

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
            //so this means that the first item in the spinner's list is selected.
            addNewEventSelectEventTypeSpinner.setSelection(0);


            //if the event type is not Slopestyle(It will then be Half pipe), the code above will be skipped
            //and this will be executed
        } else {

            //sets the selection to 1. 1 is the second item in the spinner's list, since counting starts from 0.
            addNewEventSelectEventTypeSpinner.setSelection(1);
        }

        //sets the preferenceFilename to the original one.
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        savingAndLoading.saveBoolean(this, "hasSavedEvent", false);

    }

    //this method is for setting up all of the variables.
    //All different types of variables are been set up in different methods, to make them easier to find,
    //and makes the code easier to understand.
    public void variables() {
        viewStubs();
		addingProfiles();
        intents();
        sharedPreferences();
        strings();
        textViews();
        editTexts();
        spinners();
        profileSaverAndLoader();
        eventSaverAndLoader();
        buttons();
    }

    //ViewStubs are defined here.
    public void viewStubs(){
        addEventEventCreationLayout = (ViewStub) findViewById(R.id.addEventEventCreationLayout);
        addEventEventCreationLayout.setLayoutResource(R.layout.add_new_event_layout);
        addEventEventCreationLayout.inflate();
    }

    //AddProfiles is defined here.
	public void addingProfiles(){
		addProfiles = new AddProfiles();
        addProfileContainer = (FrameLayout) findViewById(R.id.profileContainer);
        addProfileContainer.setVisibility(View.GONE);
    }

    //Intents are defined here.
    public void intents() {

        intent = getIntent();
        //checks for the class that called this one, so when an activity using the intent "goBack" is started,
        //it will know where to take you.
        if (intent.getExtras().getString("whatClass").contentEquals("SkiSlopestyleEvent")) {
            goBack = new Intent(this, SkiSlopestyleEvent.class);
        }
    }

    //Strings are defined here.
    public void strings() {
        eventType = intent.getExtras().getString("eventType");
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        competitorsUse = savingAndLoading.loadString(this, "competitorsUseCurrent");
    }

    //SharedPreferencesSavingAndLoading is defined here.
    public void sharedPreferences() {
        savingAndLoading = new SharedPreferencesSavingAndLoading();
    }

    //TextViews are defined here.
    public void textViews() {
        addingEventText = (TextView) findViewById(R.id.addingEventText);
        newEventAddInfoTextView = (TextView) findViewById(R.id.newEventAddInfoTextView);
    }

    //Buttons are defined here.
    public void buttons() {
        addProfileInEventScreen = (Button) findViewById(R.id.addProfileInEventScreenButton);
        addEventLoadExistingProfileButton = (Button) findViewById(R.id.addEventLoadExistingProfileButton);
        saveEventButton = (Button) findViewById(R.id.saveEventButton);
        addEventCancelLoadExistingButton = (Button) findViewById(R.id.addEventCancelLoadExistingProfileButton);

        addProfileInEventScreen.setOnClickListener(this);
        addEventLoadExistingProfileButton.setOnClickListener(this);
        saveEventButton.setOnClickListener(this);
        addEventCancelLoadExistingButton.setOnClickListener(this);
    }

    //EditTexts are defined here.
    public void editTexts() {
        eventEventLocationEditText = (EditText) findViewById(R.id.eventEventLocationEditText);
        addEventNewEventNameEditText = (EditText) findViewById(R.id.addEventNewEventNameEditText);
    }

    //Spinners are defined here.
    public void spinners() {
        addNewEventLoadExistingProfileSelectionSpinner = (Spinner) findViewById(R.id.addNewEventLoadExistingProfileSelectionSpinner);
        addNewEventSelectEventTypeSpinner = (Spinner) findViewById(R.id.addNewEventSelectEventTypeSpinner);
        addNewEventSelectWhatCompetitorsUseSpinner = (Spinner) findViewById(R.id.addNewEventSelectWhatCompetitorsUseSpinner);

        addNewEventLoadExistingProfileSelectionSpinner.setOnItemSelectedListener(this);

        addNewEventSelectEventTypeSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;

        //sets an adapter for this spinner from a String array.
        addNewEventSelectEventTypeSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "eventTypes")
        ));

        addNewEventSelectWhatCompetitorsUseSpinner.setOnItemSelectedListener(this);
        addNewEventSelectWhatCompetitorsUseSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "competitorsUse")
        ));

    }

    //SavingAndLoadingProfiles is defined here.
    public void profileSaverAndLoader() {
        savingAndLoadingProfiles = new SavingAndLoadingProfiles();
    }

    //SavingAndLoadingEvents is defined here.
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

    //when an item with an OnClickListener is clicked, this method will be called.
    @Override
    public void onClick(View v) {

        //this switch checks for what item was clicked, based on it's id in this case, hence "getId()"
        switch (v.getId()) {
            //inside the switch statement, cases are defined. so first the switch gets the id of the clicked item.
            //then it checks below for if any of the cases matches the id of the clicked item, and executes that.


            case R.id.addProfileInEventScreenButton:

                //checks if the user can add events
                if (canAddEvents){
                    addProfileContainer.setVisibility(View.GONE);
                    addingEventText.setVisibility(View.VISIBLE);
                    addProfileInEventScreen.setVisibility(View.GONE);
                }else{
                    addProfileInEventScreen.setVisibility(View.GONE);
                    addingEventText.setVisibility(View.GONE);
                    addProfileContainer.setVisibility(View.VISIBLE);
                    manager.beginTransaction().add(R.id.profileContainer, addProfiles, "AddProfiles").commit();
                    isInAddEventScreen = true;
                }
                break;

            case R.id.addEventLoadExistingProfileButton:

                //checks if the UseExistingProfileButton is not clicked.
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
                //handles the saving of an event
                savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
                String[] eventList = savingAndLoading.loadStringArray(this, "eventTypes");
                String[] whatCompetitorsUse = savingAndLoading.loadStringArray(this, "competitorsUse");
                //sends all of the data to the event saver and will get back the information on if the event name was invalid.
                //if the event name isn't invalid, the event will be saved.
                isInvalidEventName = savingAndLoadingEvents.saveEvent(this, addEventNewEventNameEditText.getText().toString(), eventList[addNewEventSelectEventTypeSpinner.getSelectedItemPosition()], whatCompetitorsUse[addNewEventSelectWhatCompetitorsUseSpinner.getSelectedItemPosition()], eventEventLocationEditText.getText().toString());

                //if the event name wasn't invalid, this will be true and hasCreatedEvents and hasSavedEvent will be set to true
                if (!isInvalidEventName) {
                    savingAndLoading.preferenceFilename = savingAndLoadingEvents.eventDetailsFileName;
                    savingAndLoading.saveBoolean(this, "hasCreatedEvents", true);
                    savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
                    savingAndLoading.saveBoolean(this, "hasSavedEvent", true);

                    //starts the activity "goBack", which will take the user back to the previous Activity.
                    startActivity(goBack);
                    //last, the current activity will be finished.
                    finish();
                }
                break;

            case R.id.addEventCancelLoadExistingProfileButton:
                FragmentManager manager = getFragmentManager();
                DynamicConfirmationDialog confirmationDialog = new DynamicConfirmationDialog();
                //this will ask the user to confirm to not use an existing profile, because inputted data will be lost.
				confirmationDialog.showDynamicDialog(manager, "confirmationDialog", getString(R.string.dont_use_existing_profile_dialog_question), getString(R.string.dont_use_existing_profile_dialog_confirm_text), getString(R.string.dont_use_existing_profile_dialog_cancel_text), false);
                break;
        }

    }

    //called when the user clicks a button within the dialog.
	@Override
	public void onDynamicDialogButtonClicked(boolean isAnswerPositive)
	{
        //if the answer is positive, this will be true and the following code will be executed.
        //Positive meaning affirmative.
		if (isAnswerPositive){
            addEventLoadExistingProfileButton.setVisibility(View.VISIBLE);
            addNewEventLoadExistingProfileSelectionSpinner.setVisibility(View.GONE);
            addEventCancelLoadExistingButton.setVisibility(View.GONE);
            addNewEventSelectEventTypeSpinner.setSelection(0);
            addNewEventSelectWhatCompetitorsUseSpinner.setSelection(0);
            eventEventLocationEditText.setText("");
			isUseExistingProfileButtonClicked = false;
        }
	}

    //in this case, this method will only be called when selecting items in spinners,
    //since the spinners are the only things with OnItemSelectedListeners attached to them.
    @Override
    public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {

        //same as the switch within the onClick method. Just that this checks for which spinner was clicked.
        switch (p1.getId()) {

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

            //when an existing profile is selected from the list, the following will be executed.
            case R.id.addNewEventLoadExistingProfileSelectionSpinner:
                savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
                //saves all of the profile details in to a String array.
                //the [p3] at the end is for loading the details from the selected profile.
                //the way this works is the following:
                //   All of the profiles are in a String array. to demonstrate this, let's think that
                //   the user has created 4 profiles, with the names profile1, profile2, profile3 and profile4.
                //   profiles will be saved in to a list named "profile_list", in order of creation.
                //   in this case, profile_list would look like this: "profile1,profile2,profile3,profile4"
                //   now the user decides to use an existing profile when creating an event. When the "Use existing profile" -button is pressed,
                //   all items in the profile_list are loaded in to the spinner. So the spinner would display all of the profiles
                //   from 1 to 4. let's think that the user selects profile2. this method (OnItemSelected) will be called, and the switch statement
                //   will direct to this case. now let's break the following line of code in to pieces.
                //      1. whatever we get from this line of code, will be set as the value of a String array called profileDetails.
                //      2. savingAndLoadingProfiles.loadProfile requires context and the profile name to be able to give the correct details.
                //         In this case "this" is passed in as the context, just like normal, and then the profile name part is interesting.
                //      3. savingAndLoading.loadStringArray loads the list of the profiles in to an array. But how to know which profile to select?
                //         Well, its quite simple, actually. When OnItemSelectedListener is called an int value of the selected items position is passed as p3 in this case.
                //         so, since the user selected profile2, the value of p3 will be 1. (profile1 is at position 0, profile2 at 1, profile3 at 2 and profile4 at 3)
                //         Now that we know that the position of the selected item is stored in p3,
                //         we can use it to select the correct profile from the profile list. to visualize this,
                //         all of the values will be shown:
                //         profile_list contains "profile1,profile2,profile3,profile4" and we want the profile name at position 1, since that is the value of p3.
                //         So, savingAndLoading.loadStringArray will return the selected profile name with the help of p3.
                //      4. Now let's simplify this a bit, since now we know how we get the profile name, and we know that the selected profile is profile2,
                //         we can think of the following line of code as:
                //         profileDetails = savingAndLoadingProfiles.loadProfile(this, "profile2");
                //         Makes it look pretty simple, right? so, now all of the details are loaded from profile2.
                //      5. As mentioned before, all of the profile details will be saved in to an array (profileDetails).
                //         the first item in the array will be the event type, second will be the information on what competitors will be using, last will be the event location.
                //         Let's think that the event type in profile2 is Slopestyle and competitors use both, and there isn't any location specified,
                //         profileDetails would look like this when examined: {"Slopestyle","Both",""}, just as should be expected.
                //  That's it.
                profileDetails = savingAndLoadingProfiles.loadProfile(this, savingAndLoading.loadStringArray(this, savingAndLoadingProfiles.profileListName)[p3]);

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
	
	@Override
	public void onProfileSaved(boolean isInvalidProfileName, String eventType, String competitorsUse, String EventLocation)
	{
		if (!isInvalidProfileName){
            manager.beginTransaction().remove(addProfiles).commit();
            /*
			addProfileContainer.setVisibility(View.GONE);
			addProfileInEventScreen.setText(getString(R.string.create_new_event_text));
			addProfileInEventScreen.setVisibility(View.VISIBLE);
			addingEventText.setText(getString(R.string.create_new_event_text));
			*/
			startActivity(goBack);
			finish();
			canAddEvents = true;
		}
	}

	@Override
	public void onEventTypeSelected(int selectedItemPosition)
	{
		switch (selectedItemPosition){
			case 0:

				break;
			case 1:
				addProfiles.addNewProfileSelectEventTypeSpinner.setSelection(0);
				Toast.makeText(this, getString(R.string.new_profile_slopestyle_only), Toast.LENGTH_LONG).show();
				break;
		}
	}

	@Override
	public void onCompetitorsUseSelected(int selectedItemPosition)
	{
		switch (selectedItemPosition){
			case 0:

				break;
			case 1:
				addProfiles.addNewProfileSelectWhatCompetitorsUseSpinner.setSelection(0);
				Toast.makeText(this, getString(R.string.new_profile_skis_only), Toast.LENGTH_LONG).show();
				break;
			case 2:
				addProfiles.addNewProfileSelectWhatCompetitorsUseSpinner.setSelection(0);
				Toast.makeText(this, getString(R.string.new_profile_skis_only), Toast.LENGTH_LONG).show();
				break;
		}
	}

    //This method is called when nothing is selected, but it's useless in this case, where this will never happen.
    //When the OnItemSelectedListener is implemented, this is required to exist in the class though
    @Override
    public void onNothingSelected(AdapterView<?> p1) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(goBack);
    }

}
