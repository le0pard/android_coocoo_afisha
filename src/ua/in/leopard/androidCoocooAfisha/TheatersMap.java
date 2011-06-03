package ua.in.leopard.androidCoocooAfisha;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class TheatersMap extends MapActivity implements OnClickListener {
	private MapView mapView;
	private MyLocationOverlay me;
	private MapController mapController;
	private List<Overlay> mapOverlays;
	private TheatersItemizedOverlay theatersItemizedOverlay;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theaters_map);
        
        setTitle(Html.fromHtml(getString(R.string.theaters_map_city_title) + " " + EditPreferences.getCity(this)));
        
        Bundle extras = getIntent().getExtras();
        int get_theater_id = 0;
        if(extras != null) {
        	get_theater_id = extras.getInt("theater_id", 0);
        }
        
        initMap();
        initTwoButtonsBar();
        initMapTheaters(get_theater_id);
        
        if (0 == get_theater_id){
        	moveToCityLocation();
        }
        
        if(!isGpsDeviceOn()){
        	AlertDialog.Builder gpsWarningDialog = new AlertDialog.Builder(this);
            gpsWarningDialog.setTitle(getString(R.string.gps_off_warning_title));
            gpsWarningDialog.setMessage(getString(R.string.gps_off_warning));
            gpsWarningDialog.setIcon(android.R.drawable.stat_sys_warning);
            gpsWarningDialog.setNeutralButton(getString(R.string.gps_off_warning_button), new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int which) {
            	}
            });
            gpsWarningDialog.show();
        }
	}
	
	private void initMap(){
		mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        mapView.setEnabled(true);
        mapController = mapView.getController();
        mapOverlays = mapView.getOverlays();
        if (EditPreferences.getGpsStatus(this)){
	        me = new MyLocationOverlay(this, mapView);
	        mapOverlays.add(me);
        }
	}
	
	private void initMapTheaters(int get_theater_id){
		theatersItemizedOverlay = new TheatersItemizedOverlay(this,
        		getResources().getDrawable(R.drawable.theater_map),
        		getResources().getDrawable(R.drawable.theater_map_selected));
		DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
        List<TheaterDB> theaters = DatabaseHelperObject.getTheaters(false);
        for (TheaterDB theater_row : theaters){
        	if (theater_row.getLatitude() != null && theater_row.getLatitude().length() != 0 &&
        			theater_row.getLongitude() != null && theater_row.getLongitude().length() != 0){
        		float latitude = Float.valueOf(theater_row.getLatitude()).floatValue();
        		float longitude = Float.valueOf(theater_row.getLongitude()).floatValue();
	        	GeoPoint point = new GeoPoint((int)(latitude*1e6),(int)(longitude*1e6));
	            TheaterOverlayItem overlayitem = new TheaterOverlayItem(point, theater_row.getTitle(), theater_row.getAddress());
	            overlayitem.setTheaterObj(theater_row);
	            theatersItemizedOverlay.addOverlay(overlayitem);
	            if (0 != get_theater_id){
	            	if (get_theater_id == theater_row.getId()){
	            		moveToTheater(overlayitem);
	            	}
	            }
        	}
        }
        if (theaters.size() > 0){
        	mapOverlays.add(theatersItemizedOverlay);
        }
	}
	
	private void initTwoButtonsBar(){
		View two_buttons_bar = findViewById(R.id.two_buttons_bar);
		if (two_buttons_bar != null){
			View two_buttons_bar_button_first = two_buttons_bar.findViewById(R.id.two_buttons_bar_button_one);
			if (two_buttons_bar_button_first != null){
				two_buttons_bar_button_first.setOnClickListener(this);
				TextView button_second_text = (TextView)two_buttons_bar_button_first.findViewById(R.id.two_buttons_button_label);
				if (button_second_text != null){
					button_second_text.setText(R.string.map_seances_label);
				}
				ImageView button_img = (ImageView)two_buttons_bar_button_first.findViewById(R.id.two_buttons_button_image);
				button_img.setImageResource(R.drawable.billboard_button);
			}
			
			View two_buttons_bar_button_second = two_buttons_bar.findViewById(R.id.two_buttons_bar_button_second);
			if (two_buttons_bar_button_second != null){
				two_buttons_bar_button_second.setOnClickListener(this);
				TextView button_second_text = (TextView)two_buttons_bar_button_second.findViewById(R.id.two_buttons_button_label);
				if (button_second_text != null){
					button_second_text.setText(R.string.more_info_text);
				}
				ImageView button_img = (ImageView)two_buttons_bar_button_second.findViewById(R.id.two_buttons_button_image);
				button_img.setImageResource(R.drawable.info_button);
			}
		}
	}
	
	private void moveToCityLocation(){
		Geocoder geoCoder = new Geocoder(this);
        try {
			List<Address> foundAdresses = geoCoder.getFromLocationName(EditPreferences.getCity(this) + getString(R.string.theaters_map_city_syfix), 1);
			if (foundAdresses.size() > 0){
				double lat = foundAdresses.get(0).getLatitude();
				double lon = foundAdresses.get(0).getLongitude();
				GeoPoint point = new GeoPoint((int)(lat*1e6),(int)(lon*1e6));
				mapController.animateTo(point);
				mapController.setZoom(12);
			} else {
				Toast.makeText(this, getString(R.string.theaters_map_city_found_error), Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			//e.printStackTrace();
			Toast.makeText(this, getString(R.string.theaters_map_city_found_error), Toast.LENGTH_LONG).show();
		}
	}
	
	private void moveToTheater(TheaterOverlayItem theater_overlay){
        TheaterDB theater = theater_overlay.getTheaterObj();
        if (theater != null){
        	if (theater.getLatitude() != null && theater.getLatitude().length() != 0 &&
        			theater.getLongitude() != null && theater.getLongitude().length() != 0){
        		float latitude = Float.valueOf(theater.getLatitude()).floatValue();
        		float longitude = Float.valueOf(theater.getLongitude()).floatValue();
	        	GeoPoint point = new GeoPoint((int)(latitude*1e6),(int)(longitude*1e6));
	        	mapController.animateTo(point);
	        	mapController.setZoom(16);
	        	theatersItemizedOverlay.tapByTheaterOverlayItem(theater_overlay);
        	} else {
        		moveToCityLocation();
        	}
        } else {
        	moveToCityLocation();
        }
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (EditPreferences.getGpsStatus(this)){
			this.me.enableCompass();
			this.me.enableMyLocation();
		}
	}
	@Override
	public void onPause() {
		super.onPause();
		if (EditPreferences.getGpsStatus(this)){
			this.me.disableMyLocation();
			this.me.disableCompass();
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onClick(View v) {
		TheaterDB selected_theater = theatersItemizedOverlay.getSelectedTheater();
		if (null == selected_theater){
			Toast.makeText(this, getString(R.string.theaters_map_select_theater), Toast.LENGTH_LONG).show();
		} else {
			switch (v.getId()) {
			  case R.id.two_buttons_bar_button_one:
				  	startThIntent(new Intent(this, Theater.class), selected_theater);
					break;
			  case R.id.two_buttons_bar_button_second:
				  	startThIntent(new Intent(this, TheaterInfo.class), selected_theater);
					break;
		      }
		}
	}
	
	private void startThIntent(Intent intent, TheaterDB selected_theater){
		Bundle bundle = new Bundle();
		bundle.putInt("theater_id", selected_theater.getId());
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.map_menu, menu);
	    if(!isGpsDeviceOn() || !EditPreferences.getGpsStatus(this)){
        	MenuItem myLocationButton = menu.findItem(R.id.where_me);
        	myLocationButton.setEnabled(false);
        }
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case R.id.where_me:
		    	GeoPoint user_location = me.getMyLocation();
				if (user_location != null){
					mapController.setZoom(16);
					mapController.animateTo(user_location);
				} else {
					Toast.makeText(this, getString(R.string.theaters_map_me_found_error), Toast.LENGTH_LONG).show();
				}
		        return true;
		    case R.id.city_location:
		    	moveToCityLocation();
		        return true;
		    case R.id.satellit_switch:
		    	mapView.setSatellite(!mapView.isSatellite());
		        return true;
		    case R.id.traffic_switch:
		    	mapView.setTraffic(!mapView.isTraffic());
		        return true;
		    case R.id.hotkey_help:
		    	startActivity(new Intent(this, AboutMap.class));
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		super.onKeyDown(KeyCode, event);
		switch(KeyCode){
			case KeyEvent.KEYCODE_DPAD_UP:
				moveUp();
                return true;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				moveDown();
                return true;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				moveLeft();
                return true;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				moveRight();
                return true;
			case KeyEvent.KEYCODE_DPAD_CENTER:
			case KeyEvent.KEYCODE_2:
				mapController.zoomIn();
		        return true;
			case KeyEvent.KEYCODE_8:
				mapController.zoomOut();
		        return true;
			case KeyEvent.KEYCODE_BACK:
				finish();
		        return true;
			case KeyEvent.KEYCODE_4:
				mapView.setSatellite(!mapView.isSatellite());
		        return true;
			case KeyEvent.KEYCODE_5:
				mapView.setTraffic(!mapView.isTraffic());
		        return true;
		}
		mapView.invalidate();
        return false;
	}
	
	public boolean isGpsDeviceOn(){
        String checkGpsDevice = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(checkGpsDevice.length() == 0 || checkGpsDevice == null || (!checkGpsDevice.contains(LocationManager.GPS_PROVIDER) && !checkGpsDevice.contains(LocationManager.NETWORK_PROVIDER))){
                return false;
        }
        return true;
    }
	
	public void moveUp(){
        GeoPoint centerGeoPoint = mapView.getMapCenter();
        Point centerPoint = new Point();
        mapView.getProjection().toPixels(centerGeoPoint, centerPoint);
        GeoPoint newCenterGeoPoint = mapView.getProjection().fromPixels(centerPoint.x, centerPoint.y - mapView.getHeight()/4);
        mapController.animateTo(newCenterGeoPoint);
	}
	
	public void moveDown(){
        GeoPoint centerGeoPoint = mapView.getMapCenter();
        Point centerPoint = new Point();
        mapView.getProjection().toPixels(centerGeoPoint, centerPoint);
        GeoPoint newCenterGeoPoint = mapView.getProjection().fromPixels(centerPoint.x, centerPoint.y + mapView.getHeight()/4);
        mapController.animateTo(newCenterGeoPoint);
	}
	
	public void moveLeft(){
        GeoPoint centerGeoPoint = mapView.getMapCenter();
        Point centerPoint = new Point();
        mapView.getProjection().toPixels(centerGeoPoint, centerPoint);
        GeoPoint newCenterGeoPoint = mapView.getProjection().fromPixels(centerPoint.x - mapView.getWidth()/4, centerPoint.y);
        mapController.animateTo(newCenterGeoPoint);
	}
	
	public void moveRight(){
        GeoPoint centerGeoPoint = mapView.getMapCenter();
        Point centerPoint = new Point();
        mapView.getProjection().toPixels(centerGeoPoint, centerPoint);
        GeoPoint newCenterGeoPoint = mapView.getProjection().fromPixels(centerPoint.x + mapView.getHeight()/4, centerPoint.y);
        mapController.animateTo(newCenterGeoPoint);
	}
	
	public void setTitle(CharSequence title){
		super.setTitle(title);
		setTitleFromActivityLabel(title);
	}
	
	public void setTitleFromActivityLabel(CharSequence title)
	{
	    TextView main_title = (TextView)findViewById(R.id.main_title);
	    if (main_title != null) main_title.setText(Html.fromHtml(title.toString()));
	}
	
}
