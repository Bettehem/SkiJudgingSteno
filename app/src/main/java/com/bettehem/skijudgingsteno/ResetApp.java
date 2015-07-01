package com.bettehem.skijudgingsteno;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
public class ResetApp {

    private SharedPreferencesSavingAndLoading savingAndLoading = new SharedPreferencesSavingAndLoading();
    private SavingAndLoadingProfiles savingAndLoadingProfiles = new SavingAndLoadingProfiles();
    private SavingAndLoadingEvents savingAndLoadingEvents = new SavingAndLoadingEvents();

    private int profileAmount, profileFileAmount, eventAmount, eventFileAmount, settingsFileAmount, totalFilesToDelete;
    private ProgressDialog progressDialog;

    public void resetAppData(Context context){

        calculateData(context);

        showProgressDialog(context);

        new DeleteData().execute(context);
    }

    private void calculateData(Context context){
        savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
        if (savingAndLoading.loadString(context, savingAndLoadingProfiles.profileListName).contentEquals("")){
            profileAmount = 0;
        }else{
            profileAmount = savingAndLoading.loadStringArray(context, savingAndLoadingProfiles.profileListName).length;
        }
        if (savingAndLoading.checkIfFileExists(context)){
            profileFileAmount = 1;
        }

        savingAndLoading.preferenceFilename = savingAndLoadingEvents.originalEventDetailsFilename;
        if (savingAndLoading.loadString(context, savingAndLoadingEvents.eventListName).contentEquals("")){
            eventAmount = 0;
        }else{
            eventAmount = savingAndLoading.loadStringArray(context, savingAndLoadingEvents.eventListName).length;
        }
        if (savingAndLoading.checkIfFileExists(context)){
            eventFileAmount = 1;
        }

        savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        if (savingAndLoading.checkIfFileExists(context)){
            settingsFileAmount = 1;
        }

        totalFilesToDelete = profileAmount + profileFileAmount + eventAmount + eventFileAmount + settingsFileAmount;
    }

    private void showProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Resetting app data...");
        progressDialog.setProgress(0);
        progressDialog.setMax(totalFilesToDelete);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private class DeleteData extends AsyncTask<Context, Integer, Boolean>{
        @Override
        protected Boolean doInBackground(Context... params) {

            savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
            if (savingAndLoading.checkIfFileExists(params[0])){
                profileDeletion(params[0]);
            }

            savingAndLoading.preferenceFilename = savingAndLoadingEvents.originalEventDetailsFilename;
            if (savingAndLoading.checkIfFileExists(params[0])){
                eventDeletion(params[0]);
            }

            savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
            if (savingAndLoading.checkIfFileExists(params[0])){
                settingsDeletion(params[0]);
            }

            return null;
        }

        private void profileDeletion(Context context){
            savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
            while (!savingAndLoading.loadString(context, savingAndLoadingProfiles.profileListName).contentEquals("")){
                String[] profileList = savingAndLoading.loadStringArray(context, savingAndLoadingProfiles.profileListName);
                savingAndLoadingProfiles.deleteProfile(context, profileList[profileList.length-1]);

                publishProgress(1);

            }

            savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
            savingAndLoading.deleteAllValues(context);

            publishProgress(1);

        }

        private void eventDeletion(Context context){
            savingAndLoading.preferenceFilename = savingAndLoadingEvents.originalEventDetailsFilename;
            while (!savingAndLoading.loadString(context, savingAndLoadingEvents.eventListName).contentEquals("")){
                String[] eventList = savingAndLoading.loadStringArray(context, savingAndLoadingEvents.eventListName);
                savingAndLoadingEvents.deleteEvent(context, eventList[eventList.length-1]);
                publishProgress(1);
            }

            savingAndLoading.preferenceFilename = savingAndLoadingEvents.originalEventDetailsFilename;
            savingAndLoading.deleteAllValues(context);
            publishProgress(1);
        }

        private void settingsDeletion(Context context){
            savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
            savingAndLoading.deleteAllValues(context);

            publishProgress(1);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.incrementProgressBy(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
        }
    }

}
