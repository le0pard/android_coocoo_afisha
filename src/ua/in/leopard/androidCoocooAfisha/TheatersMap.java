package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

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
        me.runOnFirstFix(new Runnable() {
            public void run() {
            	mapController.animateTo(me.getMyLocation());
            	mapController.setZoom(14);
            }
        });
        
        initMapPanel();
        
	}
	
	private void initMapPanel(){
		TextView current_city = (TextView)findViewById(R.id.theaters_map_city);
        current_city.setText(Html.fromHtml(getString(R.string.theaters_map_city_title) + " <b>" + EditPreferences.getCity(this) + "</b>"));
        
        View myLocationButton = findViewById(R.id.theaters_map_my_location);
        myLocationButton.setOnClickListener(this);
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
		switch (v.getId()) {
		  case R.id.theaters_map_my_location:
			 GeoPoint user_location = me.getMyLocation();
			 if (user_location != null){
				 mapController.animateTo(user_location);
				 mapController.setZoom(14);
			 }
	         break;        
	      }
	}

}
