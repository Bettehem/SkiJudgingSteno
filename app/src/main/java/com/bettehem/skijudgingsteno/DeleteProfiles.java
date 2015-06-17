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
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class DeleteProfiles extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Spinner deleteProfileProfileSelectionListSpinner;
    private TextView deleteProfileEventTypeTextView, deleteProfileWhatCompetitorsUseTextView, deleteProfileEventLocationTextView;
    private Button deleteProfileConfirmationButton;
    private DeletingProfiles deletingProfiles;
    private Activity userActivity;
    private View fragmentView;
    private SharedPreferencesSavingAndLoading savingAndLoading;
    private SavingAndLoadingProfiles savingAndLoadingProfiles;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        deletingProfiles = (DeletingProfiles) activity;
        userActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.delete_profile, container, false);
        variables();
        return fragmentView;
    }

    private void variables(){
        sharedPreferences();
        profileSaverAndLoader();
        spinners();
        textViews();
        buttons();
    }

    private void sharedPreferences(){
        savingAndLoading = new SharedPreferencesSavingAndLoading();
    }

    private void profileSaverAndLoader() {
        savingAndLoadingProfiles = new SavingAndLoadingProfiles();
    }

    private void spinners(){

    }

    private void textViews(){

    }

    private void buttons(){

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    interface DeletingProfiles{
        void onProfileDeleted(boolean profileDeleted);
    }
}
