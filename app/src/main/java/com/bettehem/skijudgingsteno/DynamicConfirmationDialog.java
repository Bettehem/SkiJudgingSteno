package com.bettehem.skijudgingsteno;

import android.app.DialogFragment;
import android.os.*;
import android.app.*;
import android.content.*;
import android.widget.Toast;

public class DynamicConfirmationDialog extends DialogFragment{

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		setCancelable(false);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.dont_use_existing_profile_dialog_question))
			.setPositiveButton(getString(R.string.dont_use_existing_profile_dialog_confirm_text) , new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Toast.makeText(getActivity(), "Selected don\'t use", Toast.LENGTH_SHORT).show();
				}
			})
			.setNegativeButton(getString(R.string.dont_use_existing_profile_dialog_cancel_text), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
				}
			});
        //This Creates the Dialog
        return builder.create();
    }
	
}
