package ua.in.leopard.androidCoocooAfisha;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class CinemaInfo extends MainActivity{
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
	        		setTitle(cinema_main.getTitle());
	        		
	        		TextView cinema_orig_title = (TextView)findViewById(R.id.cinema_orig_title);
	        		if (cinema_orig_title != null){
	        			cinema_orig_title.setText(Html.fromHtml(cinema_main.getOrigTitle()));
	        		}
	        		
	        		if (cinema_main.getYear() != null && Integer.parseInt(cinema_main.getYear()) != 0){
	        			TextView cinema_year = (TextView)findViewById(R.id.cinema_year);
	        			if (cinema_year != null){
	        				cinema_year.setText(cinema_main.getYear());
	        			}
	        		}
	        		
	        		TextView cinema_description = (TextView)findViewById(R.id.cinema_description);
        		    cinema_description.setText(Html.fromHtml(cinema_main.getDescription()));
        		    
        		    String cinema_casts_data = cinema_main.getCasts();
        		    TextView cinema_casts = (TextView)findViewById(R.id.cinema_casts);
        		    if (cinema_casts_data.length() == 0){
        			  cinema_casts.setText(getString(R.string.cinema_casts_not_set));
        		    } else {
        			  cinema_casts.setText(cinema_casts_data);
        		    }
	        	}
	      }
	   }
}
