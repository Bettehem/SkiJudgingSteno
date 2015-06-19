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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.*;
import android.app.*;

public class DeleteProfiles extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, DynamicConfirmationDialog.PerformDynamicDialogAction
{

    private Spinner deleteProfileProfileSelectionListSpinner;
    private TextView deleteProfileEventTypeTextView, deleteProfileWhatCompetitorsUseTextView, deleteProfileEventLocationTextView;
    private Button deleteProfileConfirmationButton;
    private DeletingProfiles deletingProfiles;
    private Activity userActivity;
    private View fragmentView;
    private SharedPreferencesSavingAndLoading savingAndLoading;
    private SavingAndLoadingProfiles savingAndLoadingProfiles;
	private String[] profileList, profileDetails;
	private FragmentManager manager;
	private DynamicConfirmationDialog confirmationDialog;
	private int selectedProfilePosition;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        deletingProfiles = (DeletingProfiles) activity;
        userActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.delete_profile, container, false);
        variables();
        return fragmentView;
    }

    private void variables(){
		profileSaverAndLoader();
        sharedPreferences();
		strings();
        spinners();
        textViews();
		fragmentManagers();
		confirmationDialogs();
		buttons();
    }
	
	private void profileSaverAndLoader() {
        savingAndLoadingProfiles = new SavingAndLoadingProfiles();
    }

    private void sharedPreferences(){
        savingAndLoading = new SharedPreferencesSavingAndLoading();
    }
	
	private void strings(){
		savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
		profileList = savingAndLoading.loadStringArray(userActivity, "profile_list");
	}

    private void spinners(){
		deleteProfileProfileSelectionListSpinner = (Spinner) fragmentView.findViewById(R.id.deleteProfileProfileSelectionListSpinner);
		
		deleteProfileProfileSelectionListSpinner.setOnItemSelectedListener(this);
        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        deleteProfileProfileSelectionListSpinner.setAdapter(new ArrayAdapter<>(
														   userActivity, android.R.layout.simple_spinner_dropdown_item, profileList
													   ));
    }

    private void textViews(){
		deleteProfileEventTypeTextView = (TextView) fragmentView.findViewById(R.id.deleteProfileEventTypeTextView);
		deleteProfileWhatCompetitorsUseTextView = (TextView) fragmentView.findViewById(R.id.deleteProfileWhatCompetitorsUseTextView);
		deleteProfileEventLocationTextView = (TextView) fragmentView.findViewById(R.id.deleteProfileEventLocationTextView);
    }
	
	private void fragmentManagers(){
		manager = userActivity.getFragmentManager();
	}
	
	private void confirmationDialogs(){
		confirmationDialog = new DynamicConfirmationDialog();
	}
	
	private void buttons(){
		deleteProfileConfirmationButton = (Button) fragmentView.findViewById(R.id.deleteProfileConfirmationButton);
		deleteProfileConfirmationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
		switch (v.getId()){
			case R.id.deleteProfileConfirmationButton:
				confirmationDialog.showDynamicDialog(manager, "confirmationDialog", stringHelper(3, profileList[selectedProfilePosition]), userActivity.getResources().getString(R.string.delete_profile_dialog_confirm_text), userActivity.getResources().getString(R.string.dialog_cancel_text), false);
				break;
		}
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		switch (parent.getId()){
			case R.id.deleteProfileProfileSelectionListSpinner:
				selectedProfilePosition = position;
				profileDetails = savingAndLoadingProfiles.loadProfile(userActivity, profileList[position]);
				deleteProfileEventTypeTextView.setText(stringHelper(0, profileDetails[0]));
				deleteProfileWhatCompetitorsUseTextView.setText(stringHelper(1, profileDetails[1]));
				deleteProfileEventLocationTextView.setText(stringHelper(2, profileDetails[2]));
				break;
		}

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
	
	private String stringHelper(int helpPos, String string){
		String readyString = "Error! Correct information not found with detail " + helpPos + "!";
		
		switch (helpPos){
			case 0:
				readyString = userActivity.getResources().getString(R.string.event_type_text) + " " + string;
				break;
			case 1:
				readyString = userActivity.getResources().getString(R.string.competitors_use_text) + " " + string;
				break;
			case 2:
				readyString = userActivity.getResources().getString(R.string.event_location_text) + " " + string;
				break;
			case 3:
				readyString = userActivity.getResources().getString(R.string.delete_profile_warning_part1) + " " + string + " " + userActivity.getResources().getString(R.string.delete_profile_warning_part2);
				break;
		}
		
		return readyString;
	}
	
	@Override
	public void onDynamicDialogButtonClicked(boolean isAnswerPositive)
	{
		if (isAnswerPositive){
			savingAndLoadingProfiles.deleteProfile(userActivity, profileList[selectedProfilePosition]);
			deletingProfiles.onProfileDeleted(true);
		}
	}

    interface DeletingProfiles{
        void onProfileDeleted(boolean profileDeleted);
    }
}
