package com.bettehem.skijudgingsteno;
import android.app.*;
import com.google.android.gms.analytics.*;

public class SkiJudgingStenoTracker extends Application
{
	public static GoogleAnalytics analytics;
	public static Tracker tracker;

	@Override
	public void onCreate() {
		analytics = GoogleAnalytics.getInstance(this);
		analytics.setLocalDispatchPeriod(1800);

		tracker = analytics.newTracker("UA-64258106-1"); 
		tracker.enableExceptionReporting(true);
		tracker.enableAdvertisingIdCollection(true);
		tracker.enableAutoActivityTracking(true);
	}
}
