package ua.in.leopard.androidCoocooAfisha;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class TheaterOverlayItem extends OverlayItem {
	private TheaterDB theater_obj = null;

	public TheaterOverlayItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
	}
	
	public void setTheaterObj(TheaterDB theater_obj){
		this.theater_obj = theater_obj;
	}
	
	public TheaterDB getTheaterObj(){
		return this.theater_obj;
	}

}
