package com.bettehem.skijudgingsteno;

import android.content.Context;

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

    public void resetAppdata(Context context){
        profileDeletion(context);
    }

    private void profileDeletion(Context context){
        savingAndLoading.preferenceFilename = savingAndLoadingProfiles.originalProfileDetailsFileName;
        String[] profileList = savingAndLoading.loadStringArray(context, savingAndLoadingProfiles.profileListName);
        while (!savingAndLoading.loadString(context, savingAndLoadingProfiles.profileListName).contentEquals("")){
            savingAndLoadingProfiles.deleteProfile(context, profileList[profileList.length]);
        }
    }
}
