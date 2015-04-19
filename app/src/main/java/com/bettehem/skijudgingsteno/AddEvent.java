package com.bettehem.skijudgingsteno;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class AddEvent extends ActionBarActivity implements View.OnClickListener{

    SharedPreferencesSavingAndLoading savingAndLoading;
    String eventType;
    Intent intent;
    TextView addingEventText;
    ViewFlipper addEventViewFlipper;
    Button addProfileInEventScreen;

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
    }

    public void buttons(){
        addProfileInEventScreen = (Button) findViewById(R.id.addProfileInEventScreenButton);

        addProfileInEventScreen.setOnClickListener(this);
    }

    public void viewFlippers(){
        addEventViewFlipper = (ViewFlipper) findViewById(R.id.addEventViewFlipper);
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
                addEventViewFlipper.setVisibility(View.VISIBLE);
                addEventViewFlipper.setDisplayedChild(0);
                addProfileInEventScreen.setVisibility(View.GONE);
                break;
        }
    }
}
