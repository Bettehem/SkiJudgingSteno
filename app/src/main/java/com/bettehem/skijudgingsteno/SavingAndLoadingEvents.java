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
import android.content.Context;
import android.widget.Toast;
import java.util.*;

public class SavingAndLoadingEvents {
    public String eventDetailsFileName = "Events";
    public String eventListName = "event_list";
    public final String originalEventDetailsFilename = "Events";

    private static final String eventTypeKey = "event_type";
    private static final String competitorsUseKey = "competitors_use";
    private static final String eventLocationKey = "event_location";
	private boolean isInvalidEventName = true;

    SharedPreferencesSavingAndLoading savingAndLoading = new SharedPreferencesSavingAndLoading();

    public boolean saveEvent(Context context, String eventName, String eventType, String whatCompetitorsUse, String eventLocation){
		SavingAndLoadingProfiles savingAndLoadingProfiles = new SavingAndLoadingProfiles();
		savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        if (eventName.contentEquals(eventDetailsFileName) || eventName.contentEquals(savingAndLoading.preferenceFilename) || eventName.contentEquals("") || eventName.contentEquals(savingAndLoadingProfiles.profileDetailsFileName) || eventName.contains(",")){
            Toast.makeText(context, context.getString(R.string.invalid_event_name), Toast.LENGTH_LONG).show();
			isInvalidEventName = true;
        }else{

            savingAndLoading.preferenceFilename = eventDetailsFileName;
            if (Arrays.asList(savingAndLoading.loadStringArray(context, eventListName)).contains(eventName)){
                Toast.makeText(context, context.getString(R.string.invalid_event_name), Toast.LENGTH_LONG).show();
                isInvalidEventName = true;
            }else{

                savingAndLoading.preferenceFilename = savingAndLoadingProfiles.profileDetailsFileName;
                if (Arrays.asList(savingAndLoading.loadStringArray(context, savingAndLoadingProfiles.profileListName)).contains(eventName)){
                    Toast.makeText(context, context.getString(R.string.invalid_event_name), Toast.LENGTH_LONG).show();
                    isInvalidEventName = true;
                }else{
                    savingAndLoading.preferenceFilename = eventDetailsFileName;
                    String existingEvents = savingAndLoading.loadString(context, eventListName);

                    String[] newEvent;
                    if (existingEvents.contentEquals("Error! Not Found!")){
                        newEvent = new String[]{eventName};
                    }else{
                        newEvent = new String[]{existingEvents + eventName};
                    }
                    savingAndLoading.saveStringArray(context, eventListName, newEvent);



                    savingAndLoading.preferenceFilename = eventName;
                    savingAndLoading.saveString(context, eventTypeKey, eventType);
                    savingAndLoading.saveString(context, competitorsUseKey, whatCompetitorsUse);
                    savingAndLoading.saveString(context, eventLocationKey, eventLocation);


                    Toast.makeText(context, context.getString(R.string.event_saved_text), Toast.LENGTH_SHORT).show();
                    isInvalidEventName = false;
                }

            }

        }
		return isInvalidEventName;
    }

    public void deleteEvent(Context context, String eventName)
    {
        savingAndLoading.preferenceFilename = eventName;
        savingAndLoading.deleteAllValues(context);
        CharSequence charSequence = eventName + ",";
        savingAndLoading.preferenceFilename = originalEventDetailsFilename;
        String profileList = savingAndLoading.loadString(context, eventListName);
        String newProfileList = profileList.replace(charSequence, "");
        savingAndLoading.saveString(context, eventListName, newProfileList);
    }
}
