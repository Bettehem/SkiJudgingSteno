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


public class AddEvent extends ActionBarActivity implements View.OnClickListener{

    SharedPreferencesSavingAndLoading savingAndLoading;
    String eventType;
    Intent intent;
    TextView addingEventText;
    Button createProfile;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = (RelativeLayout) findViewById(R.id.testi);
        setContentView(layout);
        variables();
        startup();
    }

    public void startup(){
        savingAndLoading.preferenceFilename = "Settings";
        if (!savingAndLoading.loadBoolean(this, "hasCreatedEvents")){
            addingEventText.setText(getString(R.string.creatingfirsteventtext));
            createProfileButton();
        }

        if (eventType.contentEquals("skiSlopestyle")){

        }else{

        }
    }

    public void createProfileButton(){
        createProfile = new Button(this);
        createProfile.setText(getString(R.string.createnewprofilebutton));
        layout.addView(createProfile);
    }

    public void variables(){
        intents();
        strings();
        sharedPreferences();
        textViews();
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

    }
}
