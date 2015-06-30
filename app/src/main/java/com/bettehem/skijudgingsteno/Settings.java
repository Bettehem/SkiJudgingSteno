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
import android.content.Intent;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;
import android.widget.*;


public class Settings extends ActionBarActivity implements View.OnClickListener, AddProfiles.AddingProfiles, DeleteProfiles.DeletingProfiles, DynamicConfirmationDialog.PerformDynamicDialogAction
{
	//SharedPreferences
	private SharedPreferencesSavingAndLoading savingAndLoading;
	private SavingAndLoadingProfiles savingAndLoadingProfiles;
	private ResetApp resetApp;
	
    //Intents
    private Intent openAddProfile, openDeleteProfile, goBack, goToMainMenu;

    //Buttons for settings_options.xml
    private Button profileSettingsButton, resetAppButton;

    //Buttons for profile_settings.xml
    private Button settingsAddProfileButton, settingsModifyExistingProfilesButton, settingsDeleteProfilesButton;

    //ViewFlipper that switches between different settings
    private ViewFlipper settingsViewFlipper;
	
	private FragmentManager manager = getFragmentManager();
	private AddProfiles addProfiles = new AddProfiles();
    private DeleteProfiles deleteProfiles = new DeleteProfiles();
	
	private boolean isInAddProfileScreen;
	private boolean isInDeleteProfileScreen;
	
	private DynamicConfirmationDialog confirmationDialog;

	private int confirmationDialogCaller = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        variables();

    }

    private void variables(){
		sharedPreferences();
		profileSaverAndLoader();
        intents();
        buttons();
        viewFlippers();
		dialogs();
		resettingApp();
    }

	private void sharedPreferences(){
		savingAndLoading = new SharedPreferencesSavingAndLoading();
		savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
		savingAndLoading.saveBoolean(this, "hasSavedProfile", false);
		savingAndLoading.saveBoolean(this, "hasDeletedProfile", false);
	}
	
	private void profileSaverAndLoader(){
		savingAndLoadingProfiles = new SavingAndLoadingProfiles();
	}

    private void intents(){
        openAddProfile = new Intent(this, AddProfiles.class);
        openDeleteProfile = new Intent(this, DeleteProfiles.class);
		if (getIntent().getExtras().getString("whatClass").contentEquals("MainMenu")) {
			goBack = new Intent(this, MainMenu.class);
		}
		goToMainMenu = new Intent(this, MainMenu.class);
	}

    private void buttons(){
        profileSettingsButton = (Button) findViewById(R.id.settingsProfileSettingsButton);
		resetAppButton = (Button) findViewById(R.id.settingsResetAppButton);

        settingsAddProfileButton = (Button) findViewById(R.id.settingsAddProfileButton);
        settingsModifyExistingProfilesButton = (Button) findViewById(R.id.settingsModifyExistingProfilesButton);
        settingsDeleteProfilesButton = (Button) findViewById(R.id.settingsDeleteProfilesButton);

        profileSettingsButton.setOnClickListener(this);
		resetAppButton.setOnClickListener(this);

        settingsAddProfileButton.setOnClickListener(this);
        settingsModifyExistingProfilesButton.setOnClickListener(this);
        settingsDeleteProfilesButton.setOnClickListener(this);
    }

    private void viewFlippers(){
        settingsViewFlipper = (ViewFlipper) findViewById(R.id.settingsViewFlipper);
    }
	
	private void dialogs(){
		confirmationDialog = new DynamicConfirmationDialog();
	}

	private void resettingApp(){
		resetApp = new ResetApp();
	}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settingsProfileSettingsButton:
                settingsViewFlipper.setDisplayedChild(1);
                break;

			case R.id.settingsResetAppButton:
				confirmationDialogCaller = 1;
				confirmationDialog.showDynamicDialog(manager, "confirmationDialog", getString(R.string.reset_app_warning_text), getString(R.string.reset_app_dialog_confirm_text), getString(R.string.dialog_cancel_text), false);
				break;

            case R.id.settingsAddProfileButton:
				settingsViewFlipper.setVisibility(View.GONE);
                manager.beginTransaction().add(R.id.profileContainer, addProfiles, "AddProfiles").commit();
				isInAddProfileScreen = true;
                break;

            case R.id.settingsModifyExistingProfilesButton:

                break;

            case R.id.settingsDeleteProfilesButton:
                settingsViewFlipper.setVisibility(View.GONE);
                manager.beginTransaction().add(R.id.profileContainer, deleteProfiles, "DeleteProfiles").commit();
                isInDeleteProfileScreen = true;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        switch (settingsViewFlipper.getDisplayedChild()){
            case 0:
				savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
				savingAndLoading.deleteIndividualValue(this, "hasSavedProfile");
				savingAndLoading.deleteIndividualValue(this, "hasDeletedProfile");
				startActivity(goBack);
                finish();
                break;

            default:
				//setting savingAndLoading preferenceFilename to the original value
				savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
				
				//if the user has saved the profile, set the displayed child in the viewflipper to 0,
				//which is thi first one in the list
				if (savingAndLoading.loadBoolean(this, "hasSavedProfile") || savingAndLoading.loadBoolean(this, "hasDeletedProfile")){
					
					savingAndLoading.saveBoolean(this, "hasSavedProfile", false);
					savingAndLoading.saveBoolean(this, "hasDeletedProfile", false);
					
					if (isInAddProfileScreen){
						manager.beginTransaction().remove(addProfiles).commit();
						settingsViewFlipper.setVisibility(View.VISIBLE);
						settingsViewFlipper.setDisplayedChild(1);
						isInAddProfileScreen = false;
					}else if (isInDeleteProfileScreen){
						manager.beginTransaction().remove(deleteProfiles).commit();
						settingsViewFlipper.setVisibility(View.VISIBLE);
						settingsViewFlipper.setDisplayedChild(1);
						isInDeleteProfileScreen = false;
					}else{
						settingsViewFlipper.setVisibility(View.VISIBLE);
						settingsViewFlipper.setDisplayedChild(0);
					}
					
				}else{
					
					if (isInAddProfileScreen){
						manager.beginTransaction().remove(addProfiles).commit();
						settingsViewFlipper.setVisibility(View.VISIBLE);
						settingsViewFlipper.setDisplayedChild(1);
						isInAddProfileScreen = false;
					}else if (isInDeleteProfileScreen){
						manager.beginTransaction().remove(deleteProfiles).commit();
						settingsViewFlipper.setVisibility(View.VISIBLE);
						settingsViewFlipper.setDisplayedChild(1);
						isInDeleteProfileScreen = false;
					}else{
						settingsViewFlipper.setVisibility(View.VISIBLE);
						settingsViewFlipper.setDisplayedChild(0);
					}
				}
                break;
        }
    }
	
	
	@Override
	public void onProfileSaved(boolean isInvalidProfileName, String eventType, String competitorsUse, String EventLocation)
	{
		if (!isInvalidProfileName){
			manager.beginTransaction().remove(addProfiles).commit();
			settingsViewFlipper.setVisibility(View.VISIBLE);
			savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
			savingAndLoading.saveBoolean(this, "hasSavedProfile", true);
			onBackPressed();
		}
	}

	@Override
	public void onEventTypeSelected(int selectedItemPosition)
	{
		
	}

	@Override
	public void onCompetitorsUseSelected(int selectedItemPosition)
	{
		
	}

    @Override
    public void onProfileDeleted(boolean profileDeleted) {
		if (profileDeleted){
			manager.beginTransaction().remove(deleteProfiles).commit();
			settingsViewFlipper.setVisibility(View.VISIBLE);
			isInDeleteProfileScreen = false;
			savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
			if (savingAndLoading.loadString(this, savingAndLoadingProfiles.profileListName).contentEquals("")){
				savingAndLoading.saveBoolean(this, "has_created_profiles", false);
			}
		}
    }
	
	@Override
	public void onDeleteButtonPressed()
	{
		confirmationDialogCaller = 2;
		confirmationDialog.showDynamicDialog(manager, "confirmationDialog", deleteProfiles.stringHelper(3, deleteProfiles.profileList[deleteProfiles.selectedProfilePosition]), getString(R.string.delete_profile_dialog_confirm_text), getString(R.string.dialog_cancel_text), false);
	}
	
	@Override
	public void onDynamicDialogButtonClicked(boolean isAnswerPositive)
	{
		if (isAnswerPositive){

			switch (confirmationDialogCaller){
				case 1:
					resetApp.resetAppData(this);
					confirmationDialogCaller = 0;
					Toast.makeText(this, getString(R.string.reset_app_complete), Toast.LENGTH_SHORT).show();
					startActivity(goToMainMenu);
					finish();
					break;

				case 2:
					savingAndLoadingProfiles.deleteProfile(this, deleteProfiles.profileList[deleteProfiles.selectedProfilePosition]);
					deleteProfiles.deletingProfiles.onProfileDeleted(true);
					savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
					savingAndLoading.saveBoolean(this, "hasDeletedProfile", true);
					Toast.makeText(this, getString(R.string.profile_deleted_message), Toast.LENGTH_SHORT).show();
					confirmationDialogCaller = 0;
					break;
			}

		}
	}

	@Override
	public void onProfilesNotFound()
	{
		onBackPressed();
		manager.beginTransaction().remove(deleteProfiles).commit();
		settingsViewFlipper.setVisibility(View.VISIBLE);
		Toast.makeText(this, getString(R.string.no_profiles_found_text), Toast.LENGTH_SHORT).show();
	}
}
