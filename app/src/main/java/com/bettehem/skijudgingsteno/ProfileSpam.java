package com.bettehem.skijudgingsteno;
import android.content.Context;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class ProfileSpam{
	SavingAndLoadingProfiles savingAndLoadingProfiles = new SavingAndLoadingProfiles();
	
	private ProgressDialog progressDialog;
	private int amountOfProfilesToAdd = 1000;
	private ProfileSpamming profileSpamming;
	private String spamProfileName;
	private String spamEventType;
	private String spamCompetitorsUse;
	private String spamEventLocation;
	private Context spamContext;
	
	public void spamProfiles(Context context, String profileName, String eventType, String competitorsUse, String eventLocation){
		spamProfileName = profileName;
		spamEventType = eventType;
		spamCompetitorsUse = competitorsUse;
		spamEventLocation = eventLocation;
		spamContext = context;
		
		profileSpamming = (ProfileSpamming) context;
		showProgressDialog(context);
		
		new MultipleProfiles().execute(context);
	}
	
	private void showProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Adding Profiles...");
        progressDialog.setProgress(0);
        progressDialog.setMax(amountOfProfilesToAdd);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
	
	private class MultipleProfiles extends AsyncTask<Context, Integer, Boolean>{
        @Override
        protected Boolean doInBackground(Context... params) {         
			addProfiles(spamContext);
            return null;
        }

		private void addProfiles(Context context){
			for (int i = 0; i < 1000; i++){
				savingAndLoadingProfiles.addProfile(context, spamProfileName + i, spamEventType, spamCompetitorsUse, spamEventLocation);
				publishProgress(i);
			}
		}

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.incrementProgressBy(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
			profileSpamming.onProfileSpammingFinished();
        }
    }
	
	public interface ProfileSpamming{
		void onProfileSpammingFinished();
	}
}
