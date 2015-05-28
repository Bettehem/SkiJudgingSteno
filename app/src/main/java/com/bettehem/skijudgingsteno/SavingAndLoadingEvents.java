package com.bettehem.skijudgingsteno;

import android.content.Context;
import android.widget.Toast;
import java.util.*;

public class SavingAndLoadingEvents {
    public String eventDetailsFileName = "Events";
    public String eventListName = "event_list";

    public static final String originalEventDetailsFilename = "Events";

    private static final String eventTypeKey = "event_type";
    private static final String competitorsUseKey = "competitors_use";
    private static final String eventLocationKey = "event_location";
	private boolean isInvalidEventName = false;

    SharedPreferencesSavingAndLoading savingAndLoading = new SharedPreferencesSavingAndLoading();

    public boolean saveEvent(Context context, String eventName, String eventType, String whatCompetitorsUse, String eventLocation){
		SavingAndLoadingProfiles savingAndLoadingProfiles = new SavingAndLoadingProfiles();
		savingAndLoading.preferenceFilename = savingAndLoading.originalPreferenceFilename;
        if (eventName.contentEquals(eventDetailsFileName) || eventName.contentEquals(savingAndLoading.preferenceFilename) || eventName.contentEquals("") || eventName.contentEquals(savingAndLoadingProfiles.profileDetailsFileName)){
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
}
