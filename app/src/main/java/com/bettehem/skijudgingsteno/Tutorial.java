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
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.*;
import android.widget.*;
import android.view.*;


public class Tutorial extends ActionBarActivity implements View.OnClickListener
{
    Intent restart;
	SharedPreferencesSavingAndLoading savingAndLoading;
	Button tutorialEndingButton;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
		variables();
    }
	
	public void variables(){
		intents();
		sharedPreferences();
		buttons();
	}
	
	public void intents(){
		restart = new Intent(this, Tutorial.class);
	}
	
	public void sharedPreferences(){
		savingAndLoading = new SharedPreferencesSavingAndLoading();
	}
	
	public void buttons(){
		tutorialEndingButton = (Button) findViewById(R.id.tutorialEndingButton);
		
		tutorialEndingButton.setOnClickListener(this);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial, menu);
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
	public void onClick(View v)
	{
		switch (v.getId()){
			case R.id.tutorialEndingButton:
				savingAndLoading.saveBoolean(this, "isTutorialCompleted", true);
				finish();
				break;
		}
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		startActivity(restart);
		finish();
	}
	
}
