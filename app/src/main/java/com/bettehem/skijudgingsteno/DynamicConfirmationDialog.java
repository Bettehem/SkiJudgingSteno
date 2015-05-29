package com.bettehem.skijudgingsteno;
import android.support.v4.app.DialogFragment;
import android.os.*;
import android.app.*;
import android.content.*;

public class DynamicConfirmationDialog extends DialogFragment{

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Onko es Hyvää")
			.setPositiveButton("Jooo" , new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// FIRE ZE MISSILES!
				}
			})
			.setNegativeButton("ei saatana", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// User cancelled the dialog
				}
			});
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
}
