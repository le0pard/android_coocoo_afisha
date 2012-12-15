package ua.in.leopard.androidCoocooAfisha;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.os.Bundle;

public class About extends Activity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.about);
   }
   
	
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
