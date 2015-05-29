package com.bettehem.skijudgingsteno;

import android.app.DialogFragment;
import android.os.*;
import android.app.*;
import android.content.*;

/*
This Dialog is dynamic. or adaptive. whatever you want to call it. What does it do?
It is able to adapt to whatever is needed, so For example, you want to open a dialog to ask the
user if they want to delete a picture, you can do it with this. DynamicConfirmationDialog uses
the help of SharedPreferencesSavingAndLoading, to accomplish this. You can this way define the question,
and the two answers that the user can select from. You can also define, what each answer will do.
 */
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
