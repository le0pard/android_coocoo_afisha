package ua.in.leopard.androidCoocooAfisha;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class androidCoocooAfisha extends MainActivity implements OnClickListener {
	private DataProgressDialog backgroudUpdater;
	private Intent serviceIntent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setDashboardButtons();
        
        restoreBackgroudUpdate();
        
        if (EditPreferences.getTheaterUrl(this) == "" || EditPreferences.getCinemasUrl(this) == ""){
        	startActivity(new Intent(this, EditPreferences.class));
        	Toast.makeText(this, getString(R.string.select_city_dialog), Toast.LENGTH_LONG).show();
        } else {

        	if (EditPreferences.getAutoUpdate(this)){
        		if (backgroudUpdater == null){
        			backgroudUpdater = new DataProgressDialog(this);
        		}
        		serviceIntent = new Intent(this, DataUpdateService.class);
	        	startService(serviceIntent);
	        }
        }
    }
    
    private void setDashboardButtons(){
    	View cinemasButton = findViewById(R.id.cinemas_button);
        cinemasButton.setOnClickListener(this);
        ImageView cinemasImageButton = (ImageView)cinemasButton.findViewById(R.id.dashboard_item_image);
        cinemasImageButton.setImageResource(R.drawable.dashboard_cinema_button);
        TextView cinemasTextButton = (TextView)cinemasButton.findViewById(R.id.dashboard_item_text_label);
        cinemasTextButton.setText(R.string.cinemas_label);
        
        View theatersButton = findViewById(R.id.theaters_button);
        theatersButton.setOnClickListener(this);
        ImageView theatersImageButton = (ImageView)theatersButton.findViewById(R.id.dashboard_item_image);
        theatersImageButton.setImageResource(R.drawable.dashboard_theater_button);
        TextView theatersTextButton = (TextView)theatersButton.findViewById(R.id.dashboard_item_text_label);
        theatersTextButton.setText(R.string.theaters_label);
        
        View theatersMapButton = findViewById(R.id.theaters_map_button);
        theatersMapButton.setOnClickListener(this);
        ImageView theatersMapImageButton = (ImageView)theatersMapButton.findViewById(R.id.dashboard_item_image);
        theatersMapImageButton.setImageResource(R.drawable.dashboard_map_button);
        TextView theatersMapTextButton = (TextView)theatersMapButton.findViewById(R.id.dashboard_item_text_label);
        theatersMapTextButton.setText(R.string.theaters_map_label);     
        
        View updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(this);
        ImageView updateImageButton = (ImageView)updateButton.findViewById(R.id.dashboard_item_image);
        updateImageButton.setImageResource(R.drawable.dashboard_update_button);
        TextView updateTextButton = (TextView)updateButton.findViewById(R.id.dashboard_item_text_label);
        updateTextButton.setText(R.string.update_label);
        
        View dashboardLogo = findViewById(R.id.dashboard_bar_logo);
        dashboardLogo.setClickable(false);
    }
    
    private void restoreBackgroudUpdate(){
    	if (getLastNonConfigurationInstance()!=null) {
    		backgroudUpdater = (DataProgressDialog)getLastNonConfigurationInstance();
    		if(backgroudUpdater.getStatus() == AsyncTask.Status.RUNNING) {
    			backgroudUpdater.newView(this);
    		}
    	}
    }
    
    @Override
    public Object onRetainNonConfigurationInstance() {
    	if(backgroudUpdater != null && backgroudUpdater.getStatus() == AsyncTask.Status.RUNNING) {
    		backgroudUpdater.closeView();
    	}
    	return(backgroudUpdater);
    }
  
    @Override
    protected void onResume() {
       super.onResume();
       setTitle(getString(R.string.current_city_title) + " " + EditPreferences.getCity(this));
    }

    @Override
    protected void onPause() {
       super.onPause();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	if (EditPreferences.getAutoUpdate(this) && 
    		Integer.parseInt(EditPreferences.getAutoUpdateTime(this)) != 0){
    			stopService(serviceIntent);
    	}  	
	}

	@Override
	public void onClick(View v) {
		if (EditPreferences.getTheaterUrl(this) == "" || EditPreferences.getCinemasUrl(this) == ""){
        	startActivity(new Intent(this, EditPreferences.class));
        	Toast.makeText(this, getString(R.string.select_city_dialog), Toast.LENGTH_LONG).show();
        } else {
			switch (v.getId()) {
			  case R.id.cinemas_button:
				 startActivity(new Intent(this, Cinemas.class));
		         break;
			  case R.id.theaters_button:
				 startActivity(new Intent(this, Theaters.class));
		         break;
			  case R.id.theaters_map_button:
				 startActivity(new Intent(this, TheatersMap.class));
		         break;
			  case R.id.update_button:
				 backgroudUpdater = new DataProgressDialog(this);
				 if(backgroudUpdater.getStatus() == AsyncTask.Status.PENDING) {
					 backgroudUpdater.execute();
				 }
				 /* Update widgets */
				 this.sendBroadcast(new Intent(AfishaWidgetProvider.FORCE_WIDGET_UPDATE));
		         break;         
		      }
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
      	case R.id.about_button:
      	 startActivity(new Intent(this, About.class));
      	return true;
      	default:
	     return super.onOptionsItemSelected(item);
      }
   }

}