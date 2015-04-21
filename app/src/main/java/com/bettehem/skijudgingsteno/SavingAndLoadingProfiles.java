package com.bettehem.skijudgingsteno;
import android.content.*;
import android.widget.*;

//This class requires SharedPreferencesSavingAndLoading to function correctly
public class SavingAndLoadingProfiles
{
	public String profileDetailsFileName = "Profiles";
	public String profileListName = "profile_list";
	
	private String eventTypeKey = "event_type";
	
	SharedPreferencesSavingAndLoading savingAndLoading = new SharedPreferencesSavingAndLoading();
	
	public void addProfile(Context context, String profileName, String eventType){
		if (profileName.contentEquals(profileDetailsFileName) || profileName.contentEquals(savingAndLoading.preferenceFilename) || profileName.contentEquals("")){
			Toast.makeText(context, context.getString(R.string.profile_saved_text), Toast.LENGTH_LONG).show();
		}else{

			savingAndLoading.preferenceFilename = profileDetailsFileName;
			String existingProfiles = savingAndLoading.loadString(context, profileListName);
			
			String[] newProfile;
			if (existingProfiles.toString().contentEquals("Error! Not Found!")){
				newProfile = new String[]{profileName};
			}else{
				newProfile = new String[]{existingProfiles + profileName};
			}
			savingAndLoading.saveStringArray(context, profileListName, newProfile);



			savingAndLoading.preferenceFilename = profileName;
			savingAndLoading.saveString(context, eventTypeKey, eventType);


			Toast.makeText(context, context.getString(R.string.profile_saved_text), Toast.LENGTH_SHORT).show();
			
		}
	}
	
	
	public String loadProfile(Context context, String profileName, String eventType){
		
		
		return eventType;
	}
	
	
	public void deleteProfile(Context context, String profileName){
		
	}
}
