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
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;

/*
This Dialog is dynamic. or adaptive. whatever you want to call it. What does it do?
It is able to adapt to whatever is needed, so For example, you want to open a dialog to ask the
user if they want to delete a picture, you can do it with this. You can define the question,
and the two answers that the user can select from. You can also define, what each answer will do,
and if the user will be able to cancel the dialog.
 */
public class DynamicConfirmationDialog extends DialogFragment{
	private String messageText, positiveButtonText, negativeButtonText;
	boolean isCancellable = false;

	PerformDynamicDialogAction performAction;
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		performAction = (PerformDynamicDialogAction) activity;
	}

	public void showDynamicDialog(FragmentManager manager, String tag, String dynamicConfirmationDialogMessage, String dynamicConfirmationDialogPositiveButtonText, String dynamicConfirmationDialogNegativeButtonText, boolean dialogIsCancellable){
		messageText = dynamicConfirmationDialogMessage;
		positiveButtonText = dynamicConfirmationDialogPositiveButtonText;
		negativeButtonText = dynamicConfirmationDialogNegativeButtonText;
		isCancellable = dialogIsCancellable;
		
		show(manager, tag);
	}

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		setCancelable(isCancellable);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setMessage(messageText)

			.setPositiveButton(positiveButtonText , new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					performAction.onDynamicDialogButtonClicked(true);
				}
			})

			.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					performAction.onDynamicDialogButtonClicked(false);
				}
			});

        //This Creates the Dialog
        return builder.create();
    }
	
	interface PerformDynamicDialogAction{
		public void onDynamicDialogButtonClicked(boolean isAnswerPositive);
	}

}
