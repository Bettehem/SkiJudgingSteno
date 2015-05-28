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
import android.content.*;
import android.widget.*;
import java.util.*;

//This class requires SharedPreferencesSavingAndLoading to function correctly
public class SavingAndLoadingProfiles
{
	public String profileDetailsFileName = "Profiles";
	public String profileListName = "profile_list";
	public final String originalProfileDetailsFileName = "Profiles";

	private static final String eventTypeKey = "event_type";
	private static final String competitorsUseKey = "competitors_use";
	private static final String eventLocationKey = "event_location";
	private boolean isInvalidProfileName = false;

	SharedPreferencesSavingAndLoading savingAndLoading = new SharedPreferencesSavingAndLoading();

	public boolean addProfile(Context context, String profileName, String eventType, String whatCompetitorsUse, String eventLocation)
	{
		SavingAndLoadingEvents savingAndLoadingEvents = new SavingAndLoadingEvents();
		savingAndLoading.preferenceFilename = "Settings";
		if (profileName.contentEquals(profileDetailsFileName) || profileName.contentEquals(savingAndLoading.preferenceFilename) || profileName.contentEquals("") || profileName.contentEquals(savingAndLoadingEvents.eventDetailsFileName)){
			Toast.makeText(context, context.getString(R.string.invalid_profile_name), Toast.LENGTH_LONG).show();
			isInvalidProfileName = true;
		}else{
			savingAndLoading.preferenceFilename = profileDetailsFileName;
			if (Arrays.asList(savingAndLoading.loadStringArray(context, profileListName)).contains(profileName)){

				Toast.makeText(context, context.getString(R.string.invalid_profile_name), Toast.LENGTH_LONG).show();
				isInvalidProfileName = true;
			}else{

				savingAndLoading.preferenceFilename = savingAndLoadingEvents.eventDetailsFileName;
				if (Arrays.asList(savingAndLoading.loadStringArray(context, savingAndLoadingEvents.eventListName)).contains(profileName)){
					Toast.makeText(context, context.getString(R.string.invalid_profile_name), Toast.LENGTH_LONG).show();
					isInvalidProfileName = true;
				}else{

					savingAndLoading.preferenceFilename = profileDetailsFileName;
					String existingProfiles = savingAndLoading.loadString(context, profileListName);

					String[] newProfile;
					if (existingProfiles.contentEquals("Error! Not Found!")){
						newProfile = new String[]{profileName};
					}else{
						newProfile = new String[]{existingProfiles + profileName};
					}
					savingAndLoading.saveStringArray(context, profileListName, newProfile);



					savingAndLoading.preferenceFilename = profileName;
					savingAndLoading.saveString(context, eventTypeKey, eventType);
					savingAndLoading.saveString(context, competitorsUseKey, whatCompetitorsUse);
					savingAndLoading.saveString(context, eventLocationKey, eventLocation);


					Toast.makeText(context, context.getString(R.string.profile_saved_text), Toast.LENGTH_SHORT).show();
					isInvalidProfileName = false;
				}
			}
		}
		return isInvalidProfileName;
	}


	public String[] loadProfile(Context context, String profileName)
	{
		savingAndLoading.preferenceFilename = profileName;
		String eventType = savingAndLoading.loadString(context, eventTypeKey);
		String competitorsUse = savingAndLoading.loadString(context, competitorsUseKey);
		String eventLocation = savingAndLoading.loadString(context, eventLocationKey);
		return new String[]{eventType, competitorsUse, eventLocation};
	}


	public void deleteProfile(Context context, String profileName)
	{
		savingAndLoading.preferenceFilename = profileName;
		savingAndLoading.deleteAllValues(context);
		CharSequence charSequence = profileName + ",";
		String profileList = savingAndLoading.loadString(context, profileListName);
		String newProfileList = profileList.replace(charSequence, "");
		savingAndLoading.saveString(context, profileListName, profileList);
	}
}
