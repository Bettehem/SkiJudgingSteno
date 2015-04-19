package com.bettehem.skijudgingsteno;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class AddEvent extends ActionBarActivity implements View.OnClickListener{

    SharedPreferencesSavingAndLoading savingAndLoading;
    String eventType, profileName;
    Intent intent;
    TextView addingEventText, newEventAddInfoTextView;
    ViewFlipper addEventViewFlipper;
    Button addProfileInEventScreen, saveProfile;
    String[] profiles;
    EditText profileNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        variables();
        startup();
    }

    public void startup(){
        savingAndLoading.preferenceFilename = "Settings";
        if (!savingAndLoading.loadBoolean(this, "hasCreatedEvents")){
            addingEventText.setText(getString(R.string.creatingfirsteventtext));

        }

        if (eventType.contentEquals("skiSlopestyle")){

        }else{

        }
    }

    public void variables(){
        intents();
        strings();
        sharedPreferences();
        textViews();
        buttons();
        viewFlippers();
        editTexts();
    }

    public void intents(){
        intent = getIntent();
    }

    public void strings(){
        eventType = intent.getExtras().getString("eventType");
    }

    public void sharedPreferences(){
        savingAndLoading = new SharedPreferencesSavingAndLoading();
    }

    public void textViews(){
        addingEventText = (TextView) findViewById(R.id.addingEventText);
        newEventAddInfoTextView = (TextView) findViewById(R.id.newEventAddInfoTextView);
    }

    public void buttons(){
        addProfileInEventScreen = (Button) findViewById(R.id.addProfileInEventScreenButton);
        saveProfile = (Button) findViewById(R.id.saveProfileButton);

        addProfileInEventScreen.setOnClickListener(this);
        saveProfile.setOnClickListener(this);
    }

    public void viewFlippers(){
        addEventViewFlipper = (ViewFlipper) findViewById(R.id.addEventViewFlipper);
    }

    public void editTexts(){
        profileNameEditText = (EditText) findViewById(R.id.profileNameEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addProfileInEventScreenButton:

                switch (addEventViewFlipper.getDisplayedChild()){
                    case 0:
                        addEventViewFlipper.setVisibility(View.VISIBLE);
                        addEventViewFlipper.setDisplayedChild(0);
                        addProfileInEventScreen.setVisibility(View.GONE);
                        break;

                    case 1:
                        addEventViewFlipper.setVisibility(View.VISIBLE);
                        addEventViewFlipper.setDisplayedChild(1);
                        addProfileInEventScreen.setVisibility(View.GONE);
                        String[] lel = savingAndLoading.loadStringArray(this, "Profiles");
                        newEventAddInfoTextView.setText(lel.toString());
                        break;
                }

                break;

            case R.id.saveProfileButton:
                profileName = profileNameEditText.getText().toString();
                profiles = new String[]{profileName};

                savingAndLoading.preferenceFilename = "Profiles";
                savingAndLoading.saveStringArray(this, "profile_list", profiles);

                Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show();
                addEventViewFlipper.setVisibility(View.GONE);
                addEventViewFlipper.setDisplayedChild(1);
                addProfileInEventScreen.setText("Create new event");
                addProfileInEventScreen.setVisibility(View.VISIBLE);
                break;
        }
    }

}
