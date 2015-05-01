package com.bettehem.skijudgingsteno;
import android.content.*;
import android.widget.*;

//This class requires SharedPreferencesSavingAndLoading to function correctly
public class SavingAndLoadingProfiles
{
	public String profileDetailsFileName = "Profiles";
	public String profileListName = "profile_list";
	
	private String eventTypeKey = "event_type";
	private String competitorsUseKey = "competitors_use";
	
	SharedPreferencesSavingAndLoading savingAndLoading = new SharedPreferencesSavingAndLoading();
	
	public void addProfile(Context context, String profileName, String eventType, String whatCompetitorsUse){
		if (profileName.contentEquals(profileDetailsFileName) || profileName.contentEquals(savingAndLoading.preferenceFilename) || profileName.contentEquals("")){
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


			Toast.makeText(context, context.getString(R.string.profile_saved_text), Toast.LENGTH_SHORT).show();
			
		}
	}
	
	
	public String[] loadProfile(Context context, String profileName){
		savingAndLoading.preferenceFilename = profileName;
		String eventType = savingAndLoading.loadString(context, eventTypeKey);
		String competitorsUse = savingAndLoading.loadString(context, competitorsUseKey);
		return new String[]{eventType, competitorsUse};
	}
	
	
	public void deleteProfile(Context context, String profileName){
		
	}
}
