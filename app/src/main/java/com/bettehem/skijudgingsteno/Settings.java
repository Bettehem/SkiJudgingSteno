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


public class Settings extends ActionBarActivity implements View.OnClickListener, AddProfiles.AddingProfiles
{
	//SharedPreferences
	private SharedPreferencesSavingAndLoading savingAndLoading;
	
    //Intents
    private Intent openAddProfile;

    //Buttons for settings_options.xml
    private Button profileSettingsButton;

    //Buttons for profile_settings.xml
    private Button settingsAddProfileButton, settingsModifyExistingProfilesButton, settingsDeleteProfilesButton;

    //ViewFlipper that switches between different settings
    private ViewFlipper settingsViewFlipper;
	
	private FragmentManager manager = getFragmentManager();
	private AddProfiles addProfiles = new AddProfiles();
	
	private boolean isInAddProfileScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        variables();

    }

    private void variables(){
		sharedPreferences();
        intents();
        buttons();
        viewFlippers();
    }
	private void sharedPreferences(){
		savingAndLoading = new SharedPreferencesSavingAndLoading();
		savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
		savingAndLoading.saveBoolean(this, "hasSavedProfile", false);
	}

    private void intents(){
        openAddProfile = new Intent(this, AddProfiles.class);
    }

    private void buttons(){
        profileSettingsButton = (Button) findViewById(R.id.settingsProfileSettingsButton);

        settingsAddProfileButton = (Button) findViewById(R.id.settingsAddProfileButton);
        settingsModifyExistingProfilesButton = (Button) findViewById(R.id.settingsModifyExistingProfilesButton);
        settingsDeleteProfilesButton = (Button) findViewById(R.id.settingsDeleteProfilesButton);

        profileSettingsButton.setOnClickListener(this);

        settingsAddProfileButton.setOnClickListener(this);
        settingsModifyExistingProfilesButton.setOnClickListener(this);
        settingsDeleteProfilesButton.setOnClickListener(this);
    }

    private void viewFlippers(){
        settingsViewFlipper = (ViewFlipper) findViewById(R.id.settingsViewFlipper);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settingsProfileSettingsButton:
                settingsViewFlipper.setDisplayedChild(1);
                break;

            case R.id.settingsAddProfileButton:
				settingsViewFlipper.setVisibility(View.GONE);
                manager.beginTransaction().add(R.id.addProfileContainer, addProfiles, "AddProfiles").commit();
				isInAddProfileScreen = true;
                break;

            case R.id.settingsModifyExistingProfilesButton:

                break;

            case R.id.settingsDeleteProfilesButton:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        switch (settingsViewFlipper.getDisplayedChild()){
            case 0:
                finish();
                break;

            default:
				savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
				if (savingAndLoading.loadBoolean(this, "hasSavedProfile")){
					settingsViewFlipper.setDisplayedChild(0);
				}else{
					manager.beginTransaction().remove(addProfiles).commit();
					settingsViewFlipper.setVisibility(View.VISIBLE);
					if (isInAddProfileScreen){
						settingsViewFlipper.setDisplayedChild(1);
						isInAddProfileScreen = false;
					}else{
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
	
}
