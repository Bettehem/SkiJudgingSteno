package com.bettehem.skijudgingsteno;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class SkiSlopestyleEvent extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    SharedPreferencesSavingAndLoading savingAndLoading;
    TextView amountOfJudgedEvents;
    boolean hasOpenedBefore;
    Button newEventButton;
    Intent openNewEvent;
    Spinner skiSlopestyleEventSelectExistingEventSpinner;
    SavingAndLoadingEvents savingAndLoadingEvents;
    TextView skiSlopestyleExistingEventTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ski_slopestyle_event);
        variables();
        startup();
    }

    public void startup(){
		
        savingAndLoading.preferenceFilename = savingAndLoadingEvents.eventDetailsFileName;
        if (savingAndLoading.loadBoolean(this, "hasCreatedEvents")){
            skiSlopestyleExistingEventTextView.setVisibility(View.VISIBLE);
			
			skiSlopestyleEventSelectExistingEventSpinner.setAdapter(new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, savingAndLoading.loadStringArray(this, savingAndLoadingEvents.eventListName)
            ));
            skiSlopestyleEventSelectExistingEventSpinner.setVisibility(View.VISIBLE);
        }
    }

    public void variables(){
        textViews();
        sharedPreferences();
        buttons();
        intents();
        eventSaverAndLoader();
        spinners();
    }

    public void textViews(){
        amountOfJudgedEvents = (TextView) findViewById(R.id.amountOfJudgedEvents);
        skiSlopestyleExistingEventTextView = (TextView) findViewById(R.id.skiSlopestyleExistingEventTextView);
    }

    public void sharedPreferences(){
        savingAndLoading = new SharedPreferencesSavingAndLoading();
        savingAndLoading.preferenceFilename = "SkiSlopestyleEvent";
        hasOpenedBefore = savingAndLoading.loadBoolean(this, "hasOpenedBefore");
        if (!hasOpenedBefore){
            savingAndLoading.saveInt(this, "amountOfJudgedEvents", 0);
        }


        amountOfJudgedEvents.setText(getString(R.string.amountofjudgedeventstext) + savingAndLoading.loadInt(this, "amountOfJudgedEvents"));

    }

    public void buttons(){
        newEventButton = (Button) findViewById(R.id.newEventButton);

        newEventButton.setOnClickListener(this);
    }

    public void intents(){
        openNewEvent = new Intent(this, AddEvent.class);
        openNewEvent.putExtra("eventType", "Slopestyle");
		openNewEvent.putExtra("whatClass", "SkiSlopestyleEvent");
    }

    public void eventSaverAndLoader(){
        savingAndLoadingEvents = new SavingAndLoadingEvents();
    }

    public void spinners(){
        skiSlopestyleEventSelectExistingEventSpinner = (Spinner) findViewById(R.id.skiSlopestyleEventSelectExistingEventSpinner);

        skiSlopestyleEventSelectExistingEventSpinner.setOnItemSelectedListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ski_slopestyle_event, menu);
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
            case R.id.newEventButton:
                startActivity(openNewEvent);
				finish();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.skiSlopestyleEventSelectExistingEventSpinner:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
