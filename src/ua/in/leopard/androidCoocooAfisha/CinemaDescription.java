package ua.in.leopard.androidCoocooAfisha;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class CinemaDescription extends Activity{
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.cinema_description);
	      
	      Bundle extras = getIntent().getExtras();
          String cinema_title = "";
          String cinema_content = "";
          if(extras != null) {
        	  cinema_title = extras.getString("cinema_title");
        	  cinema_content = extras.getString("cinema_content");
          }
	      
	      setTitle(cinema_title);
	      
  		  TextView cinema_description = (TextView)findViewById(R.id.cinema_description);
  		  cinema_description.setText(Html.fromHtml(cinema_content));
	   }
}
