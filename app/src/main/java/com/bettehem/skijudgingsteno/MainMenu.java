package com.bettehem.skijudgingsteno;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;


public class MainMenu extends ActionBarActivity implements View.OnClickListener{

    SharedPreferencesSavingAndLoading savingAndLoading;
    boolean isTutorialCompleted;
    Button mainMenuSkiJudgingButton, mainMenuSnowboardJudgingButton, mainMenuSettingsButton;
    Intent openTutorial, openSkiJudging, openSnowboardJudging, openSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        variables();
    }

    public void variables(){
        intents();
        sharedPreferences();
        buttons();

    }

    public void intents(){
        openTutorial = new Intent(this, Tutorial.class);
    }

    public void sharedPreferences(){
        savingAndLoading = new SharedPreferencesSavingAndLoading();
        savingAndLoading.preferenceFilename = "Settings";
        savingAndLoading.saveBoolean(this, "hasCreatedEvents", false);
        isTutorialCompleted = savingAndLoading.loadBoolean(this, "isTutorialCompleted");
        if (!isTutorialCompleted){
            startActivity(openTutorial);
        }
    }

    public void buttons(){
        mainMenuSkiJudgingButton = (Button) findViewById(R.id.mainMenuSkiJudgingButton);
        mainMenuSnowboardJudgingButton = (Button) findViewById(R.id.mainMenuSnowboardJudgingButton);
        mainMenuSettingsButton = (Button) findViewById(R.id.mainmenuAppSettingsButton);

        mainMenuSkiJudgingButton.setOnClickListener(this);
        mainMenuSnowboardJudgingButton.setOnClickListener(this);
        mainMenuSettingsButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
            case R.id.mainMenuSkiJudgingButton:

                break;
            case R.id.mainMenuSnowboardJudgingButton:

                break;
            case R.id.mainmenuAppSettingsButton:

                break;
        }
    }
}
