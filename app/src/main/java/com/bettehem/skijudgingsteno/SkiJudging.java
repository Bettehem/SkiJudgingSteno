package com.bettehem.skijudgingsteno;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class SkiJudging extends ActionBarActivity implements View.OnClickListener{

    Button skiJudgingSlopestyle, skiJudgingHalfPipe;
    Intent openSkiSlopestyleEvent, openSkiHalfPipeEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ski_judging);
        variables();
    }

    public void variables(){
        intents();
        buttons();
    }

    public void intents(){
        openSkiSlopestyleEvent = new Intent(this, SkiSlopestyleEvent.class);
    }

    public void buttons(){
        skiJudgingSlopestyle = (Button) findViewById(R.id.skiJudgingSlopestyleButton);
        skiJudgingHalfPipe = (Button) findViewById(R.id.skiJudgingHalfPipeButton);

        skiJudgingSlopestyle.setOnClickListener(this);
        skiJudgingHalfPipe.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ski_judging, menu);
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
            case R.id.skiJudgingSlopestyleButton:
                startActivity(openSkiSlopestyleEvent);
                finish();
                break;
            case R.id.skiJudgingHalfPipeButton:
                Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
