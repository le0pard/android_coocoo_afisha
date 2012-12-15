package ua.in.leopard.androidCoocooAfisha;

import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class CinemaInfo extends MainActivity {
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.cinema_info);
	      
	      Bundle extras = getIntent().getExtras();
	      int cinema_id = 0;
	      if(extras != null) {
	        cinema_id = extras.getInt("cinema_id", 0);
	      }
	      
	      if (cinema_id != 0){
	        	DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
	        	CinemaDB cinema_main = DatabaseHelperObject.getCinema(cinema_id);
	        	if (cinema_main != null){
	        		setTitle(Html.fromHtml(cinema_main.getTitle()));
	        		
	        		TextView cinema_orig_title = (TextView)findViewById(R.id.cinema_orig_title);
	        		if (cinema_orig_title != null){
	        			if (cinema_main.getOrigTitle() != null && cinema_main.getOrigTitle().length() > 0){
	        				cinema_orig_title.setText(Html.fromHtml(cinema_main.getOrigTitle()));
	        			} else {
	        				cinema_orig_title.setText(R.string.not_set);
	        			}
	        		}
	        		
	        		TextView cinema_year = (TextView)findViewById(R.id.cinema_year);
	        		if (cinema_year != null){
		        		if (cinema_main.getYear() != null && Integer.parseInt(cinema_main.getYear()) > 0){
		        			cinema_year.setText(cinema_main.getYear());
		        		} else {
		        			cinema_year.setText(R.string.not_set);
		        		}
	        		}
	        		
	        		TextView cinema_description = (TextView)findViewById(R.id.cinema_description);
	        		if (cinema_main.getDescription() != null && cinema_main.getDescription().length() > 0){
	        			cinema_description.setText(Html.fromHtml(cinema_main.getDescription()));
	        		} else {
	        			cinema_description.setText(R.string.not_set);
	        		}
        		    
        		    String cinema_casts_data = cinema_main.getCasts();
        		    TextView cinema_casts = (TextView)findViewById(R.id.cinema_casts);
        		    if (cinema_casts_data != null && cinema_casts_data.length() > 0){
        		    	cinema_casts.setText(cinema_casts_data);
        		    } else {
        		    	cinema_casts.setText(R.string.not_set);
        		    }
	        	}
	      }
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
