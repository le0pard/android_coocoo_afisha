package ua.in.leopard.androidCoocooAfisha;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class CinemaDescription extends Activity{
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.cinema_description);
	      
	      Bundle extras = getIntent().getExtras();
          String cinema_title = "";
          String cinema_content = "";
          String cinema_casts_data = "";
          if(extras != null) {
        	  cinema_title = extras.getString("cinema_title");
        	  cinema_content = extras.getString("cinema_content");
        	  cinema_casts_data = extras.getString("cinema_casts");
          }
	      
	      setTitle(cinema_title);
	      
  		  TextView cinema_description = (TextView)findViewById(R.id.cinema_description);
  		  cinema_description.setText(Html.fromHtml(cinema_content));
  		  
  		  
  		  TextView cinema_casts = (TextView)findViewById(R.id.cinema_casts);
  		  cinema_casts.setMovementMethod(new ScrollingMovementMethod());
  		  if (cinema_casts_data.length() == 0){
  			  cinema_casts.setText(getString(R.string.cinema_casts_not_set));
  		  } else {
  			  cinema_casts.setText(cinema_casts_data);
  		  }
	   }
}
