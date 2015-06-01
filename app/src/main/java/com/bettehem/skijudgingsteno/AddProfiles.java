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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProfiles extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private SharedPreferencesSavingAndLoading savingAndLoading;
    private SavingAndLoadingProfiles savingAndLoadingProfiles;
    private EditText profileNameEditText, profileEventLocationEditText;
    private Spinner addNewProfileSelectEventTypeSpinner, addNewProfileSelectWhatCompetitorsUseSpinner;
    private Button saveProfile;
    private String profileName, eventLocation, eventType, competitorsUse;
    private boolean isInvalidProfileName;
    private SavingProfiles savingProfiles;
    private Activity callerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_profile);

        getDetails();
        variables();
    }

    public void getDetails(){
        callerActivity =  getIntent().getExtras().getParcelable("Activity");
        Toast.makeText(this, String.valueOf(callerActivity), Toast.LENGTH_LONG).show();
    }

    private void variables(){
        sharedPreferences();
        profileSaverAndLoader();
        editTexts();
        spinners();
        buttons();
    }

    private void sharedPreferences(){
        savingAndLoading = new SharedPreferencesSavingAndLoading();
    }

    private void profileSaverAndLoader() {
        savingAndLoadingProfiles = new SavingAndLoadingProfiles();
    }

    private void editTexts(){
        profileNameEditText = (EditText) findViewById(R.id.profileNameEditText);
        profileEventLocationEditText = (EditText) findViewById(R.id.profileEventLocationEditText);
    }

    private void spinners(){
        addNewProfileSelectEventTypeSpinner = (Spinner) findViewById(R.id.addNewProfileSelectEventTypeSpinner);
        addNewProfileSelectWhatCompetitorsUseSpinner = (Spinner) findViewById(R.id.addNewProfileSelectWhatCompetitorsUseSpinner);

        addNewProfileSelectEventTypeSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        addNewProfileSelectEventTypeSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "eventTypes")
        ));

        addNewProfileSelectWhatCompetitorsUseSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        addNewProfileSelectWhatCompetitorsUseSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, "competitorsUse")
        ));
    }

    private void buttons(){
        saveProfile = (Button) findViewById(R.id.saveProfileButton);

        saveProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveProfileButton:
                profileName = profileNameEditText.getText().toString();
                eventLocation = profileEventLocationEditText.getText().toString();
                isInvalidProfileName = savingAndLoadingProfiles.addProfile(this, profileName, eventType, competitorsUse, eventLocation);
                if (!isInvalidProfileName) {
                    savingAndLoading.preferenceFilename = savingAndLoadingProfiles.profileDetailsFileName;
                    savingAndLoading.saveBoolean(this, "has_created_profiles", true);
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.addNewProfileSelectEventTypeSpinner:
                switch (position) {
                    case 0:

                        break;
                    case 1:
                        addNewProfileSelectEventTypeSpinner.setSelection(0);
                        Toast.makeText(this, getString(R.string.new_profile_slopestyle_only), Toast.LENGTH_LONG).show();
                        break;
                }
                break;

            case R.id.addNewProfileSelectWhatCompetitorsUseSpinner:
                switch (position) {
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    interface SavingProfiles{
        void onProfileSaved(boolean isInvalidProfilename);
    }

}
