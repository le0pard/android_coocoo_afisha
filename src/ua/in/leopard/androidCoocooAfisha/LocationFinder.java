package ua.in.leopard.androidCoocooAfisha;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationFinder {
	 private Location my_location = null;
	 private LocationManager locationManager;
	 private LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			my_location = location;
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status,
				Bundle extras) {
			// TODO Auto-generated method stub

		}

	};

	 public LocationFinder(Context context) {
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null){
			my_location = location;
		}
		int update_val = Integer.parseInt(EditPreferences.getGpsUpdateInterval(context));
		if (0 != update_val && update_val > 0){
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, update_val * 60 * 1000, 100, locationListener);
	 	}
	}
	 
	public Location getOwnLocation(){
		return this.my_location;
	}
	
	public void stopFinder(){
		locationManager.removeUpdates(locationListener);
	}
}
