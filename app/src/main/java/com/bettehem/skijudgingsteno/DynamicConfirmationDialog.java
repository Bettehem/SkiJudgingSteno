package com.bettehem.skijudgingsteno;

import android.app.DialogFragment;
import android.os.*;
import android.app.*;
import android.content.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.widget.*;
import java.lang.reflect.*;
import java.io.*;

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


	Class callerClass;
	Method callerClassActionMethod;
	Object variableConstruction;
	Context jeh;
	
	//Here you will have to tell DynamicConfirmationDialog, what method you want to use,
	//when a button is clicked.
	public void setDynamicDialogAction(Context context, String className, String methodName){
		jeh = context;
		try
		{
			callerClass = Class.forName(className);
		}
		catch (ClassNotFoundException e)
		{}
		Toast.makeText(context, "Class is: " + callerClass.getName(), Toast.LENGTH_LONG).show();
		
		try
		{
			callerClassActionMethod = callerClass.getMethod(methodName, boolean.class);
		}
		catch (NoSuchMethodException e)
		{}
		Toast.makeText(context, "Method is: " + callerClassActionMethod.getName(), Toast.LENGTH_LONG).show();
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
					performAction(true);
				}
			})

			.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					performAction(false);
				}
			});

        //This Creates the Dialog
        return builder.create();
    }

	private void performAction(boolean isAnswerPositive){
		try
		{
			variableConstruction = callerClass.getConstructor(boolean.class).newInstance(isAnswerPositive);
		}
		catch (NoSuchMethodException | SecurityException e) {
				            // Exceptions thrown
				            e.printStackTrace();
				        } catch (InstantiationException e) {
				            
				            e.printStackTrace();
				        } catch (IllegalAccessException e) {
				            
				            e.printStackTrace();
				        } catch (IllegalArgumentException e) {
				            
				            e.printStackTrace();
				        } catch (InvocationTargetException e) {
				            // TODO Auto-generated catch block
				            e.printStackTrace();
				        }
		Toast.makeText(jeh, "Answer is: " + isAnswerPositive, Toast.LENGTH_LONG).show();
		Toast.makeText(jeh, "VariableConstruction is: " + variableConstruction.toString(), Toast.LENGTH_LONG).show();
	}

}
