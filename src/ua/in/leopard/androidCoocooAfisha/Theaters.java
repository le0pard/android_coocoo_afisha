package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Theaters extends MainActivity implements OnItemClickListener {
	private TheaterAdapter theaters_adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.theaters);
        
        String title = getString(R.string.theaters_title) + " - " + EditPreferences.getCity(this);
        if (EditPreferences.isTheatersIsFilter(this)){
        	title = title + " (" + getString(R.string.theaters_is_filter_on) + ")";
        }
        setTitle(title);
        
        ListView TheaterList = (ListView)findViewById(R.id.theaters_list);
        DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
        List<TheaterDB> theaters = DatabaseHelperObject.getTheaters(false);
        theaters_adapter = new TheaterAdapter(this, theaters);
        TheaterList.setAdapter(theaters_adapter);
        TheaterList.setOnItemClickListener(this);
        if (theaters.size() == 0){
        	TheaterList.setVisibility(View.GONE);
        	TextView noDataText = (TextView)findViewById(R.id.no_data_title);
        	noDataText.setVisibility(View.VISIBLE);
        	noDataText.setText(R.string.no_data_maybe_update);
        }
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		TheaterDB theater_object = (TheaterDB)theaters_adapter.getItem(position);
		//track info
		tracker.setCustomVar(1, "Cinema Selected", theater_object.getTitle());
		tracker.trackPageView("/cinema_selected");
		tracker.dispatch();
		//work
		Intent intent = new Intent(this, Theater.class);
		Bundle bundle = new Bundle();
		bundle.putInt("theater_id", theater_object.getId());
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
