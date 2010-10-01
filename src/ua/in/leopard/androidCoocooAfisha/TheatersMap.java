package ua.in.leopard.androidCoocooAfisha;

import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
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
	
	private LinearLayout theater_info_block;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theaters_map);
	    
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        mapView.setEnabled(true);
        mapController = mapView.getController();
        mapOverlays = mapView.getOverlays();
        
        me = new MyLocationOverlay(this, mapView);
        mapOverlays.add(me);
        
        moveToCityLocation();
        initMapPanel();
        
        theatersItemizedOverlay = new TheatersItemizedOverlay(
        		getResources().getDrawable(R.drawable.theater_map),
        		getResources().getDrawable(R.drawable.theater_map_selected));
        
        theatersItemizedOverlay.setViews(theater_info_block, (TextView)findViewById(R.id.theaters_map_title));
        
        initMapTheaters();
	}
	
	private void initMapTheaters(){
		DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
        List<TheaterDB> theaters = DatabaseHelperObject.getTheaters(false);
        for (int i = 0; i < theaters.size(); i++){
        	TheaterDB theater_row = theaters.get(i);
        	if (theater_row.getLatitude() != "" && theater_row.getLongitude() != ""){
        		float latitude = Float.valueOf(theater_row.getLatitude()).floatValue();
        		float longitude = Float.valueOf(theater_row.getLongitude()).floatValue();
	        	GeoPoint point = new GeoPoint((int)(latitude*1e6),(int)(longitude*1e6));
	            TheaterOverlayItem overlayitem = new TheaterOverlayItem(point, theater_row.getTitle(), theater_row.getAddress());
	            overlayitem.setTheaterObj(theater_row);
	            theatersItemizedOverlay.addOverlay(overlayitem);
        	}
        }
        mapOverlays.add(theatersItemizedOverlay);
	}
	
	private void initMapPanel(){
		TextView current_city = (TextView)findViewById(R.id.theaters_map_city);
        current_city.setText(Html.fromHtml(getString(R.string.theaters_map_city_title) + " <b>" + EditPreferences.getCity(this) + "</b>"));
        
        theater_info_block = (LinearLayout) findViewById(R.id.theaters_map_block_info);
        View myLocationButton = findViewById(R.id.theaters_map_my_location);
        myLocationButton.setOnClickListener(this);
        View theatersLocationButton = findViewById(R.id.theaters_map_show_city_location);
        theatersLocationButton.setOnClickListener(this);
        View theatersSeancesButton = findViewById(R.id.theaters_map_show_seances);
        theatersSeancesButton.setOnClickListener(this);
        View theatersPhoneButton = findViewById(R.id.theaters_map_call_phone);
        theatersPhoneButton.setOnClickListener(this);
        View theatersHideButton = findViewById(R.id.theaters_map_close_info);
        theatersHideButton.setOnClickListener(this);
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
	
	@Override
	public void onResume() {
		super.onResume();
		me.enableCompass();
		me.enableMyLocation();
	}
	@Override
	public void onPause() {
		super.onPause();
		me.disableCompass();
		me.disableMyLocation();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onClick(View v) {
		TheaterDB selected_theater = theatersItemizedOverlay.getSelectedTheater();
		switch (v.getId()) {
		  case R.id.theaters_map_my_location:
			 GeoPoint user_location = me.getMyLocation();
			 if (user_location != null){
				 mapController.animateTo(user_location);
			 } else {
				 Toast.makeText(this, getString(R.string.theaters_map_me_found_error), Toast.LENGTH_LONG).show();
			 }
	         break;
		  case R.id.theaters_map_show_seances:
			  if (selected_theater != null){
				Intent intent = new Intent(this, Theater.class);
				Bundle bundle = new Bundle();
				bundle.putInt("theater_id", selected_theater.getId());
				intent.putExtras(bundle);
				startActivity(intent);
			  }
		      break;
		  case R.id.theaters_map_call_phone:
			  if (selected_theater != null){
				  String toDial="tel:" + selected_theater.getCallPhone();
				  startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(toDial)));
			  }
		      break;
		  case R.id.theaters_map_show_city_location:
			  moveToCityLocation();
		      break;
		  case R.id.theaters_map_close_info:
			  theater_info_block.setVisibility(View.GONE);
		      break;
	      }
	}

}