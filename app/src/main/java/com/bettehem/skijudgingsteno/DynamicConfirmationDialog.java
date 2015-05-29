package com.bettehem.skijudgingsteno;

import android.app.DialogFragment;
import android.os.*;
import android.app.*;
import android.content.*;
import android.widget.Toast;

public class DynamicConfirmationDialog extends DialogFragment{

	SharedPreferencesSavingAndLoading savingAndLoading;
	SavingAndLoadingProfiles savingAndLoadingProfiles;

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		variables();
		setCancelable(false);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setMessage(getString(R.string.dont_use_existing_profile_dialog_question))

			.setPositiveButton(getString(R.string.dont_use_existing_profile_dialog_confirm_text) , new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
					savingAndLoading.saveBoolean(getActivity(), "notUsingExistingProfiles", true);
				}
			})

			.setNegativeButton(getString(R.string.dont_use_existing_profile_dialog_cancel_text), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
					savingAndLoading.saveBoolean(getActivity(), "notUsingExistingProfiles", false);
				}
			});


        //This Creates the Dialog
        return builder.create();
    }

	public void variables(){
		sharedPreferences();
		profileSaverAndLoader();
	}

	public void sharedPreferences(){
		savingAndLoading = new SharedPreferencesSavingAndLoading();
	}

	public void profileSaverAndLoader(){
		savingAndLoadingProfiles = new SavingAndLoadingProfiles();
	}

}
