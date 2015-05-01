package com.bettehem.skijudgingsteno;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.*;


public class AddEvent extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
	
    SharedPreferencesSavingAndLoading savingAndLoading;
	SavingAndLoadingProfiles savingAndLoadingProfiles;
    String eventType, profileName, competitorsUse, loadedEventTypeFromProfile, loadedCompetitorsUseFromProfile;
    Intent intent;
    TextView addingEventText, newEventAddInfoTextView;
    ViewFlipper addEventViewFlipper;
    Button addProfileInEventScreen, saveProfile, addEventLoadExistingProfileButton;
    boolean isUseExistingProfileButtonClicked = false;
    EditText profileNameEditText;
	Spinner addNewProfileSelectEventTypeSpinner, addNewEventLoadExistingProfileSelectionSpinner, addNewProfileSelectWhatCompetitorsUseSpinner, addNewEventSelectEventTypeSpinner, addNewEventSelectWhatCompetitorsUseSpinner;
    String[] profileDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        variables();
        startup();
    }

    public void startup(){
		savingAndLoading.preferenceFilename = "Profiles";
		if (savingAndLoading.loadBoolean(this, "has_created_profiles")){
			addEventViewFlipper.setDisplayedChild(1);
			addProfileInEventScreen.setText("Create new event");
		}
		
        savingAndLoading.preferenceFilename = "Settings";
        if (!savingAndLoading.loadBoolean(this, "hasCreatedEvents")){
            addingEventText.setText(getString(R.string.creatingfirsteventtext));

        }

        if (eventType.contentEquals("Slopestyle")){
			addNewProfileSelectEventTypeSpinner.setSelection(0);
            addNewEventSelectEventTypeSpinner.setSelection(0);
        }else{
			addNewProfileSelectEventTypeSpinner.setSelection(1);
            addNewEventSelectEventTypeSpinner.setSelection(1);
        }
    }

    public void variables(){
        intents();
        sharedPreferences();
        strings();
        textViews();
        buttons();
        viewFlippers();
        editTexts();
		spinners();
		profileSaverAndLoader();
    }

    public void intents(){
        intent = getIntent();
    }

    public void strings(){
        eventType = intent.getExtras().getString("eventType");
        savingAndLoading.preferenceFilename = "Settings";
        competitorsUse = savingAndLoading.loadString(this, "competitorsUseCurrent");
    }

    public void sharedPreferences(){
        savingAndLoading = new SharedPreferencesSavingAndLoading();
    }

    public void textViews(){
        addingEventText = (TextView) findViewById(R.id.addingEventText);
        newEventAddInfoTextView = (TextView) findViewById(R.id.newEventAddInfoTextView);
    }

    public void buttons(){
        addProfileInEventScreen = (Button) findViewById(R.id.addProfileInEventScreenButton);
        saveProfile = (Button) findViewById(R.id.saveProfileButton);
		addEventLoadExistingProfileButton = (Button) findViewById(R.id.addEventLoadExistingProfileButton);

        addProfileInEventScreen.setOnClickListener(this);
        saveProfile.setOnClickListener(this);
		addEventLoadExistingProfileButton.setOnClickListener(this);
    }

    public void viewFlippers(){
        addEventViewFlipper = (ViewFlipper) findViewById(R.id.addEventViewFlipper);
    }

    public void editTexts(){
        profileNameEditText = (EditText) findViewById(R.id.profileNameEditText);
    }
	
	public void spinners(){
		addNewProfileSelectEventTypeSpinner = (Spinner) findViewById(R.id.addNewProfileSelectEventTypeSpinner);
		addNewEventLoadExistingProfileSelectionSpinner = (Spinner) findViewById(R.id.addNewEventLoadExistingProfileSelectionSpinner);
		addNewProfileSelectWhatCompetitorsUseSpinner = (Spinner) findViewById(R.id.addNewProfileSelectWhatCompetitorsUseSpinner);
        addNewEventSelectEventTypeSpinner = (Spinner) findViewById(R.id.addNewEventSelectEventTypeSpinner);
        addNewEventSelectWhatCompetitorsUseSpinner = (Spinner) findViewById(R.id.addNewEventSelectWhatCompetitorsUseSpinner);


        addNewProfileSelectEventTypeSpinner.setOnItemSelectedListener(this);
		savingAndLoading.preferenceFilename = "Settings";
		addNewProfileSelectEventTypeSpinner.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "eventTypes")
        ));
			
		addNewEventLoadExistingProfileSelectionSpinner.setOnItemSelectedListener(this);
		
		addNewProfileSelectWhatCompetitorsUseSpinner.setOnItemSelectedListener(this);
		addNewProfileSelectWhatCompetitorsUseSpinner.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "competitorsUse")
        ));

        addNewEventSelectEventTypeSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = "Settings";
        addNewEventSelectEventTypeSpinner.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "eventTypes")
        ));

        addNewEventSelectWhatCompetitorsUseSpinner.setOnItemSelectedListener(this);
        addNewEventSelectWhatCompetitorsUseSpinner.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "competitorsUse")
        ));
		
	}
	
	public void profileSaverAndLoader(){
		savingAndLoadingProfiles = new SavingAndLoadingProfiles();
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
        switch (v.getId()){
            case R.id.addProfileInEventScreenButton:

                switch (addEventViewFlipper.getDisplayedChild()){
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
                savingAndLoadingProfiles.addProfile(this, profileName, eventType, competitorsUse);
				savingAndLoading.preferenceFilename = "Profiles";
                savingAndLoading.saveBoolean(this, "has_created_profiles", true);
                
				
				addEventViewFlipper.setVisibility(View.GONE);
                addEventViewFlipper.setDisplayedChild(1);
				
				
                addProfileInEventScreen.setText("Create new event");
                addProfileInEventScreen.setVisibility(View.VISIBLE);
                break;
				
			case R.id.addEventLoadExistingProfileButton:
                if (!isUseExistingProfileButtonClicked){
                    savingAndLoading.preferenceFilename = "Profiles";
                    addNewEventLoadExistingProfileSelectionSpinner.setAdapter(new ArrayAdapter<String>(
                            this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "profile_list")
                    ));
                    addNewEventLoadExistingProfileSelectionSpinner.setVisibility(View.VISIBLE);
                    isUseExistingProfileButtonClicked = true;
                }
				break;
        }
		
    }
	
	
	
	
	@Override
	public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
	{
		switch (p1.getId()){
			case R.id.addNewProfileSelectEventTypeSpinner:
				switch (p3){
					case 0:
						
						break;
					case 1:
						addNewProfileSelectEventTypeSpinner.setSelection(0);
						Toast.makeText(this, getString(R.string.new_profile_slopestyle_only), Toast.LENGTH_LONG).show();
						break;
				}
				break;

            case R.id.addNewProfileSelectWhatCompetitorsUseSpinner:
                switch (p3){
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
                switch (p3){
                    case 0:

                        break;
                    case 1:
                        addNewEventSelectEventTypeSpinner.setSelection(0);
                        Toast.makeText(this, getString(R.string.new_profile_slopestyle_only), Toast.LENGTH_LONG).show();
                        break;
                }
                break;

            case R.id.addNewEventSelectWhatCompetitorsUseSpinner:
                switch (p3){
                    case 0:

                        break;
                    case 1:
                        addNewEventSelectWhatCompetitorsUseSpinner.setSelection(0);
                        Toast.makeText(this, getString(R.string.new_profile_skis_only), Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        addNewEventSelectWhatCompetitorsUseSpinner.setSelection(0);
                        Toast.makeText(this, getString(R.string.new_profile_skis_only), Toast.LENGTH_LONG).show();
                        break;
                }
                break;

            case R.id.addNewEventLoadExistingProfileSelectionSpinner:
                profileDetails = savingAndLoadingProfiles.loadProfile(this, savingAndLoading.loadStringArray(this, "profile_list")[p3]);

                loadedEventTypeFromProfile = profileDetails[0];
                if (loadedEventTypeFromProfile.contentEquals("Slopestyle")) {
                    addNewEventSelectEventTypeSpinner.setSelection(0);
                }else if (loadedEventTypeFromProfile.contentEquals("Half Pipe")){
                    addNewEventSelectEventTypeSpinner.setSelection(1);
                }

                loadedCompetitorsUseFromProfile = profileDetails[1];
                if (loadedCompetitorsUseFromProfile.contentEquals(getString(R.string.competitorsUseSkis))){
                    addNewEventSelectWhatCompetitorsUseSpinner.setSelection(0);
                }else if (loadedCompetitorsUseFromProfile.contentEquals(getString(R.string.competitorsUseSnowboards))){
                    addNewEventSelectWhatCompetitorsUseSpinner.setSelection(1);
                }else if (loadedCompetitorsUseFromProfile.contentEquals(getString(R.string.competitorsUseBoth))){
                    addNewEventSelectWhatCompetitorsUseSpinner.setSelection(2);
                }

                break;
		}
	}

	//This method is called when nothing is selected, but it's useless in this case, where this will never happen.
	@Override
	public void onNothingSelected(AdapterView<?> p1)
	{
		
	}

}
