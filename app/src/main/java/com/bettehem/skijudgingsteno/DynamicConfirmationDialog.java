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
        builder.setMessage("Onko es Hyvää")
			.setPositiveButton("Jooo" , new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Toast.makeText(getActivity(), "kek", Toast.LENGTH_SHORT).show();
				}
			})
			.setNegativeButton("nope", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Toast.makeText(getActivity(), "lel", Toast.LENGTH_SHORT).show();
				}
			});
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
}
