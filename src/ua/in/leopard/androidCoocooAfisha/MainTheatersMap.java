package ua.in.leopard.androidCoocooAfisha;

import com.google.analytics.tracking.android.EasyTracker;

public class MainTheatersMap extends TheatersMap{
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this); // Add this method.
	}
	  
	@Override
	public void onStop() {
		super.onStop();
	    EasyTracker.getInstance().activityStop(this); // Add this method.
	}
}
