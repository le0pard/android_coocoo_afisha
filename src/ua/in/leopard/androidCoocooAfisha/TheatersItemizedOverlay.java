package ua.in.leopard.androidCoocooAfisha;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;

public class TheatersItemizedOverlay extends ItemizedOverlay<TheaterOverlayItem> {
	
	private ArrayList<TheaterOverlayItem> mOverlays = new ArrayList<TheaterOverlayItem>();
	private Drawable marker = null;
	private Drawable selected_marker = null;
	private TheaterOverlayItem selected_item = null;
	
	private LinearLayout view_info_box = null;
	private TextView view_theater_title = null;
	
	public TheatersItemizedOverlay(Drawable defaultMarker, Drawable selected_marker) {
		super(boundCenterBottom(defaultMarker));
		this.marker = defaultMarker;
		this.selected_marker = selected_marker;
	}
	
	public void setViews(LinearLayout view_info_box, TextView view_theater_title){
		this.view_info_box = view_info_box;
		this.view_theater_title = view_theater_title;
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
			this.view_theater_title.setText(theater_obj.getTitle());
			this.view_info_box.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

}
