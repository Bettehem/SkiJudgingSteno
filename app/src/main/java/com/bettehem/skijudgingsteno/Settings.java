package com.bettehem.skijudgingsteno;

import android.content.Intent;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;


public class Settings extends ActionBarActivity implements View.OnClickListener, AddProfiles.AddingProfiles
{
    //Intents
    private Intent openAddProfile;

    //Buttons for settings_options.xml
    private Button profileSettingsButton;

    //Buttons for profile_settings.xml
    private Button settingsAddProfileButton, settingsModifyExistingProfilesButton, settingsDeleteProfilesButton;

    //ViewFlipper that switches between different settings
    private ViewFlipper settingsViewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        variables();

    }

    private void variables(){
        intents();
        buttons();
        viewFlippers();
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
                FragmentManager manager = getFragmentManager();
                AddProfiles addProfiles = new AddProfiles();
				addProfiles.setRestrictions(this, true, null, null);
                manager.beginTransaction().add(R.id.addProfileContainer, addProfiles, "AddProfiles").commit();
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
                settingsViewFlipper.setDisplayedChild(0);
                break;
        }
    }
	
	
	@Override
	public void onProfileSaved(boolean isInvalidProfilename, String eventType, String competitorsUse, String EventLocation)
	{
		
	}

	@Override
	public void onEventTypeSelected(boolean isAllowedEventType)
	{
		
	}

	@Override
	public void onCompetitorsUseSelected(boolean isAllowedCompetitorsUseType)
	{
		
	}
	
}
