package com.bettehem.skijudgingsteno;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;


public class Settings extends ActionBarActivity implements View.OnClickListener{

    //Buttons for settings_options.xml
    private Button profileSettingsButton;

    //Buttons for profile_settings.xml
    private Button settingsAddProfileButton, settingsModifyExistingProfilesButton;

    //ViewFlipper that switches between different settings
    private ViewFlipper settingsViewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        variables();

    }

    private void variables(){
        buttons();
        viewFlippers();
    }

    private void buttons(){
        profileSettingsButton = (Button) findViewById(R.id.settingsProfileSettingsButton);

        settingsAddProfileButton = (Button) findViewById(R.id.settingsAddProfileButton);
        settingsModifyExistingProfilesButton = (Button) findViewById(R.id.settingsModifyExistingProfilesButton);

        profileSettingsButton.setOnClickListener(this);

        settingsAddProfileButton.setOnClickListener(this);
        settingsModifyExistingProfilesButton.setOnClickListener(this);
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

                break;

            case R.id.settingsModifyExistingProfilesButton:

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
}
