package ua.in.leopard.androidCoocooAfisha;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Html;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class TheatersItemizedOverlay extends ItemizedOverlay<TheaterOverlayItem> {
	
	private ArrayList<TheaterOverlayItem> mOverlays = new ArrayList<TheaterOverlayItem>();
	private Drawable marker = null;
	private Drawable selected_marker = null;
	private TheaterOverlayItem selected_item = null;
	private final MapActivity mapActivity;
	
	public TheatersItemizedOverlay(MapActivity mapActivity, Drawable defaultMarker, Drawable selected_marker) {
		super(boundCenterBottom(defaultMarker));
		this.mapActivity = mapActivity;
		this.marker = defaultMarker;
		this.selected_marker = selected_marker;
	}
	
	public TheaterDB getSelectedTheater(){
		if (selected_item != null && selected_item.getTheaterObj() != null){
			return selected_item.getTheaterObj();
		} else {
			return null;
		}
	}
	
	public void addOverlay(TheaterOverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected TheaterOverlayItem createItem(int i) {
		return mOverlays.get(i);
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		boundCenterBottom(marker);
	}
	@Override
	protected boolean onTap(int index) {
		TheaterOverlayItem item = mOverlays.get(index);
		if (selected_item != null){
			selected_item.setMarker(boundCenterBottom(marker));
		}
		item.setMarker(boundCenterBottom(selected_marker));
		selected_item = item;
		initInfoBlock(item);
		return true;
	}
	
	private void initInfoBlock(TheaterOverlayItem item){
		if (item.getTheaterObj() != null){
			TheaterDB theater_obj = item.getTheaterObj();
			this.mapActivity.setTitle(Html.fromHtml(theater_obj.getTitle()));
		}
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

}
