package ua.in.leopard.androidCoocooAfisha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class androidCoocooAfisha extends Activity implements OnClickListener {
	private TextView current_city;
	private dataCollector dataCollectorObject;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View cinemasButton = findViewById(R.id.cinemas_button);
        cinemasButton.setOnClickListener(this);
        View theatersButton = findViewById(R.id.theaters_button);
        theatersButton.setOnClickListener(this);
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
             
        dataCollectorObject = new dataCollector(this);
        dataCollectorObject.getTheatersData();
        
        current_city=(TextView)findViewById(R.id.current_city);
        current_city.setText(Html.fromHtml(getString(R.string.current_city_title) + " <b>" + EditPreferences.getCity(this) + "</b>"));
    }
    
    @Override
    protected void onResume() {
       super.onResume();
       current_city.setText(Html.fromHtml(getString(R.string.current_city_title) + " <b>" + EditPreferences.getCity(this) + "</b>"));
    }

    @Override
    protected void onPause() {
       super.onPause();
    }

	@Override
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		  case R.id.cinemas_button:
	         i = new Intent(this, Cinemas.class);
	         startActivity(i);
	         break;
		  case R.id.theaters_button:
	         i = new Intent(this, Theaters.class);
	         startActivity(i);
	         break;
	      case R.id.about_button:
	         i = new Intent(this, About.class);
	         startActivity(i);
	         break;
	      case R.id.exit_button:
	         finish();
	         break;	         
	      }
		
	}
	
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      super.onCreateOptionsMenu(menu);
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.settings:
         startActivity(new Intent(this, EditPreferences.class));
         return true;
      }
      return false;
   }

}