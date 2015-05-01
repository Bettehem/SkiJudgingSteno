package com.bettehem.skijudgingsteno;

import android.content.Context;
import android.widget.Toast;

public class SavingAndLoadingEvents {
    public String eventDetailsFileName = "Events";
    public String eventListName = "event_list";

    private static final String eventTypeKey = "event_type";
    private static final String competitorsUseKey = "competitors_use";
    private static final String eventLocationKey = "event_location";

    SharedPreferencesSavingAndLoading savingAndLoading = new SharedPreferencesSavingAndLoading();
    SavingAndLoadingProfiles savingAndLoadingProfiles = new SavingAndLoadingProfiles();

    public void saveEvent(Context context, String eventName, String eventType, String whatCompetitorsUse, String eventLocation){
        if (eventName.contentEquals(eventDetailsFileName) || eventName.contentEquals(savingAndLoading.preferenceFilename) || eventName.contentEquals("") || eventName.contentEquals(savingAndLoadingProfiles.profileDetailsFileName)){
            Toast.makeText(context, context.getString(R.string.invalid_profile_name), Toast.LENGTH_LONG).show();
        }else{

            savingAndLoading.preferenceFilename = eventDetailsFileName;
            String existingProfiles = savingAndLoading.loadString(context, eventListName);

            String[] newEvent;
            if (existingProfiles.contentEquals("Error! Not Found!")){
                newEvent = new String[]{eventName};
            }else{
                newEvent = new String[]{existingProfiles + eventName};
            }
            savingAndLoading.saveStringArray(context, eventListName, newEvent);



            savingAndLoading.preferenceFilename = eventName;
            savingAndLoading.saveString(context, eventTypeKey, eventType);
            savingAndLoading.saveString(context, competitorsUseKey, whatCompetitorsUse);
            savingAndLoading.saveString(context, eventLocationKey, eventLocation);


            Toast.makeText(context, context.getString(R.string.event_saved_text), Toast.LENGTH_SHORT).show();

        }
    }
}
