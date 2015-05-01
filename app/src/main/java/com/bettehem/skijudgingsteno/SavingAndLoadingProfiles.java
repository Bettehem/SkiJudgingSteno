package com.bettehem.skijudgingsteno;
import android.content.*;
import android.widget.*;

//This class requires SharedPreferencesSavingAndLoading to function correctly
public class SavingAndLoadingProfiles
{
	public String profileDetailsFileName = "Profiles";
	public String profileListName = "profile_list";
	
	private static final String eventTypeKey = "event_type";
	private static final String competitorsUseKey = "competitors_use";
	private static final String eventLocationKey = "event_location";
	
	SharedPreferencesSavingAndLoading savingAndLoading = new SharedPreferencesSavingAndLoading();
	SavingAndLoadingEvents savingAndLoadingEvents = new SavingAndLoadingEvents();
	
	public void addProfile(Context context, String profileName, String eventType, String whatCompetitorsUse, String eventLocation){
		if (profileName.contentEquals(profileDetailsFileName) || profileName.contentEquals(savingAndLoading.preferenceFilename) || profileName.contentEquals("") || profileName.contentEquals(savingAndLoadingEvents.eventDetailsFileName)){
			Toast.makeText(context, context.getString(R.string.invalid_profile_name), Toast.LENGTH_LONG).show();
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
			
		}
	}
	
	
	public String[] loadProfile(Context context, String profileName){
		savingAndLoading.preferenceFilename = profileName;
		String eventType = savingAndLoading.loadString(context, eventTypeKey);
		String competitorsUse = savingAndLoading.loadString(context, competitorsUseKey);
		String eventLocation = savingAndLoading.loadString(context, eventLocationKey);
		return new String[]{eventType, competitorsUse, eventLocation};
	}
	
	
	public void deleteProfile(Context context, String profileName){
		savingAndLoading.preferenceFilename = profileName;
		savingAndLoading.deleteAllValues(context);
		CharSequence charSequence = profileName + ",";
		String profileList = savingAndLoading.loadString(context, profileListName);
		profileList.replace(charSequence, "");
		savingAndLoading.saveString(context, profileListName, profileList);
	}
}
