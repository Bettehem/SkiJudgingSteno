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
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.android.gms.analytics.*;

public class AddProfiles extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    public Spinner addNewProfileSelectEventTypeSpinner, addNewProfileSelectWhatCompetitorsUseSpinner;
	
    private SharedPreferencesSavingAndLoading savingAndLoading;
    private SavingAndLoadingProfiles savingAndLoadingProfiles;
    private EditText profileNameEditText, profileEventLocationEditText;
    private Button saveProfile;
    private String profileName, eventLocation, eventType, competitorsUse;
    private boolean isInvalidProfileName;
    private AddingProfiles addingProfiles;
	private Activity userActivity;
	private View fragmentView;
	private Tracker tracker;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        addingProfiles = (AddingProfiles) activity;
		userActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.add_new_profile, container, false);
		variables();
        return fragmentView;
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
        profileNameEditText = (EditText) fragmentView.findViewById(R.id.profileNameEditText);
        profileEventLocationEditText = (EditText) fragmentView.findViewById(R.id.profileEventLocationEditText);
    }

    private void spinners(){
        addNewProfileSelectEventTypeSpinner = (Spinner) fragmentView.findViewById(R.id.addNewProfileSelectEventTypeSpinner);
        addNewProfileSelectWhatCompetitorsUseSpinner = (Spinner) fragmentView.findViewById(R.id.addNewProfileSelectWhatCompetitorsUseSpinner);

        addNewProfileSelectEventTypeSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        addNewProfileSelectEventTypeSpinner.setAdapter(new ArrayAdapter<>(
			userActivity, android.R.layout.simple_spinner_dropdown_item, userActivity.getResources().getStringArray(R.array.events_list_array)
        ));

        addNewProfileSelectWhatCompetitorsUseSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        addNewProfileSelectWhatCompetitorsUseSpinner.setAdapter(new ArrayAdapter<>(
			userActivity, android.R.layout.simple_spinner_dropdown_item, userActivity.getResources().getStringArray(R.array.competitors_use_list_array)
        ));
    }

    private void buttons(){
        saveProfile = (Button) fragmentView.findViewById(R.id.saveProfileButton);

        saveProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveProfileButton:
                profileName = profileNameEditText.getText().toString();
                eventLocation = profileEventLocationEditText.getText().toString();
                isInvalidProfileName = savingAndLoadingProfiles.addProfile(getActivity(), profileName, eventType, competitorsUse, eventLocation);
                if (!isInvalidProfileName) {
                    savingAndLoading.preferenceFilename = savingAndLoadingProfiles.profileDetailsFileName;
                    savingAndLoading.saveBoolean(getActivity(), "has_created_profiles", true);
					profileNameEditText.setText("");
					profileEventLocationEditText.setText("");
					addNewProfileSelectEventTypeSpinner.setSelection(0);
					addNewProfileSelectWhatCompetitorsUseSpinner.setSelection(0);
                }
				
                addingProfiles.onProfileSaved(isInvalidProfileName, eventType, competitorsUse, eventLocation);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.addNewProfileSelectEventTypeSpinner:
				addingProfiles.onEventTypeSelected(position);
				eventType = userActivity.getResources().getStringArray(R.array.events_list_array)[position];
                break;

            case R.id.addNewProfileSelectWhatCompetitorsUseSpinner:
                addingProfiles.onCompetitorsUseSelected(position);
				competitorsUse = userActivity.getResources().getStringArray(R.array.competitors_use_list_array)[position];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    interface AddingProfiles{
        void onProfileSaved(boolean isInvalidProfileName, String eventType, String competitorsUse, String EventLocation);
        void onEventTypeSelected(int selectedItemPosition);
        void onCompetitorsUseSelected(int selectedItemPosition);
    }

}
