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
import android.widget.Toast;

public class AddProfiles extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private SharedPreferencesSavingAndLoading savingAndLoading;
    private SavingAndLoadingProfiles savingAndLoadingProfiles;
    private EditText profileNameEditText, profileEventLocationEditText;
    private Spinner addNewProfileSelectEventTypeSpinner, addNewProfileSelectWhatCompetitorsUseSpinner;
    private Button saveProfile;
    private String profileName, eventLocation, eventType, competitorsUse;
    private boolean isInvalidProfileName, isAllowedEventType;
    private AddingProfiles addingProfiles;
    private String[] allowedProfileEventTypes, allowedProfileCompetitorsUse;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        addingProfiles = (AddingProfiles) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        variables();
        return inflater.inflate(R.layout.add_new_profile, container, false);
    }

    public void setRestrictions(boolean allOptionsAllowed, String[] allowedEventTypes, String[] allowedCompetitorsUsing){
        if (allOptionsAllowed) {
            allowedProfileEventTypes = savingAndLoading.loadStringArray(getActivity().getApplicationContext(), "eventTypes");
            allowedProfileCompetitorsUse = savingAndLoading.loadStringArray(getActivity().getApplicationContext(), "competitorsUse");
        }else{
            allowedProfileEventTypes = allowedEventTypes;
            allowedProfileCompetitorsUse = allowedCompetitorsUsing;
        }
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
        profileNameEditText = (EditText) getView().findViewById(R.id.profileNameEditText);
        profileEventLocationEditText = (EditText) getView().findViewById(R.id.profileEventLocationEditText);
    }

    private void spinners(){
        addNewProfileSelectEventTypeSpinner = (Spinner) getView().findViewById(R.id.addNewProfileSelectEventTypeSpinner);
        addNewProfileSelectWhatCompetitorsUseSpinner = (Spinner) getView().findViewById(R.id.addNewProfileSelectWhatCompetitorsUseSpinner);

        addNewProfileSelectEventTypeSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        addNewProfileSelectEventTypeSpinner.setAdapter(new ArrayAdapter<>(
                getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(getActivity().getApplicationContext(), "eventTypes")
        ));

        addNewProfileSelectWhatCompetitorsUseSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        addNewProfileSelectWhatCompetitorsUseSpinner.setAdapter(new ArrayAdapter<>(
                getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(getActivity().getApplicationContext(), "competitorsUse")
        ));
    }

    private void buttons(){
        saveProfile = (Button) getActivity().findViewById(R.id.saveProfileButton);

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
                }
                addingProfiles.onProfileSaved(isInvalidProfileName, eventType, competitorsUse, eventLocation);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.addNewProfileSelectEventTypeSpinner:

                for (int i = 0; i < allowedProfileEventTypes.length; i++){
                    if (addNewProfileSelectEventTypeSpinner.getSelectedItem().equals(allowedProfileEventTypes[i])){
                        isAllowedEventType = true;
                        break;
                    }
                }
                if (isAllowedEventType){
                    switch (position){
                        case 0:
                            eventType = "Slopestyle";
                            break;

                        case 1:
                            eventType = "Half Pipe";
                            break;
                    }
                    addingProfiles.onEventTypeSelected(isAllowedEventType);
                }else{
                    addingProfiles.onEventTypeSelected(isAllowedEventType);
                }

                break;

            case R.id.addNewProfileSelectWhatCompetitorsUseSpinner:
                switch (position) {
                    case 0:

                        break;
                    case 1:
                        addNewProfileSelectWhatCompetitorsUseSpinner.setSelection(0);
                        Toast.makeText(getActivity(), getString(R.string.new_profile_skis_only), Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        addNewProfileSelectWhatCompetitorsUseSpinner.setSelection(0);
                        Toast.makeText(getActivity(), getString(R.string.new_profile_skis_only), Toast.LENGTH_LONG).show();
                        break;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    interface AddingProfiles{
        void onProfileSaved(boolean isInvalidProfilename, String eventType, String competitorsUse, String EventLocation);
        void onEventTypeSelected(boolean isAllowedEventType);
        void onCompetitorsUseSelected(boolean isAllowedCompetitorsUseType);
    }

}
